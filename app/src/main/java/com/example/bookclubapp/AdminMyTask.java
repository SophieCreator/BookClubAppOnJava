package com.example.bookclubapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.bookclubapp.helpers.MyTaskListRecyclerViewHelper;
import com.example.bookclubapp.models.Task;
import com.example.bookclubapp.utils.MyVolleySingletonUtil;
import com.example.bookclubapp.utils.SpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminMyTask extends AppCompatActivity {

    private ImageButton btnAdd;
    Button btnAllTasks;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SearchView searchView;
    private RecyclerView.Adapter adapter;
    TextView txtNoTasks;

    private RequestQueue mRequestQueue;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_my_task);

        txtNoTasks = findViewById(R.id.no_tasks);
        btnAdd = findViewById(R.id.btnAdd);

        recyclerView = findViewById(R.id.task_list_recycler_view);
        mRequestQueue =  MyVolleySingletonUtil.getInstance(AdminMyTask.this).getRequestQueue();
        progressBar = findViewById(R.id.get_not_progress_bar);
        searchView = findViewById(R.id.taskSearch);
        btnAllTasks = findViewById(R.id.btnAllTasks);
        // for recycler view:
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminMyTask.this));

        taskList = new ArrayList<>();

        try {
            getTasks(2);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        btnAllTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMyTask.this, AdminAllTasks.class);
                startActivity(intent);
                finish();
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Task> taskListFiltered = new ArrayList<>();
                for (Task task : taskList){
                    if(task.getTask_name().toLowerCase().contains(newText.toLowerCase()) || String.valueOf(task.getTask_text()).toLowerCase().contains(newText.toLowerCase())){
                        taskListFiltered.add(task);
                    }
                }

                adapter = new MyTaskListRecyclerViewHelper(taskListFiltered, AdminMyTask.this);
                recyclerView.setAdapter(adapter);
                return false;
            }
        });


    }

    public void getTasks(int user_id) throws JSONException {

        JsonArrayRequest jsonArrayRequest
                = new JsonArrayRequest(Request.Method.POST, "http://192.168.43.3:9080/app/task/getAll", null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                Log.d("THIS_RESPONSE IS", String.valueOf(response));

                for (int i = 0; i < response.length(); i++) {

                    try{
                        JSONObject responseObject = response.getJSONObject(i);
                        Log.d("INCOME OBJECT IS", String.valueOf(responseObject));
                        LocalDate date = LocalDate.parse((CharSequence) responseObject.get("deadline"));
                        Log.d("THIS_DATE", String.valueOf(date));
                        Task task
                                = new Task(responseObject.getInt("task_id"),
                                responseObject.getInt("user_id"),
                                responseObject.getString("task_name"),
                                responseObject.getString("task_text"),
                                date,
                                responseObject.getString("is_done"));
                        Log.d("THIS_INCOME", String.valueOf(task));
                        taskList.add(task);

                    } catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(AdminMyTask.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }

                adapter = new MyTaskListRecyclerViewHelper(taskList, AdminMyTask.this);
                SpacingItemDecoration spacingItemDecoration = new SpacingItemDecoration(30);
                recyclerView.addItemDecoration(spacingItemDecoration);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                progressBar.setVisibility(View.GONE);
                txtNoTasks.setVisibility(View.VISIBLE);
                Log.i("Task", volleyError.toString());
                Toast.makeText(AdminMyTask.this, "Failed to get tasks", Toast.LENGTH_LONG).show();

            }
        }){
        };
        mRequestQueue.add(jsonArrayRequest);
    }


}