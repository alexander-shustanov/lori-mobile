package com.shustanov.lorimobile.ui.fragment.week;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.depthguru.mvp.annotations.EMvpFragment;
import com.depthguru.mvp.annotations.Presenter;
import com.shustanov.lorimobile.R;
import com.shustanov.lorimobile.ui.activity.timeentry.NewTimeEntryActivity_;
import com.shustanov.lorimobile.data.timeentry.TimeEntry;
import com.shustanov.lorimobile.ui.fragment.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.joda.time.LocalDate;

import java.util.Date;
import java.util.List;

@EMvpFragment
@EFragment(R.layout.f_week)
public class WeekFragment extends BaseFragment implements WeekView {
    private static final int EDIT_TIME_ENTRY = 0xff00;

    @ViewById(R.id.days_list)
    RecyclerView daysList;

    @ViewById(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @Presenter
    WeekPresenter presenter;

    @FragmentArg
    long time;
    private WeekAdapter adapter;


    @AfterViews
    void init() {
        presenter.setup(new Date(time));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        daysList.setLayoutManager(layoutManager);
        Drawable divider = getContext().getResources().getDrawable(R.drawable.day_divider, getContext().getTheme());
        daysList.addItemDecoration(new WeekItemDecorator(divider));

        refreshLayout.setOnRefreshListener(presenter);
        setupRefreshLayout(refreshLayout);
    }

    @Override
    public void onStop() {
        refreshLayout.setRefreshing(false);
        super.onStop();
    }

    @Override
    public void setWeek(LocalDate monday) {
        adapter = new WeekAdapter(getContext(), monday, presenter);
        daysList.setAdapter(adapter);
    }

    @Override
    public void update(List<TimeEntry> entries) {
        adapter.updateEntries(entries);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        refreshLayout.setRefreshing(refreshing);
    }

    @Override
    public void refreshFailed(Throwable throwable) {
        if (!checkNetworkAndLogin(throwable)) {
            snackBar(R.string.something_went_wrong);
        }
    }

    @Override
    public void deletionFailed(Throwable throwable) {
        if (!checkNetworkAndLogin(throwable)) {
            snackBar(R.string.something_went_wrong);
        }
    }

    @Override
    public void startEditing(TimeEntry timeEntry) {
        NewTimeEntryActivity_.intent(this).timeEntryId(timeEntry.getId()).startForResult(EDIT_TIME_ENTRY);
    }

    @OnActivityResult(EDIT_TIME_ENTRY)
    void onEditFinish(int result) {
        if(result == Activity.RESULT_OK) {
            presenter.onEditSuccess();
        }
    }
}
