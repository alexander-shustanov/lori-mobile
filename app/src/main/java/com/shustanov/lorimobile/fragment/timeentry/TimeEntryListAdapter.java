package com.shustanov.lorimobile.fragment.timeentry;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class TimeEntryListAdapter extends RecyclerView.Adapter<TimeEntryListAdapter.Holder> {

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    protected class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);
        }
    }
}
