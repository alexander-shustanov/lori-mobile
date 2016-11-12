package com.shustanov.lorimobile.ui.fragment.task;

import com.shustanov.lorimobile.data.task.Task;
import com.shustanov.lorimobile.ui.list.ListView;

public interface TaskListView extends ListView<Task> {
    void openAddTimeEntryActivity(String id);
}
