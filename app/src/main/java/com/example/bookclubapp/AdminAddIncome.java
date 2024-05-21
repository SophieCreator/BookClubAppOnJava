package com.example.bookclubapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bookclubapp.utils.MyVolleySingletonUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AdminAddIncome extends AppCompatActivity {

    Button btnAddDate, btnAdd;
    TextInputEditText incomeName, incomeAmount;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_income);

        btnAddDate = findViewById(R.id.btnAddDate);
        incomeName = findViewById(R.id.incomeName);
        incomeAmount = findViewById(R.id.incomeAmount);
        btnAdd = findViewById(R.id.btnAdd);
        mRequestQueue =  MyVolleySingletonUtil.getInstance(AdminAddIncome.this).getRequestQueue();

        btnAddDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                LocalDate localDate = LocalDate.now();
                int currYear = localDate.getYear();
                int currMonth = localDate.getMonthValue();
                int currDay = localDate.getDayOfMonth();
                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminAddIncome.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String neededMonth = String.valueOf(month);
                        if (neededMonth.length() == 1){
                            neededMonth = "0" + neededMonth;
                        }
                        btnAddDate.setText(String.valueOf(year) + "-" + neededMonth + "-" + String.valueOf(dayOfMonth));
                    }
                }, currYear, currMonth, currDay);
                datePickerDialog.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIncome();
            }
        });
    }


    public void addIncome(){
        String incomeNameText = incomeName.getText().toString();
        String incomeAmountText = incomeAmount.getText().toString();
        String incomeDateText = btnAddDate.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.43.3:9080/app/income/add", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(AdminAddIncome.this, AdminBudgetIncomeList.class);
                startActivity(intent);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AdminAddIncome.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("name", incomeNameText);
                params.put("amount", incomeAmountText);
                params.put("date", incomeDateText);
                return params;
            }
        };

        mRequestQueue.add(request);

    }
}