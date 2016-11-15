package com.shustanov.lorimobile.ui.activity.timeentry;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.shustanov.lorimobile.R;
import com.shustanov.lorimobile.Utilities;
import com.shustanov.lorimobile.data.project.Project;
import com.shustanov.lorimobile.data.project.ProjectRepository;
import com.shustanov.lorimobile.data.task.Task;
import com.shustanov.lorimobile.data.task.TaskApi;
import com.shustanov.lorimobile.data.task.TaskRepository;
import com.shustanov.lorimobile.data.timeentry.TimeEntry;
import com.shustanov.lorimobile.data.timeentry.TimeEntryRepository;
import com.shustanov.lorimobile.databinding.ANewTimeEntryBinding;
import com.shustanov.lorimobile.rx.DoOnFirst;
import com.shustanov.lorimobile.ui.activity.BaseActivity;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.joda.time.LocalDate;

import java.util.Objects;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

@EActivity
public class NewTimeEntryActivity extends BaseActivity implements TimeEntryVm.Listener {
    @Extra
    protected String projectId;
    @Extra
    protected String taskId;
    @Extra
    protected String timeEntryId;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.foreground_progress)
    View foregroundProgress;

    @Bean
    TaskRepository taskRepository;
    @Bean
    TimeEntryRepository timeEntryRepository;
    @Bean
    TaskApi taskApi;
    @Bean
    ProjectRepository projectRepository;

    private TimeEntryVm vm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ANewTimeEntryBinding binding = DataBindingUtil.setContentView(this, R.layout.a_new_time_entry);

        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setTitle(R.string.a_new_time_entry_title);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        binding.toolbar.setNavigationOnClickListener(v -> finish());


        ArrayAdapter<Project> projectAdapter = createSpinnerAdapter();
        ArrayAdapter<Task> taskAdapter = createSpinnerAdapter();
        vm = new TimeEntryVm(this, projectAdapter, taskAdapter);
        binding.setVm(vm);

        binding.projectSpinner.setAdapter(projectAdapter);
        binding.taskSpinner.setAdapter(taskAdapter);
    }

    @NonNull
    private <T> ArrayAdapter<T> createSpinnerAdapter() {
        ArrayAdapter<T> projectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return projectAdapter;
    }

    @Override
    protected void onStart() {
        super.onStart();

        addSubscription(
                projectRepository
                        .getAll()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(vm::addProjects)
        );

        if (taskId != null) {
            addSubscription(
                    updateTask(this.taskId)
                            .doOnNext(vm::setTask)
                            .flatMap(task -> updateProject(task.getProject()))
                            .subscribe(vm::setProject)
            );
        }

        if (projectId != null) {
            addSubscription(updateProject(projectId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(project -> {
                        vm.setProject(project);
                        loadTaskList(project);
                    }));
        }

        if (timeEntryId != null) {
            addSubscription(timeEntryRepository
                    .getById(timeEntryId)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .doOnNext(vm::fill)
                    .flatMap(timeEntry -> updateTask(timeEntry.getTaskId()))
                    .doOnNext(vm::setTask)
                    .flatMap(task -> updateProject(task.getProject()))
                    .subscribe(vm::setProject));
        }
    }

    private Observable<Project> updateProject(String projectId) {
        return projectRepository
                .getById(projectId)
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Task> updateTask(String taskId) {
        return taskRepository
                .getById(taskId)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Click(R.id.date_text)
    protected void onDateClicked() {
        LocalDate date = vm.getDate();
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (datePicker1, year, month, day) -> vm.setDate(new LocalDate(year, month + 1, day)),
                date.getYear(),
                date.getMonthOfYear() - 1,
                date.getDayOfMonth());
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Click(R.id.new_time_entry_done)
    protected void done() {
        Utilities.hideKeyBoard(this);
        showProgress();
        TimeEntry entity = vm.createTimeEntry();
        if (timeEntryId != null) {
            entity.setId(timeEntryId);
        }
        addSubscription(
                timeEntryRepository
                        .commit(entity)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(timeEntry -> {
                            setResult(RESULT_OK);
                            finish();
                        }, throwable -> {
                            hideProgress();
                            snackBar(R.string.something_went_wrong);
                        }, this::hideProgress));
    }

    private void showProgress() {
        foregroundProgress.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        foregroundProgress.setVisibility(View.GONE);
    }

    @Override
    public void onProjectChanged(Project project) {
        if (Objects.equals(projectId, project.getId())) {
            return;
        }

        projectId = project.getId();
        getIntent().putExtra(NewTimeEntryActivity_.PROJECT_ID_EXTRA, projectId);
        loadTaskList(project);
    }

    private void loadTaskList(Project project) {
        addSubscription(
                projectRepository
                        .getById(projectId)
                        .flatMap(taskRepository::getByProject)
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(DoOnFirst.onFirstDo(tasks -> {
                            vm.clearTasks();
                            Task vmTask = vm.getTask();
                            if (vmTask == null || !Objects.equals(vmTask.getProject(), project.getId())) {
                                vm.setTask(tasks.get(0));
                            }
                        }))
                        .subscribe(vm::addTasks)
        );
    }

    @Override
    public void onTaskChanged(Task task) {
        taskId = task.getId();
        getIntent().putExtra(NewTimeEntryActivity_.TASK_ID_EXTRA, taskId);
    }
}
