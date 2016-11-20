package com.shustanov.lorimobile.ui.fragment.project;

import com.depthguru.mvp.annotations.EMvpView;
import com.depthguru.mvp.annotations.stretegy.Once;
import com.depthguru.mvp.annotations.stretegy.Skip;
import com.shustanov.lorimobile.data.project.Project;
import com.shustanov.lorimobile.ui.list.ListView;

/**
 * ProjectListView
 * </p>
 * alexander.shustanov on 10.11.16
 */
@EMvpView
public interface ProjectListView extends ListView<Project> {
    @Once
    void openAddTimeEntryActivity(String projectId);
}