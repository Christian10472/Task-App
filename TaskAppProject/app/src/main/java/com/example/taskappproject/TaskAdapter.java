package com.example.taskappproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    Context context;
    ArrayList<NewTask> taskArrayList;
    public TaskAdapter(Context context, ArrayList<NewTask> taskArrayList) {
        this.context = context;
        this.taskArrayList = taskArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.task_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewTask task = taskArrayList.get(position);
        holder.tabTask.setText(task.heading);

    }

    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tabTask;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tabTask = itemView.findViewById(R.id.CheckBox);
        }
    }
}
