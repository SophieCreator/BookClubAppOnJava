package com.example.bookclubapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.bookclubapp.helpers.ExpenseListRecyclerViewHelper;
import com.example.bookclubapp.helpers.IncomeListRecyclerViewHelper;
import com.example.bookclubapp.models.Expense;
import com.example.bookclubapp.models.Income;
import com.example.bookclubapp.utils.MyVolleySingletonUtil;
import com.example.bookclubapp.utils.SpacingItemDecoration;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminMoreAboutBudget extends AppCompatActivity {

    ImageButton btnBack;
    BarChart monthChart;
    List<String> XValues = Arrays.asList("Доходы", "Расходы", "Прибыль");
    List<Income> incomeList = new ArrayList<>();
    List<Expense> expenseList = new ArrayList<>();
    private RequestQueue mRequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_more_about_budget);

        monthChart = findViewById(R.id.monthChart);
        monthChart.getAxisRight().setDrawLabels(false);
        mRequestQueue =  MyVolleySingletonUtil.getInstance(AdminMoreAboutBudget.this).getRequestQueue();

        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMoreAboutBudget.this, AdminBudgetIncomeList.class);
                startActivity(intent);
            }
        });

        getIncome();
        getExpense();

        ArrayList<BarEntry> entries = new ArrayList<>();


        entries.add(new BarEntry(0, 10000F));
        entries.add(new BarEntry(1, 6000));
        entries.add(new BarEntry(2, 4000));

        YAxis yAxis = monthChart.getAxisLeft();
        yAxis.setAxisMaximum(0F);
        yAxis.setAxisMaximum(10050F);
        yAxis.setAxisLineWidth(2F);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);

        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        BarData barData = new BarData(dataSet);
        monthChart.setData(barData);
        monthChart.getDescription().setEnabled(false);

        monthChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(XValues));
        monthChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        monthChart.getXAxis().setGranularity(1f);
        monthChart.getXAxis().setGranularityEnabled(true);

    }

    public void getIncome(){

        JsonArrayRequest jsonArrayRequest
                = new JsonArrayRequest(Request.Method.POST, "http://192.168.43.3:9080/app/income/getAll", null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {

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
                        Toast.makeText(AdminMoreAboutBudget.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }
                Float sum = 0F;
                for (Income income : incomeList){
                    sum += income.getAmount();
                }
                Log.d("THE_SUM_I_IS", sum.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                Log.i("Income", volleyError.toString());
                Toast.makeText(AdminMoreAboutBudget.this, "Failed to get incomes", Toast.LENGTH_LONG).show();

            }
        }){
        };
        mRequestQueue.add(jsonArrayRequest);
    }

    public void getExpense(){

        JsonArrayRequest jsonArrayRequest
                = new JsonArrayRequest(Request.Method.POST, "http://192.168.43.3:9080/app/expense/getAll", null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
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
                        Toast.makeText(AdminMoreAboutBudget.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }
                Float sum = 0F;
                for (Expense expense : expenseList){
                    sum += expense.getSum();
                }
                Log.d("THE_SUM_E_IS", sum.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                Log.i("Expense", volleyError.toString());
                Toast.makeText(AdminMoreAboutBudget.this, "Failed to get expenses", Toast.LENGTH_LONG).show();

            }
        }){
        };
        mRequestQueue.add(jsonArrayRequest);
    }


}