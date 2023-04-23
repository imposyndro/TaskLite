package com.example.tasklite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasklite.Entities.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {
    private List<Task> reminders = new ArrayList<>();
    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_view, parent, false);
        return new TaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        Task currentReminder = reminders.get(position);
        holder.title.setText(currentReminder.getName());
        holder.description.setText(currentReminder.getDescription());
        holder.priority.setText(String.valueOf(currentReminder.getPriority()));
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    public void setTasks(List<Task> reminders){
        this.reminders = reminders;
        notifyDataSetChanged();
    }

    public Task getTaskAt(int position){
        return reminders.get(position);
    }

    class TaskHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView description;
        private TextView priority;

        public TaskHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.r_title);
            description = itemView.findViewById(R.id.description);
            priority = itemView.findViewById(R.id.priority);
        }
    }
}
