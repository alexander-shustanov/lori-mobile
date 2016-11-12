package com.shustanov.lorimobile.ui.fragment.task;

import android.support.v7.util.DiffUtil;

import com.shustanov.lorimobile.data.task.Task;

import java.util.List;
import java.util.Objects;

class TasksDiffCallback extends DiffUtil.Callback {
    private final List<Task> newItems;
    private final List<Task> oldItems;

    TasksDiffCallback(List<Task> newItems, List<Task> oldItems) {
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
        Task oldItem = oldItems.get(oldItemPosition);
        Task newItem = newItems.get(newItemPosition);
        return Objects.equals(oldItem.getId(), newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Task oldItem = oldItems.get(oldItemPosition);
        Task newItem = newItems.get(newItemPosition);
        return oldItem.equals(newItem);
    }
}
