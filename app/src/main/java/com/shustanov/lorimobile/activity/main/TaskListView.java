package com.shustanov.lorimobile.activity.main;

import com.depthguru.mvp.api.View;
import com.shustanov.lorimobile.data.task.Task;

import java.util.List;

public interface TaskListView extends View {
    void setTasks(List<Task> tasks);

    void stopRefresh();

    void openAddTimeEntryActivity(String id);

    void refreshFailed(Throwable throwable);
}
