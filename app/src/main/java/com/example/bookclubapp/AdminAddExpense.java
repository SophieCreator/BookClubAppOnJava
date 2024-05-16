package com.example.bookclubapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

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

public class AdminAddExpense extends AppCompatActivity {

    Button btnAddDate, btnAdd;
    TextInputEditText expenseName, expenseSum, expenseUrl;
    SwitchCompat expenseIsRegular;
    private RequestQueue mRequestQueue;
    String RegularIsChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_expense);

        btnAddDate = findViewById(R.id.btnAddDate);
        expenseName = findViewById(R.id.expenseName);
        expenseSum = findViewById(R.id.expenseSum);
        expenseUrl = findViewById(R.id.expenseUrl);
        btnAdd = findViewById(R.id.btnAdd);
        expenseIsRegular = findViewById(R.id.expenseIsRegular);
        mRequestQueue =  MyVolleySingletonUtil.getInstance(AdminAddExpense.this).getRequestQueue();

        expenseIsRegular.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    RegularIsChecked = "1";
                } else {
                    RegularIsChecked = "0";
                }
            }
        });

        btnAddDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                LocalDate localDate = LocalDate.now();
                int currYear = localDate.getYear();
                int currMonth = localDate.getMonthValue();
                int currDay = localDate.getDayOfMonth();
                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminAddExpense.this, new DatePickerDialog.OnDateSetListener() {
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
                addExpense(RegularIsChecked);
            }
        });
    }


    public void addExpense(String RegularIsChecked){
        String expenseNameText = expenseName.getText().toString();
        String expenseUrlText = expenseUrl.getText().toString();
        String expenseSumText = expenseSum.getText().toString();
        String expenseDateText = btnAddDate.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.43.3:9080/app/expense/add", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(AdminAddExpense.this, AdminBudgetExpenseList.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AdminAddExpense.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("name", expenseNameText);
                params.put("url", expenseUrlText);
                params.put("amount", expenseSumText);
                params.put("date", expenseDateText);
                params.put("is_regular", RegularIsChecked);
                return params;
            }
        };

        mRequestQueue.add(request);

    }
}

