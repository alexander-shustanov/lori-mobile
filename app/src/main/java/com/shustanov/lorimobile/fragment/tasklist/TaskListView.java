package com.shustanov.lorimobile.fragment.tasklist;

import com.depthguru.mvp.api.View;
import com.shustanov.lorimobile.data.task.Task;

import java.util.List;

public interface TaskListView extends View {
    void addTasks(List<Task> tasks);

    void stopRefresh();

    void clear();

    void openAddTimeEntryActivity(String id);
}
