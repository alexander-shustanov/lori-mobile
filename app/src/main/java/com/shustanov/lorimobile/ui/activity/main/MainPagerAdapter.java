package com.shustanov.lorimobile.ui.activity.main;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shustanov.lorimobile.R;
import com.shustanov.lorimobile.ui.fragment.project.ProjectListFragment_;
import com.shustanov.lorimobile.ui.fragment.task.TaskListFragment_;

import java.util.InputMismatchException;

class MainPagerAdapter extends FragmentPagerAdapter {
    private final Context context;


    MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ProjectListFragment_.builder().build();
            case 1:
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
    private int getPageTitleId(int position) {
        switch (position) {
            case 0:
                return R.string.projects_tab;
            case 1:
                return R.string.tasks_tab;
        }
        return -1;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
