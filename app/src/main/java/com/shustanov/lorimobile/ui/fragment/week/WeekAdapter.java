package com.shustanov.lorimobile.ui.fragment.week;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.shustanov.lorimobile.data.timeentry.TimeEntry;
import com.shustanov.lorimobile.databinding.IDayOfWeekBinding;
import com.shustanov.lorimobile.databinding.ITimeEntryBinding;
import com.shustanov.lorimobile.view.timeentry.TimeEntryActionListener;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.Holder> {
    private final LocalDate monday;
    private final LayoutInflater inflater;
    private final TimeEntryActionListener listener;

    private SparseArray<List<TimeEntry>> entriesByWeek = new SparseArray<>();

    private List<WeekItem> items = new ArrayList<>();

    WeekAdapter(Context context, LocalDate monday, TimeEntryActionListener listener) {
        inflater = LayoutInflater.from(context);
        this.monday = monday;
        this.listener = listener;
        resetEntriesByWeek();
    }

    private void resetEntriesByWeek() {
        for (int i = 1; i <= 7; i++) {
            entriesByWeek.put(i, new ArrayList<>());
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == WeekItem.DAY) {
            return new DayHolder(IDayOfWeekBinding.inflate(inflater, parent, false));
        }
        return new EntryHolder(ITimeEntryBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).type;
    }

    void updateEntries(List<TimeEntry> entries) {
        resetEntriesByWeek();
        for (TimeEntry entry : entries) {
            entriesByWeek.get(LocalDate.fromDateFields(entry.getDate()).dayOfWeek().get()).add(entry);
        }
        List<WeekItem> items = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            List<TimeEntry> dayEntries = entriesByWeek.get(i);
            items.add(new Day(monday.plusDays(i - 1), i));
            for (TimeEntry entry : dayEntries) {
                items.add(new Entry(entry));
            }
        }

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new WeekItemsDiffCallback(items, this.items));
        this.items = items;
        diffResult.dispatchUpdatesTo(this);
    }


    abstract class Holder<T extends ViewDataBinding, D> extends RecyclerView.ViewHolder {
        T binding;

        Holder(T binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        abstract void bind(D item);
    }

    class EntryHolder extends Holder<ITimeEntryBinding, Entry> {

        EntryHolder(ITimeEntryBinding binding) {
            super(binding);
        }

        @Override
        void bind(Entry item) {
            binding.setTimeEntry(item.timeEntry);
            binding.setListener(listener);
        }
    }

    class DayHolder extends Holder<IDayOfWeekBinding, Day> {

        DayHolder(IDayOfWeekBinding binding) {
            super(binding);
        }

        @Override
        void bind(Day item) {
            binding.setDay(item.date);
        }
    }

    static class WeekItem {
        static final int DAY = 0;
        static final int ENTRY = 1;

        final int type;

        WeekItem(int type) {
            this.type = type;
        }
    }

    static class Day extends WeekItem {

        final LocalDate date;
        final int day;

        Day(LocalDate date, int day) {
            super(DAY);
            this.date = date;
            this.day = day;
        }
    }

    static class Entry extends WeekItem {

        final TimeEntry timeEntry;

        Entry(TimeEntry timeEntry) {
            super(ENTRY);
            this.timeEntry = timeEntry;
        }
    }
}
