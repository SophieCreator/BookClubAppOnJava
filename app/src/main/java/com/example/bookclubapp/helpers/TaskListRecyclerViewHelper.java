package com.example.bookclubapp.helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bookclubapp.AdminTaskDetails;
import com.example.bookclubapp.R;
import com.example.bookclubapp.models.Task;

import java.util.List;

public class TaskListRecyclerViewHelper extends RecyclerView.Adapter<TaskListRecyclerViewHelper.TaskListViewHolder> {

    private List<Task> taskListItems;
    private Context context;

    public TaskListRecyclerViewHelper(List<Task> taskListItems, Context context){
        this.taskListItems = taskListItems;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_task_list, parent, false);
        return new TaskListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListViewHolder holder, int position) {
        Task task = this.taskListItems.get(position);

        holder.hwoDoes.setVisibility(View.GONE);
        /*

        if (task.getUser_id() == -1){
            holder.taskUser.setVisibility(View.GONE);
        } else {
            holder.taskUser.setText(task.getLogin());
        }

         */


        holder.taskName.setText(task.getTask_name());
        holder.taskText.setText(String.valueOf(task.getTask_text()));
        holder.taskDeadline.setText(task.getDeadline().toString());

        holder.taskItemLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, "You clicked: " + task.getTask_id(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, AdminTaskDetails.class);
                intent.putExtra("taskId", String.valueOf(task.getTask_id()));
                intent.putExtra("name", task.getTask_name());
                intent.putExtra("text", String.valueOf(task.getTask_text()));
                intent.putExtra("deadline", String.valueOf(task.getDeadline().toString()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return taskListItems.size();
    }

    public class TaskListViewHolder extends RecyclerView.ViewHolder{
        public TextView taskId, taskName, taskText, taskDeadline, taskUser;
        private LinearLayout taskItemLayout, hwoDoes;
        public TaskListViewHolder(@NonNull View itemView) {
            super(itemView);
            taskUser = itemView.findViewById(R.id.user);
            taskName = itemView.findViewById(R.id.task_name);
            taskText = itemView.findViewById(R.id.task_text);
            taskDeadline = itemView.findViewById(R.id.deadline);
            taskItemLayout = itemView.findViewById(R.id.taskItemLayout);
            hwoDoes = itemView.findViewById(R.id.hwoDoes);
        }
    }
}


