package com.shustanov.lorimobile;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.joda.time.LocalDate;

import java.util.Date;
import java.util.Locale;

public class Utilities {
    private static final String DAY_MONTH_PATTERN = DateFormat.getBestDateTimePattern(Locale.getDefault(), "MMMM dd");
    private static final String FULL_DATE_PATTERN = DateFormat.getBestDateTimePattern(Locale.getDefault(), "MMMM dd, yyyy");

    public static void hideKeyBoard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static CharSequence printMonthDay(LocalDate day) {
        return DateFormat.format(DAY_MONTH_PATTERN, day.toDate());
    }

    public static CharSequence printFullDate(Date date) {
        return DateFormat.format(FULL_DATE_PATTERN, date);
    }
}
