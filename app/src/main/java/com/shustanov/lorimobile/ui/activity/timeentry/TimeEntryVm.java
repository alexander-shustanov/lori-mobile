package com.shustanov.lorimobile.ui.activity.timeentry;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.android.databinding.library.baseAdapters.BR;
import com.shustanov.lorimobile.data.project.Project;
import com.shustanov.lorimobile.data.task.Task;
import com.shustanov.lorimobile.data.timeentry.TimeEntry;

import org.joda.time.LocalDate;

import java.util.List;

public class TimeEntryVm extends BaseObservable {
    private final Listener listener;
    private final ArrayAdapter<Project> projectAdapter;
    private final ArrayAdapter<Task> taskAdapter;
    private LocalDate date = new LocalDate();
    private Task task;
    private Project project;
    private String description;

    private int hours = 0, minutes = 0;

    private int projectPosition = -1, taskPosition = -1;

    public TimeEntryVm(Listener listener, ArrayAdapter<Project> projectAdapter, ArrayAdapter<Task> taskAdapter) {
        this.listener = listener;
        this.projectAdapter = projectAdapter;
        this.taskAdapter = taskAdapter;
    }

    public void setTask(Task task) {
        this.task = task;
        listener.onTaskChanged(task);
        setTaskPosition(taskAdapter.getPosition(task));
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

    @Bindable
    public int getProjectPosition() {
        return projectPosition;
    }

    public void setProjectPosition(int projectPosition) {
        if(this.projectPosition != projectPosition) {
            this.projectPosition = projectPosition;
            if (projectPosition != -1) {
                setProject(projectAdapter.getItem(projectPosition));
            }
            notifyPropertyChanged(BR.projectPosition);
        }
    }

    @Bindable
    public int getTaskPosition() {
        return taskPosition;
    }

    public void setTaskPosition(int taskPosition) {
        if (this.taskPosition != taskPosition) {
            this.taskPosition = taskPosition;
            if(taskPosition != -1) {
                setTask(taskAdapter.getItem(taskPosition));
            }
            notifyPropertyChanged(BR.taskPosition);
        }
    }

    public void addProjects(List<Project> projects) {
        projectAdapter.addAll(projects);
        if(project != null) {
            setProject(project);
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

    public void setProject(Project project) {
        this.project = project;
        listener.onProjectChanged(project);
        setProjectPosition(projectAdapter.getPosition(project));
    }

    public void addTasks(List<Task> tasks) {
        taskAdapter.addAll(tasks);
        setTask(task);
    }

    public Task getTask() {
        return task;
    }

    public void clearTasks() {
        taskAdapter.clear();
    }

    public interface Listener {
        void onProjectChanged(Project project);

        void onTaskChanged(Task task);
    }
}
