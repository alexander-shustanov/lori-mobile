package com.shustanov.lorimobile.fragment.week;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shustanov.lorimobile.R;
import com.shustanov.lorimobile.data.timeentry.TimeEntry;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@EViewGroup(R.layout.v_day_of_week)
public class DayOfWeek extends LinearLayout {
    @ViewById(R.id.day)
    TextView day;
    @ViewById(R.id.entries_list)
    RecyclerView entriesList;
    private EntriesAdapter adapter;

    public DayOfWeek(Context context) {
        super(context);
        setOrientation(VERTICAL);
    }

    @AfterViews
    void init() {
        adapter = new EntriesAdapter();
        entriesList.setAdapter(adapter);
    }

    void setDay(LocalDate day) {
        this.day.setText(DateFormat.format(DateFormat.getBestDateTimePattern(Locale.getDefault(),"MMMM dd"), day.toDate()));
    }

    public void setItems(List<TimeEntry> entries) {
        adapter.setEntries(entries);
    }

    private class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.Holder> {
        private List<TimeEntry> entries = new ArrayList<>();

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return entries.size();
        }

        public void setEntries(List<TimeEntry> entries) {
            this.entries = entries;
            notifyDataSetChanged();
        }

        class Holder extends RecyclerView.ViewHolder {
            public Holder(View itemView) {
                super(itemView);
            }
        }
    }
}
