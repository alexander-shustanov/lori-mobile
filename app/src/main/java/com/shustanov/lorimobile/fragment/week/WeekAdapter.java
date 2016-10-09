package com.shustanov.lorimobile.fragment.week;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.shustanov.lorimobile.data.timeentry.TimeEntry;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.Holder> {
    private final LocalDate monday;

    private SparseArray<List<TimeEntry>> entriesByWeek = new SparseArray<>();


    WeekAdapter(LocalDate monday) {
        this.monday = monday;
        resetEntriesByWeek();
    }

    private void resetEntriesByWeek() {
        for (int i = 1; i <= 7; i++) {
            entriesByWeek.put(i, new ArrayList<>());
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(DayOfWeek_.build(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.dayOfWeek.setDay(monday.plusDays(position));
        holder.dayOfWeek.setItems(entriesByWeek.get(position + 1));
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    void updateEntries(List<TimeEntry> entries) {
        resetEntriesByWeek();
        for (TimeEntry entry : entries) {
            entriesByWeek.get(LocalDate.fromDateFields(entry.getDate()).dayOfWeek().get()).add(entry);
        }
    }

    class Holder extends RecyclerView.ViewHolder {
        private DayOfWeek dayOfWeek;

        Holder(DayOfWeek dayOfWeek) {
            super(dayOfWeek);
            this.dayOfWeek = dayOfWeek;
        }
    }
}
