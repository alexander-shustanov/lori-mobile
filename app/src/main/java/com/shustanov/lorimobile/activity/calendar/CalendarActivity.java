package com.shustanov.lorimobile.activity.calendar;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.shustanov.lorimobile.R;
import com.shustanov.lorimobile.activity.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.joda.time.LocalDate;

import java.util.Date;
import java.util.Locale;

import static android.text.format.DateFormat.format;
import static android.text.format.DateFormat.getBestDateTimePattern;

@EActivity(R.layout.a_calendar)
public class CalendarActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private static final String pattern = getBestDateTimePattern(Locale.getDefault(), "MMMM dd, yyyy");

    @ViewById(R.id.week_pager)
    ViewPager weekPager;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    private LocalDate currentWeek = getCurrentWeek();

    @AfterViews
    void init() {
        weekPager.addOnPageChangeListener(this);

        weekPager.setAdapter(new WeeksAdapter(getSupportFragmentManager(), currentWeek));
        weekPager.setCurrentItem(WeeksAdapter.CURRENT_WEEK_POSITION);

        setSupportActionBar(toolbar);
    }

    private LocalDate getCurrentWeek() {
        LocalDate today = new LocalDate();
        return today.minusDays(today.dayOfWeek().get() - 1);
    }

    @Override
    public void onPageSelected(int position) {
        Date monday = currentWeek.plusWeeks(position - WeeksAdapter.CURRENT_WEEK_POSITION).toDate();
        Date sunday = currentWeek.plusWeeks(position - WeeksAdapter.CURRENT_WEEK_POSITION + 1).minusDays(1).toDate();
        setTitle(format(pattern, monday) + " - " + format(pattern, sunday));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.go_today:
                weekPager.setCurrentItem(WeeksAdapter.CURRENT_WEEK_POSITION, false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
