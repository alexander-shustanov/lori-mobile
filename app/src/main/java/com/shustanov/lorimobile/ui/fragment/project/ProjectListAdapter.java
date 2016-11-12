package com.shustanov.lorimobile.ui.fragment.project;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.shustanov.lorimobile.data.project.Project;
import com.shustanov.lorimobile.databinding.IProjectBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * ProjectListAdapter
 * </p>
 * alexander.shustanov on 10.11.16
 */
public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.Holder> {

    private List<Project> projects = new ArrayList<>();

    private final Context context;
    private final Listener listener;
    private final LayoutInflater inflater;

    public ProjectListAdapter(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        IProjectBinding projectBinding = IProjectBinding.inflate(inflater, parent, false);
        return new Holder(projectBinding);
    }


    public void setProjects(List<Project> projects) {
        ProjectsDiffCallback diffCallback = new ProjectsDiffCallback(this.projects, projects);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.projects = projects;
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.binding.setProject(projects.get(position));
        holder.binding.setListener(listener);
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        private IProjectBinding binding;

        Holder(IProjectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface Listener {
        void addTimeEntry(Project project);
    }
}
