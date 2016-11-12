package com.shustanov.lorimobile.ui.fragment.project;

import com.depthguru.mvp.annotations.EPresenter;
import com.shustanov.lorimobile.data.EntityApi;
import com.shustanov.lorimobile.data.Repository;
import com.shustanov.lorimobile.data.project.Project;
import com.shustanov.lorimobile.data.project.ProjectApi;
import com.shustanov.lorimobile.data.project.ProjectRepository;
import com.shustanov.lorimobile.ui.fragment.project.ProjectListAdapter;
import com.shustanov.lorimobile.ui.fragment.project.ProjectListView;
import com.shustanov.lorimobile.ui.list.ListViewPresenter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * ProjectListPresenter
 * </p>
 * alexander.shustanov on 10.11.16
 */
@EBean
@EPresenter
public class ProjectListPresenter extends ListViewPresenter<Project, ProjectListView> implements ProjectListAdapter.Listener {
    @Bean
    protected ProjectApi projectApi;
    @Bean
    protected ProjectRepository projectRepository;

    @Override
    public void addTimeEntry(Project task) {
        getView().openAddTimeEntryActivity(task.getId());
    }

    @Override
    protected EntityApi<Project, ?> api() {
        return projectApi;
    }

    @Override
    protected Repository<Project> repository() {
        return projectRepository;
    }
}
