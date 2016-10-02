package com.shustanov.lorimobile.activity.timeentry;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.shustanov.lorimobile.R;
import com.shustanov.lorimobile.activity.BaseActivity;
import com.shustanov.lorimobile.data.task.TaskApi;
import com.shustanov.lorimobile.data.task.TaskRepository;
import com.shustanov.lorimobile.data.timeentry.TimeEntry;
import com.shustanov.lorimobile.data.timeentry.TimeEntryApi;
import com.shustanov.lorimobile.data.timeentry.TimeEntryRepository;
import com.shustanov.lorimobile.databinding.ANewTimeEntryBinding;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.joda.time.LocalDate;

@EActivity
public class NewTimeEntryActivity extends BaseActivity {

    @Extra
    protected String taskId;

    @ViewById(R.id.toolbar)
    protected Toolbar toolbar;
    @ViewById(R.id.foreground_progress)
    protected View foregroundProgress;

    @Bean
    protected TaskRepository taskRepository;
    @Bean
    protected TimeEntryRepository timeEntryRepository;
    @Bean
    protected TaskApi taskApi;

    private NewTimeEntryVm vm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new NewTimeEntryVm();

        ANewTimeEntryBinding binding = DataBindingUtil.setContentView(this, R.layout.a_new_time_entry);
        binding.setVm(vm);

        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setTitle(R.string.a_new_time_entry_title);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void onStart() {
        super.onStart();
        addSubscription(taskRepository.getById(taskId).subscribe(this.vm::setTask));
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
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Click(R.id.new_time_entry_done)
    protected void done() {
        foregroundProgress.setVisibility(View.VISIBLE);
        addSubscription(timeEntryRepository.create(vm.createTimeEntry()).subscribe(timeEntry -> {
        },throwable -> {
            foregroundProgress.setVisibility(View.GONE);
            toast("Error");
        },() -> {
            foregroundProgress.setVisibility(View.GONE);
        }));
    }
}
