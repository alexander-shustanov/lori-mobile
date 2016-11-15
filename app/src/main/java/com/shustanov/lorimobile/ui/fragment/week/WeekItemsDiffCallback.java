package com.shustanov.lorimobile.ui.fragment.week;

import android.support.v7.util.DiffUtil;

import java.util.List;

class WeekItemsDiffCallback extends DiffUtil.Callback {
    private final List<WeekAdapter.WeekItem> newItems;
    private final List<WeekAdapter.WeekItem> oldItems;

    WeekItemsDiffCallback(List<WeekAdapter.WeekItem> newItems, List<WeekAdapter.WeekItem> oldItems) {
        this.newItems = newItems;
        this.oldItems = oldItems;
    }

    @Override
    public int getOldListSize() {
        return oldItems.size();
    }

    @Override
    public int getNewListSize() {
        return newItems.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        WeekAdapter.WeekItem oldItem = oldItems.get(oldItemPosition);
        WeekAdapter.WeekItem newItem = newItems.get(newItemPosition);
        if (oldItem.type != newItem.type) {
            return false;
        }
        if (oldItem.type == WeekAdapter.WeekItem.DAY) {
            return ((WeekAdapter.Day) oldItem).day == ((WeekAdapter.Day) newItem).day;
        }
        return ((WeekAdapter.Entry) oldItem).timeEntry.getId().equals(((WeekAdapter.Entry) newItem).timeEntry.getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        WeekAdapter.WeekItem oldItem = oldItems.get(oldItemPosition);
        WeekAdapter.WeekItem newItem = newItems.get(newItemPosition);
        return oldItem.type == WeekAdapter.WeekItem.DAY;
    }
}
