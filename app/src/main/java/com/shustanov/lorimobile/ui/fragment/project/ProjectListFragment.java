package com.shustanov.lorimobile.ui.fragment.project;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.depthguru.mvp.annotations.EMvpFragment;
import com.depthguru.mvp.annotations.Presenter;
import com.shustanov.lorimobile.R;
import com.shustanov.lorimobile.data.project.Project;
import com.shustanov.lorimobile.ui.activity.timeentry.NewTimeEntryActivity_;
import com.shustanov.lorimobile.ui.fragment.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * ProjectListFragment
 * </p>
 * alexander.shustanov on 10.11.16
 */
@EFragment(R.layout.f_refresh_list)
@EMvpFragment
public class ProjectListFragment extends BaseFragment implements ProjectListView {
    private static final int ADD_TIME_ENTRY_REQUEST_CODE = 0x0002;
    @Presenter
    ProjectListPresenter presenter;
    @ViewById(R.id.recycler_view)
    RecyclerView list;
    @ViewById(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;
    private ProjectListAdapter adapter;

    @AfterViews
    void init() {
        adapter = new ProjectListAdapter(getContext(), presenter);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        refreshLayout.setOnRefreshListener(presenter::refresh);
        setupRefreshLayout(refreshLayout);
    }

    @Override
    public void setItems(List<Project> items) {
        adapter.setProjects(items);
    }

    @Override
    public void refreshFailed(Throwable throwable) {
        if (!checkNetworkAndLogin(throwable)) {
            snackBar(R.string.something_went_wrong);
        }
    }

    @Override
    public void stopRefresh() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void openAddTimeEntryActivity(String taskId) {
        NewTimeEntryActivity_.intent(this).taskId(taskId).startForResult(ADD_TIME_ENTRY_REQUEST_CODE);
    }
}
