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
import com.example.bookclubapp.helpers.IncomeListRecyclerViewHelper;
import com.example.bookclubapp.models.Income;
import com.example.bookclubapp.utils.MyVolleySingletonUtil;
import com.example.bookclubapp.utils.SpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminBudgetIncomeList extends AppCompatActivity {

    private ImageButton btnAdd;
    private Button btnExpenses, btnMore, btnTask;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SearchView searchView;
    private RecyclerView.Adapter adapter;
    TextView txtNoIncomes;

    private RequestQueue mRequestQueue;
    private List<Income> incomeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_budget_income_list);

        txtNoIncomes = findViewById(R.id.no_incomes);
        btnAdd = findViewById(R.id.btnAdd);
        btnExpenses = findViewById(R.id.btnExpenses);
        recyclerView = findViewById(R.id.income_list_recycler_view);
        mRequestQueue =  MyVolleySingletonUtil.getInstance(AdminBudgetIncomeList.this).getRequestQueue();
        progressBar = findViewById(R.id.get_not_progress_bar);
        searchView = findViewById(R.id.incomeSearch);
        btnMore = findViewById(R.id.btnMoreAboutBudget);
        btnTask = findViewById(R.id.btnTasks);
        // for recycler view:
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminBudgetIncomeList.this));

        incomeList = new ArrayList<>();

        getIncomes();

        btnExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminBudgetIncomeList.this, AdminBudgetExpenseList.class);
                startActivity(intent);
                finish();
            }
        });

        btnTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminBudgetIncomeList.this, AdminMyTask.class);
                startActivity(intent);
                finish();
            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminBudgetIncomeList.this, AdminMoreAboutBudget.class);
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
                List<Income> incomeListFiltered = new ArrayList<>();
                for (Income income : incomeList){
                    if(income.getName().toLowerCase().contains(newText.toLowerCase()) || String.valueOf(income.getAmount()).toLowerCase().contains(newText.toLowerCase()) || String.valueOf(income.getDate_get()).toLowerCase().contains(newText.toLowerCase())){
                        incomeListFiltered.add(income);
                    }
                }

                adapter = new IncomeListRecyclerViewHelper(incomeListFiltered, AdminBudgetIncomeList.this);
                recyclerView.setAdapter(adapter);
                return false;
            }
        });

        btnAddAction();
    }


    public void btnAddAction(){

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddFinanceItem();
            }
        });
    }

    public void goToAddFinanceItem(){
        Intent goToAddFinanceItem = new Intent(AdminBudgetIncomeList.this, AdminAddIncome.class);
        startActivity(goToAddFinanceItem);
    }


    public void getIncomes(){

        HashMap<String, String> params = new HashMap<String, String>();
        //params.put("token", "email");

        JsonArrayRequest jsonArrayRequest
                = new JsonArrayRequest(Request.Method.POST, "http://192.168.43.3:9080/app/income/getAll", null, new Response.Listener<JSONArray>() {
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
                        LocalDate date = LocalDate.parse((CharSequence) responseObject.get("date_get"));
                        Log.d("THIS_DATE", String.valueOf(date));
                        Income income
                                = new Income(responseObject.getInt("income_id"),
                                responseObject.getString("name"),
                                responseObject.getInt("amount"),
                                date);
                        Log.d("THIS_INCOME", String.valueOf(income));
                        incomeList.add(income);

                    } catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(AdminBudgetIncomeList.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }

                adapter = new IncomeListRecyclerViewHelper(incomeList, AdminBudgetIncomeList.this);
                SpacingItemDecoration spacingItemDecoration = new SpacingItemDecoration(30);
                recyclerView.addItemDecoration(spacingItemDecoration);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                progressBar.setVisibility(View.GONE);
                txtNoIncomes.setVisibility(View.VISIBLE);
                Log.i("Income", volleyError.toString());
                Toast.makeText(AdminBudgetIncomeList.this, "Failed to get incomes", Toast.LENGTH_LONG).show();

            }
        }){
        };
        mRequestQueue.add(jsonArrayRequest);
    }


}