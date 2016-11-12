package com.shustanov.lorimobile.ui.fragment.project;

import android.support.v7.util.DiffUtil;

import com.shustanov.lorimobile.data.project.Project;

import java.util.List;

/**
 * ProjectsDiffCallback
 * </p>
 * alexander.shustanov on 10.11.16
 */
public class ProjectsDiffCallback extends DiffUtil.Callback {

    private final List<Project> oldItems;
    private final List<Project> newItems;

    public ProjectsDiffCallback(List<Project> oldItems, List<Project> newItems) {
        this.oldItems = oldItems;
        this.newItems = newItems;
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
        return oldItems.get(oldItemPosition).getId().equals(newItems.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItems.get(oldItemPosition).equals(newItems.get(newItemPosition));
    }
}
