package com.shustanov.lorimobile.fragment.week;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.depthguru.mvp.annotations.EMvpFragment;
import com.depthguru.mvp.annotations.Presenter;
import com.shustanov.lorimobile.R;
import com.shustanov.lorimobile.data.timeentry.TimeEntry;
import com.shustanov.lorimobile.fragment.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.joda.time.LocalDate;

import java.util.List;

@EMvpFragment
@EFragment(R.layout.f_week)
public class WeekFragment extends BaseFragment implements WeekView {
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
        daysList.setLayoutManager(new LinearLayoutManager(getContext()));

        refreshLayout.setOnRefreshListener(presenter);
    }

    @Override
    public void onStop() {
        refreshLayout.setRefreshing(false);
        super.onStop();
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public void setWeek(LocalDate monday) {
        adapter = new WeekAdapter(monday);
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
        if (!checkNetwork(throwable)) {
            snackBar(R.string.something_went_wrong);
        }
    }


}
