package com.shustanov.lorimobile.fragment.tasklist;

import com.depthguru.mvp.annotations.EPresenter;
import com.depthguru.mvp.api.Presenter;
import com.shustanov.lorimobile.data.task.Task;
import com.shustanov.lorimobile.data.task.TaskApi;
import com.shustanov.lorimobile.data.task.TaskRepository;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

@EPresenter
@EBean
public class TaskListPresenter extends Presenter<TaskListView> implements TaskListAdapter.Listener {
    @Bean
    protected TaskApi taskApi;
    @Bean
    protected TaskRepository taskRepository;

    private CompositeSubscription compositeSubscription;

    private boolean clearPending;

    @Override
    protected void onViewAttached() {
        super.onViewAttached();
        compositeSubscription = new CompositeSubscription();
        compositeSubscription.add(taskRepository.getAll().subscribe(this::onTasksReceived, this::onTaskGetAllError));
    }

    private void onTaskGetAllError(Throwable throwable) {
        throw new RuntimeException(throwable);
    }

    public void refresh() {
        taskRepository.refresh();
        clearPending = true;
        compositeSubscription.add(
                taskRepository.
                        getAll().
                        subscribe(this::onTasksReceived, this::onTaskGetAllError, getView()::stopRefresh)
        );
    }

    @Override
    protected void onViewDetached() {
        super.onViewDetached();
        compositeSubscription.unsubscribe();
    }

    private void onTasksReceived(List<Task> tasks) {
        if(clearPending) {
            getView().clear();
            clearPending = false;
        }
        getView().addTasks(tasks);
    }

    @Override
    public void addTimeEntry(Task task) {
        getView().openAddTimeEntryActivity(task.getId());
    }

    @Override
    public void openTaskDetails(Task task) {
        getView().openTaskDetails(task.getId());
    }
}
