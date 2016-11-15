package com.shustanov.lorimobile.ui.fragment.project;

import com.shustanov.lorimobile.data.project.Project;
import com.shustanov.lorimobile.ui.list.ListView;

/**
 * ProjectListView
 * </p>
 * alexander.shustanov on 10.11.16
 */
public interface ProjectListView extends ListView<Project> {
    void openAddTimeEntryActivity(String projectId);
}
