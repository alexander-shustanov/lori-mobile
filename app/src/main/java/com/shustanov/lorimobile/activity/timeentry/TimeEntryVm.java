package com.shustanov.lorimobile.activity.timeentry;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.widget.EditText;

import com.android.databinding.library.baseAdapters.BR;
import com.shustanov.lorimobile.data.task.Task;
import com.shustanov.lorimobile.data.timeentry.TimeEntry;

import org.joda.time.LocalDate;

public class TimeEntryVm extends BaseObservable {
    private Task task;

    private LocalDate date = new LocalDate();

    private String description;

    private int hours = 0, minutes = 0;

    @Bindable
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
        notifyPropertyChanged(BR.task);
    }

    @Bindable
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Bindable
    public int getHours() {
        return this.hours;
    }

    @Bindable
    public int getMinutes() {
        return this.minutes;
    }

    public void setHours(int hours) {
        if(this.hours != hours) {
            this.hours = hours;
        }
    }

    public void setMinutes(int minutes) {
        if(this.minutes != minutes) {
            this.minutes = minutes % 60;
            int hours = minutes / 60;
            if(hours != 0) {
                this.hours += hours;
                notifyPropertyChanged(BR.hours);
                notifyPropertyChanged(BR.minutes);
            }
        }
    }

    TimeEntry createTimeEntry() {
        return new TimeEntry(date.toDate(), hours * 60 + minutes, "new", task.getId(), task.getName(), description);
    }

    @BindingAdapter(value = "android:text")
    public static void bindHours(EditText text, int val) {
        String str = String.valueOf(val);
        text.setText(str);
        text.setSelection(str.length());
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static int inverseBindHours(EditText text) {
        String s = text.getText().toString();
        if(s.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(s);
    }

    void fill(TimeEntry timeEntry) {
        this.date = LocalDate.fromDateFields(timeEntry.getDate());
        this.description = timeEntry.getDescription();
        this.hours = timeEntry.getTimeInMinutes() / 60;
        this.minutes = timeEntry.getTimeInMinutes() % 60;
        notifyChange();
    }
}
