package com.shustanov.lorimobile.activity.main;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shustanov.lorimobile.R;
import com.shustanov.lorimobile.activity.BaseActivity;
import com.shustanov.lorimobile.activity.calendar.CalendarActivity;
import com.shustanov.lorimobile.activity.calendar.CalendarActivity_;

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected View getSnackBarView() {
        return findViewById(R.id.coordinator);
    }
}
