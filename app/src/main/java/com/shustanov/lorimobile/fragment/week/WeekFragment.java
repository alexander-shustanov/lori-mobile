package com.shustanov.lorimobile.fragment.week;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shustanov.lorimobile.R;
import com.shustanov.lorimobile.fragment.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.joda.time.LocalDate;

import java.util.Date;

//@EMvpFragment
@EFragment(R.layout.f_week)
public class WeekFragment extends BaseFragment {
    @ViewById(R.id.days_list)
    RecyclerView daysList;

    @FragmentArg
    long time;

    private LocalDate monday;
    //to be honest, it is not sunday, it is the next week monday. lol
    private LocalDate sunday;

    @AfterViews
    void init() {
        monday = LocalDate.fromDateFields(new Date(time));
        sunday = monday.plusWeeks(1);

        daysList.setLayoutManager(new LinearLayoutManager(getContext()));
        daysList.setAdapter(new WeekAdapter(getContext(), monday));
    }
}
