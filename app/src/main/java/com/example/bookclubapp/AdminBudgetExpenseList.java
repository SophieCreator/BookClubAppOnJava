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
import com.example.bookclubapp.helpers.ExpenseListRecyclerViewHelper;
import com.example.bookclubapp.models.Expense;
import com.example.bookclubapp.utils.MyVolleySingletonUtil;
import com.example.bookclubapp.utils.SpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminBudgetExpenseList extends AppCompatActivity {

    private ImageButton btnAddFinances;
    Button btnIncomes;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SearchView searchView;
    private RecyclerView.Adapter adapter;
    TextView txtNoExpenses;

    private RequestQueue mRequestQueue;
    private List<Expense> expenseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_budget_expense_list);

        txtNoExpenses = findViewById(R.id.no_expenses);
        btnAddFinances = findViewById(R.id.btnAddFinances);
        btnIncomes = findViewById(R.id.btnIncomes);
        recyclerView = findViewById(R.id.expense_list_recycler_view);
        mRequestQueue =  MyVolleySingletonUtil.getInstance(AdminBudgetExpenseList.this).getRequestQueue();
        progressBar = findViewById(R.id.get_not_progress_bar);
        searchView = findViewById(R.id.expenseSearch);
        // for recycler view:
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminBudgetExpenseList.this));

        expenseList = new ArrayList<>();

        getExpenses();

        btnIncomes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminBudgetExpenseList.this, AdminBudgetIncomeList.class);
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
                List<Expense> expenseListFiltered = new ArrayList<>();
                for (Expense expense : expenseList){
                    if(expense.getName().toLowerCase().contains(newText.toLowerCase()) || String.valueOf(expense.getSum()).toLowerCase().contains(newText.toLowerCase()) || String.valueOf(expense.getDate()).toLowerCase().contains(newText.toLowerCase())){
                        expenseListFiltered.add(expense);
                    }
                }

                adapter = new ExpenseListRecyclerViewHelper(expenseListFiltered, AdminBudgetExpenseList.this);
                recyclerView.setAdapter(adapter);
                return false;
            }
        });

        btnAddFinancesAction();
    }


    public void btnAddFinancesAction(){

        btnAddFinances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddFinanceItem();
            }
        });
    }

    public void goToAddFinanceItem(){
        Intent goToAddFinanceItem = new Intent(AdminBudgetExpenseList.this, AdminAddExpense.class);
        startActivity(goToAddFinanceItem);
    }


    public void getExpenses(){

        HashMap<String, String> params = new HashMap<String, String>();
        //params.put("token", "email");

        JsonArrayRequest jsonArrayRequest
                = new JsonArrayRequest(Request.Method.POST, "http://192.168.43.3:9080/app/expense/getAll", null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                Log.d("THIS_RESPONSE IS", String.valueOf(response));

                for (int i = 0; i < response.length(); i++) {

                    try{
                        JSONObject responseObject = response.getJSONObject(i);
                        Log.d("EXPENSE OBJECT IS", String.valueOf(responseObject));
                        LocalDate date = LocalDate.parse((CharSequence) responseObject.get("date"));
                        Log.d("THIS_DATE", String.valueOf(date));
                        Expense expense
                                = new Expense(responseObject.getInt("income_id"),
                                responseObject.getString("name"),
                                responseObject.getString("url"),
                                date,
                                responseObject.getInt("sum"),
                                responseObject.getString("is_regular"));
                        Log.d("THIS_EXPENSE", String.valueOf(expense));
                        expenseList.add(expense);

                    } catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(AdminBudgetExpenseList.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }

                adapter = new ExpenseListRecyclerViewHelper(expenseList, AdminBudgetExpenseList.this);
                SpacingItemDecoration spacingItemDecoration = new SpacingItemDecoration(30);
                recyclerView.addItemDecoration(spacingItemDecoration);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                progressBar.setVisibility(View.GONE);
                txtNoExpenses.setVisibility(View.VISIBLE);
                Log.i("Expense", volleyError.toString());
                Toast.makeText(AdminBudgetExpenseList.this, "Failed to get expenses", Toast.LENGTH_LONG).show();

            }
        }){
        };
        mRequestQueue.add(jsonArrayRequest);
    }
}

