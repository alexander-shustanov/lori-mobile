package com.shustanov.lorimobile.ui.fragment.task;

import com.depthguru.mvp.annotations.EMvpView;
import com.depthguru.mvp.annotations.stretegy.Skip;
import com.shustanov.lorimobile.data.task.Task;
import com.shustanov.lorimobile.ui.list.ListView;

@EMvpView
public interface TaskListView extends ListView<Task> {
    @Skip
    void openAddTimeEntryActivity(String id);
}
