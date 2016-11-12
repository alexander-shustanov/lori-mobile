package com.shustanov.lorimobile.ui.fragment.task;

import com.depthguru.mvp.annotations.EPresenter;
import com.shustanov.lorimobile.data.EntityApi;
import com.shustanov.lorimobile.data.Repository;
import com.shustanov.lorimobile.data.task.Task;
import com.shustanov.lorimobile.data.task.TaskApi;
import com.shustanov.lorimobile.data.task.TaskRepository;
import com.shustanov.lorimobile.ui.list.ListViewPresenter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

@EPresenter
@EBean
public class TaskListPresenter extends ListViewPresenter<Task, TaskListView> implements TaskListAdapter.Listener {
    @Bean
    protected TaskApi taskApi;
    @Bean
    protected TaskRepository taskRepository;

    protected EntityApi<Task, ?> api() {
        return taskApi;
    }

    @Override
    protected Repository<Task> repository() {
        return taskRepository;
    }

    @Override
    public void addTimeEntry(Task task) {
        getView().openAddTimeEntryActivity(task.getId());
    }

}
