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
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bookclubapp.helpers.TaskListRecyclerViewHelper;
import com.example.bookclubapp.models.Task;
import com.example.bookclubapp.models.User;
import com.example.bookclubapp.utils.MyVolleySingletonUtil;
import com.example.bookclubapp.utils.SpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminAllTasks extends AppCompatActivity {

    private ImageButton btnAdd;
    Button btnAllTasks;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SearchView searchView;
    private RecyclerView.Adapter adapter;
    TextView txtNoTasks;

    private RequestQueue mRequestQueue;
    private List<Task> taskList;
    private List<Integer> idsList;
    private Map<Integer, String> userIdsAndLogins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_tasks);

        txtNoTasks = findViewById(R.id.no_tasks);
        btnAdd = findViewById(R.id.btnAdd);

        recyclerView = findViewById(R.id.task_list_recycler_view);
        mRequestQueue =  MyVolleySingletonUtil.getInstance(AdminAllTasks.this).getRequestQueue();
        progressBar = findViewById(R.id.get_not_progress_bar);
        searchView = findViewById(R.id.taskSearch);
        btnAllTasks = findViewById(R.id.btnAllTasks);
        // for recycler view:
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminAllTasks.this));

        taskList = new ArrayList<>();
        idsList = new ArrayList<>();

        try {
            getTasks();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        btnAllTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAllTasks.this, AdminAllTasks.class);
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

                adapter = new TaskListRecyclerViewHelper(taskListFiltered, AdminAllTasks.this);
                recyclerView.setAdapter(adapter);
                return false;
            }
        });


    }

    public void getTasks() throws JSONException {

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
                        Integer id = responseObject.getInt("user_id");
                        Log.d("THIS_DATE", String.valueOf(date));
                        Integer user_id = responseObject.getInt("user_id");
                        String login = getLoginById(user_id);
                        Log.d("THIS_TASK_LOGIN", login);
                        Task task
                                = new Task(responseObject.getInt("task_id"),
                                login,
                                responseObject.getString("task_name"),
                                responseObject.getString("task_text"),
                                date,
                                responseObject.getString("is_done"));
                        Log.d("THIS_TASK", String.valueOf(task));
                        taskList.add(task);
                        idsList.add(id);
                    } catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(AdminAllTasks.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }

                adapter = new TaskListRecyclerViewHelper(taskList, AdminAllTasks.this);
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
                Toast.makeText(AdminAllTasks.this, "Failed to get tasks", Toast.LENGTH_LONG).show();

            }
        }){
        };
        mRequestQueue.add(jsonArrayRequest);
    }

    public String getLoginById(Integer id) throws JSONException {
        String[] loginInfo = {"Задача свободна"};
        if (id != -1) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("user_id", id.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://192.168.43.3:9080/app/user/get", new JSONObject(params), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("onResponse", response.toString());
                    try {

                        loginInfo[0] = (String) response.get("login");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String body = "";
                    int status = error.networkResponse.statusCode;
                }
                });
                mRequestQueue.add(jsonObjectRequest);
            }
        return loginInfo[0];
    }



}
