package com.shustanov.lorimobile.activity.main;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shustanov.lorimobile.R;
import com.shustanov.lorimobile.fragment.tasklist.TaskListFragment_;

import java.util.InputMismatchException;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private final Context context;


    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TaskListFragment_.builder().build();
            default:
                throw new InputMismatchException();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(getPageTitleId(position));
    }

    @StringRes
    public int getPageTitleId(int position) {
        switch (position) {
            case 0:
                return R.string.tasks_tab;
            case 1:
                return 0;
        }
        return -1;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
