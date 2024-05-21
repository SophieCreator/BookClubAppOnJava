package com.example.bookclubapp.helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookclubapp.AdminMyTaskDetails;
import com.example.bookclubapp.R;
import com.example.bookclubapp.models.Task;

import java.util.List;

public class MyTaskListRecyclerViewHelper extends RecyclerView.Adapter<MyTaskListRecyclerViewHelper.TaskListViewHolder> {

    private List<Task> taskListItems;
    private Context context;

    public MyTaskListRecyclerViewHelper(List<Task> taskListItems, Context context){
        this.taskListItems = taskListItems;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_task_list, parent, false);
        return new TaskListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListViewHolder holder, int position) {
        Task task = this.taskListItems.get(position);

        holder.taskName.setText(task.getTask_name());
        holder.taskText.setText(String.valueOf(task.getTask_text()));
        holder.taskDeadline.setText(task.getDeadline().toString());

        holder.taskItemLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, "You clicked: " + task.getTask_id(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, AdminMyTaskDetails.class);
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
        public TextView taskId, taskName, taskText, taskDeadline;
        private LinearLayout taskItemLayout;
        public TaskListViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.task_name);
            taskText = itemView.findViewById(R.id.task_text);
            taskDeadline = itemView.findViewById(R.id.deadline);
            taskItemLayout = itemView.findViewById(R.id.taskItemLayout);
        }
    }
}


