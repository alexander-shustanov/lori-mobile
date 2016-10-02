package com.shustanov.lorimobile.activity.main;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.shustanov.lorimobile.R;
import com.shustanov.lorimobile.activity.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.a_main)
public class MainActivity extends BaseActivity {
    @ViewById(R.id.toolbar)
    protected Toolbar toolbar;
    @ViewById(R.id.tabs)
    protected TabLayout tabs;
    @ViewById(R.id.pager)
    protected ViewPager pager;

    @AfterViews
    protected void afterViews() {
        tabs.setupWithViewPager(pager);
        setSupportActionBar(toolbar);
        pager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), this));
    }
}
