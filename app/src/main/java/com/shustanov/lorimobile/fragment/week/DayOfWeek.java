package com.shustanov.lorimobile.fragment.week;

import android.content.Context;
import android.text.format.DateFormat;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shustanov.lorimobile.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.joda.time.LocalDate;

import java.util.Locale;

@EViewGroup(R.layout.v_day_of_week)
public class DayOfWeek extends LinearLayout {
    @ViewById(R.id.day)
    TextView day;

    public DayOfWeek(Context context) {
        super(context);
        setOrientation(VERTICAL);
    }

    void setDay(LocalDate day) {
        this.day.setText(DateFormat.format(DateFormat.getBestDateTimePattern(Locale.getDefault(),"MMMM dd"), day.toDate()));
    }
}
