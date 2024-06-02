package com.example.bookclubapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.bookclubapp.models.Reward;
import com.example.bookclubapp.utils.MyVolleySingletonUtil;
import com.example.bookclubapp.utils.SpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserMyRewards extends AppCompatActivity {

    private ImageButton btnAdd;
    private ProgressBar progressBar;

    private RequestQueue mRequestQueue;
    private List<Reward> rewardList;
    private List<Integer> idsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnLists = findViewById(R.id.btnLists);

        btnAdd = findViewById(R.id.btnAdd);

        mRequestQueue =  MyVolleySingletonUtil.getInstance(UserMyRewards.this).getRequestQueue();
        progressBar = findViewById(R.id.get_not_progress_bar);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserMyRewards.this));

        rewardList = new ArrayList<>();
        idsList = new ArrayList<>();

        try {
            getRewards();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMyRewards.this, UserMyRewards.class);
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
                List<Reward> rewardListFiltered = new ArrayList<>();
                for (Reward reward : rewardList){
                    if(reward.getName().toLowerCase().contains(newText.toLowerCase()) || String.valueOf(reward.getName()).toLowerCase().contains(newText.toLowerCase())){
                        rewardListFiltered.add(reward);
                    }
                }

                recyclerView.setAdapter(adapter);
                return false;
            }
        });


    }

    public void getRewards() throws JSONException {

        JsonArrayRequest jsonArrayRequest
                = new JsonArrayRequest(Request.Method.POST, "http://192.168.43.3:9080/app/reward/getAll", null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                Log.d("THIS_RESPONSE IS", String.valueOf(response));

                for (int i = 0; i < response.length(); i++) {

                    try{
                        JSONObject responseObject = response.getJSONObject(i);
                        Log.d("REWARD OBJECT IS", String.valueOf(responseObject));
                        Integer id = responseObject.getInt("user_id");
                        Integer user_id = responseObject.getInt("user_id");
                        Reward reward
                                = new reward(responseObject.getInt("reward_id"),
                                responseObject.getString("reward_name"),
                                responseObject.getString("reason"));
                        Log.d("THIS_REWARD", String.valueOf(reward));
                        rewardList.add(reward);
                        idsList.add(id);
                    } catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(UserMyRewards.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }

                SpacingItemDecoration spacingItemDecoration = new SpacingItemDecoration(30);
                recyclerView.addItemDecoration(spacingItemDecoration);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                progressBar.setVisibility(View.GONE);
                Log.i("Reward", volleyError.toString());
                Toast.makeText(UserMyRewards.this, "Failed to get rewards", Toast.LENGTH_LONG).show();

            }
        }){
        };
        mRequestQueue.add(jsonArrayRequest);
    }
}


