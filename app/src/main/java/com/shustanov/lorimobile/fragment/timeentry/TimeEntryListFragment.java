package com.shustanov.lorimobile.fragment.timeentry;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.depthguru.mvp.annotations.EMvpFragment;
import com.depthguru.mvp.annotations.Presenter;
import com.shustanov.lorimobile.R;
import com.shustanov.lorimobile.fragment.BaseFragment;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.f_refresh_list)
@EMvpFragment
public class TimeEntryListFragment extends BaseFragment implements TimeEntryListView {
    @ViewById(R.id.recycler_view)
    protected RecyclerView list;
    @ViewById(R.id.swipe_refresh)
    protected SwipeRefreshLayout refreshLayout;

    @Presenter
    protected TimeEntryListPresenter presenter;
}
