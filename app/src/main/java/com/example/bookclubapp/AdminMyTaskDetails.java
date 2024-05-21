package com.example.bookclubapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bookclubapp.utils.MyVolleySingletonUtil;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class AdminMyTaskDetails extends AppCompatActivity {

    private TextView name, text, deadline;
    private Button btnEdit;
    private SwitchCompat done;
    private ImageButton btnBack;
    private RequestQueue mRequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_my_task_details);

        name = findViewById(R.id.name);
        text = findViewById(R.id.text);
        deadline = findViewById(R.id.deadline);
        btnEdit = findViewById(R.id.btnEdit);
        btnBack = findViewById(R.id.btnBack);
        done = findViewById(R.id.done);

        mRequestQueue = MyVolleySingletonUtil.getInstance(AdminMyTaskDetails.this).getRequestQueue();

        String nameText = getIntent().getStringExtra("name");
        String taskId = getIntent().getStringExtra("taskId");
        String textText = getIntent().getStringExtra("text");
        String deadlineText = getIntent().getStringExtra("deadline");

        name.setText(nameText);
        text.setText(textText);
        deadline.setText(deadlineText);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMyTaskDetails.this, AdminMyTask.class);
                startActivity(intent);
                finish();
            }
        });

        done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    taskSetDone(taskId);
                } else {
                    taskUnSetDone(taskId);
                }
            }
        });
    }

    public void taskSetDone(String task_id){

        String url = "http://192.168.43.3:9080/app/task/done";

        Map<String, String> params = new HashMap<String, String>();
        params.put("user_id", String.valueOf(4));
        params.put("task_id", task_id);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("onResponse", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body = "";
                int status = error.networkResponse.statusCode;
                Log.d("onErrorResponse", String.valueOf(status));

                if (error.networkResponse.data != null) {
                    try {
                        body = new String(error.networkResponse.data, "UTF-8");
                        Log.d("onErrorResponse", body);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(AdminMyTaskDetails.this, body, Toast.LENGTH_LONG).show();
                }
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }


    public void taskUnSetDone(String task_id){

        String url = "http://192.168.43.3:9080/app/task/undone";

        Map<String, String> params = new HashMap<String, String>();
        params.put("user_id", String.valueOf(4));
        params.put("task_id", task_id);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("onResponse", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body = "";
                int status = error.networkResponse.statusCode;
                Log.d("onErrorResponse", String.valueOf(status));

                if (error.networkResponse.data != null) {
                    try {
                        body = new String(error.networkResponse.data, "UTF-8");
                        Log.d("onErrorResponse", body);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(AdminMyTaskDetails.this, body, Toast.LENGTH_LONG).show();
                }
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }
}