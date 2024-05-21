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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class AdminTaskDetails extends AppCompatActivity {

    private TextView name, text, deadline;
    private Button btnEdit;
    private ImageButton btnBack;
    private SwitchCompat choose;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_task_details);

        name = findViewById(R.id.name);
        text = findViewById(R.id.text);
        deadline = findViewById(R.id.deadline);
        btnEdit = findViewById(R.id.btnEdit);
        btnBack = findViewById(R.id.btnBack);
        choose = findViewById(R.id.choose);

        mRequestQueue = MyVolleySingletonUtil.getInstance(AdminTaskDetails.this).getRequestQueue();

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
                Intent intent = new Intent(AdminTaskDetails.this, AdminAllTasks.class);
                startActivity(intent);
                finish();
            }
        });

        choose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    taskSetMine(taskId);
                } else {
                    taskUnSet(taskId);
                }
            }
        });
    }

    public void taskSetMine(String task_id){

        String url = "http://192.168.43.3:9080/app/task/take";

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
                    Toast.makeText(AdminTaskDetails.this, body, Toast.LENGTH_LONG).show();
                }
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }


    public void taskUnSet(String task_id){

        String url = "http://192.168.43.3:9080/app/task/untake";

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
                    Toast.makeText(AdminTaskDetails.this, body, Toast.LENGTH_LONG).show();
                }
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

}