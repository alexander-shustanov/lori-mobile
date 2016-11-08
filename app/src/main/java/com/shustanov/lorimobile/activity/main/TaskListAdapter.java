package com.shustanov.lorimobile.activity.main;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shustanov.lorimobile.R;
import com.shustanov.lorimobile.data.task.Task;
import com.shustanov.lorimobile.databinding.ITaskBinding;

import java.util.ArrayList;
import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.Holder> {
    private final LayoutInflater inflater;
    private Listener listener = null;

    private List<Task> tasks = new ArrayList<>();

    TaskListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflater.inflate(R.layout.i_task, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.binding.setTask(getTask(position));
        holder.binding.setListener(listener);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    private Task getTask(int position) {
        return tasks.get(position);
    }

    void setTasks(List<Task> tasks) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new TasksDiffCallback(tasks, this.tasks));
        this.tasks = tasks;
        diffResult.dispatchUpdatesTo(this);
    }

    void setListener(Listener listener) {
        this.listener = listener;
    }

    class Holder extends RecyclerView.ViewHolder {
        ITaskBinding binding;

        Holder(View itemView) {
            super(itemView);
            binding = ITaskBinding.bind(itemView);
        }
    }

    public interface Listener {
        void addTimeEntry(Task task);
    }
}
