package com.shustanov.lorimobile.activity.calendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shustanov.lorimobile.fragment.week.WeekFragment_;

import org.joda.time.LocalDate;


class WeeksAdapter extends FragmentPagerAdapter {
    static final int CURRENT_WEEK_POSITION = Integer.MAX_VALUE / 2;

    private final LocalDate currentWeek;

    WeeksAdapter(FragmentManager fm, LocalDate currentWeek) {
        super(fm);
        this.currentWeek = currentWeek;
    }

    @Override
    public Fragment getItem(int position) {
        int diff = position - CURRENT_WEEK_POSITION;
        LocalDate date = currentWeek.plusWeeks(diff);

        return WeekFragment_.builder().time(date.toDate().getTime()).build();
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
}
