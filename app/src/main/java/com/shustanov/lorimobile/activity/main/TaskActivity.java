package com.shustanov.lorimobile.activity.main;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.depthguru.mvp.annotations.EMvpActivity;
import com.depthguru.mvp.annotations.Presenter;
import com.shustanov.lorimobile.R;
import com.shustanov.lorimobile.activity.BaseActivity;
import com.shustanov.lorimobile.activity.calendar.CalendarActivity_;
import com.shustanov.lorimobile.activity.login.LoginActivity_;
import com.shustanov.lorimobile.activity.timeentry.NewTimeEntryActivity_;
import com.shustanov.lorimobile.api.LoginApi;
import com.shustanov.lorimobile.data.task.Task;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.a_main)
@EMvpActivity
public class TaskActivity extends BaseActivity implements TaskListView {
    private static final int ADD_TIME_ENTRY_REQUEST_CODE = 0x0001;

    private TaskListAdapter adapter;

    @Bean
    LoginApi loginApi;

    @ViewById(R.id.recycler_view)
    RecyclerView list;
    @ViewById(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @Presenter
    TaskListPresenter presenter;

    @AfterViews
    protected void afterViews() {
        setSupportActionBar(toolbar);

        adapter = new TaskListAdapter(this);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter.setListener(presenter);

        refreshLayout.setOnRefreshListener(presenter::refresh);
        setupRefreshLayout(refreshLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.open_calendar:
                CalendarActivity_.intent(this).start();
                return true;
            case R.id.logout:
                loginApi.logout();
                finish();
                LoginActivity_.intent(this).start();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected View getSnackBarView() {
        return findViewById(R.id.coordinator);
    }

    @Override
    public void setTasks(List<Task> tasks) {
        adapter.setTasks(tasks);
    }

    @Override
    public void stopRefresh() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void refreshFailed(Throwable throwable) {
        if(!checkNetworkAndLogin(throwable)) {
            snackBar(R.string.something_went_wrong);
        }
    }

    @Override
    public void openAddTimeEntryActivity(String taskId) {
        NewTimeEntryActivity_.intent(this).taskId(taskId).startForResult(ADD_TIME_ENTRY_REQUEST_CODE);
    }

    @OnActivityResult(ADD_TIME_ENTRY_REQUEST_CODE)
    void onTimeEntryResult(int result) {
        if(result == Activity.RESULT_OK) {
            snackBar("New time entry created");
        }
    }

}
