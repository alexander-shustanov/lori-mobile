package com.shustanov.lorimobile.fragment.tasklist;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.depthguru.mvp.annotations.EMvpFragment;
import com.depthguru.mvp.annotations.Presenter;
import com.shustanov.lorimobile.R;
import com.shustanov.lorimobile.activity.timeentry.NewTimeEntryActivity_;
import com.shustanov.lorimobile.data.task.Task;
import com.shustanov.lorimobile.fragment.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.f_refresh_list)
@EMvpFragment
public class TaskListFragment extends BaseFragment implements TaskListView {

    @ViewById(R.id.recycler_view)
    protected RecyclerView list;
    @ViewById(R.id.swipe_refresh)
    protected SwipeRefreshLayout refreshLayout;

    @Presenter
    protected TaskListPresenter presenter;

    private TaskListAdapter adapter;

    @AfterViews
    protected void afterViews() {
        adapter = new TaskListAdapter(getContext());
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        refreshLayout.setOnRefreshListener(presenter::refresh);
        adapter.setListener(presenter);
    }

    @Override
    public void addTasks(List<Task> tasks) {
        adapter.addTasks(tasks);
    }

    @Override
    public void stopRefresh() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void clear() {
        adapter.clear();
    }

    @Override
    public void openAddTimeEntryActivity(String taskId) {
        NewTimeEntryActivity_.intent(this).taskId(taskId).start();
    }
}