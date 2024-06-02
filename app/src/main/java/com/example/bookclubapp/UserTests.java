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
import com.example.bookclubapp.models.Test;
import com.example.bookclubapp.models.User;
import com.example.bookclubapp.utils.MyVolleySingletonUtil;
import com.example.bookclubapp.utils.SpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserTests extends AppCompatActivity {

    private Button btnNews, btnTests, btnBudget, btnLists;
    private ImageButton btnAdd;
    Button btnAllTests, btnMyTests;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SearchView searchView;
    private RecyclerView.Adapter adapter;
    TextView txtNoTests;

    private RequestQueue mRequestQueue;
    private List<Test> testList;
    private List<Integer> idsList;
    private Map<Integer, String> userIdsAndLogins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnLists = findViewById(R.id.btnLists);

        btnAdd = findViewById(R.id.btnAdd);

        mRequestQueue =  MyVolleySingletonUtil.getInstance(UserTests.this).getRequestQueue();
        progressBar = findViewById(R.id.get_not_progress_bar);
        // for recycler view:
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserTests.this));

        testList = new ArrayList<>();
        idsList = new ArrayList<>();

        try {
            getTests();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        btnBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserTests.this, AdminBudgetIncomeList.class);
                startActivity(intent);
                finish();
            }
        });

        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserTests.this, AdminNews.class);
                startActivity(intent);
            }
        });

        btnLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserTests.this, AdminListsBooks.class);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserTests.this, UserTests.class);
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
                List<Test> testListFiltered = new ArrayList<>();
                for (Test test : testList){
                    if(test.getTest_name().toLowerCase().contains(newText.toLowerCase()) || String.valueOf(test.getTest_name()).toLowerCase().contains(newText.toLowerCase())){
                        testListFiltered.add(test);
                    }
                }

                recyclerView.setAdapter(adapter);
                return false;
            }
        });


    }

    public void getTests() throws JSONException {

        JsonArrayRequest jsonArrayRequest
                = new JsonArrayRequest(Request.Method.POST, "http://192.168.43.3:9080/app/test/getAll", null, new Response.Listener<JSONArray>() {
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
                        Integer id = responseObject.getInt("user_id");
                        Integer user_id = responseObject.getInt("user_id");
                        Test test
                                = new test(responseObject.getInt("test_id"),
                                responseObject.getString("test_name"),
                                responseObject.getString("is_done"));
                        Log.d("THIS_TEST", String.valueOf(test));
                        testList.add(test);
                        idsList.add(id);
                    } catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(UserTests.this, "Something went wrong", Toast.LENGTH_LONG).show();
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
                Log.i("Test", volleyError.toString());
                Toast.makeText(UserTests.this, "Failed to get tests", Toast.LENGTH_LONG).show();

            }
        }){
        };
        mRequestQueue.add(jsonArrayRequest);
    }
}

