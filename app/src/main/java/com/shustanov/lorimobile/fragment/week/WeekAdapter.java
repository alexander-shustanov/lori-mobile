package com.shustanov.lorimobile.fragment.week;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.joda.time.LocalDate;

class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.Holder> {

    private final LocalDate monday;

    WeekAdapter(Context context, LocalDate monday) {
        this.monday = monday;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(DayOfWeek_.build(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.dayOfWeek.setDay(monday.plusDays(position));
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    class Holder extends RecyclerView.ViewHolder {
        private DayOfWeek dayOfWeek;

        Holder(DayOfWeek dayOfWeek) {
            super(dayOfWeek);
            this.dayOfWeek = dayOfWeek;
        }
    }
}
