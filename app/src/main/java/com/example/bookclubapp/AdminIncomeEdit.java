package com.example.bookclubapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

public class AdminIncomeEdit extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnEdit, btnChangeDate;
    private TextInputEditText incomeName, incomeAmount;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_income_edit);

        incomeName = findViewById(R.id.incomeName);
        incomeAmount = findViewById(R.id.incomeAmount);

        btnBack = findViewById(R.id.btnBack);
        btnEdit = findViewById(R.id.btnEdit);
        btnChangeDate = findViewById(R.id.btnChangeDate);
        mRequestQueue =  MyVolleySingletonUtil.getInstance(AdminIncomeEdit.this).getRequestQueue();

        String incomeIdText = getIntent().getStringExtra("incomeId");
        String incomeNameText = getIntent().getStringExtra("incomeName");
        String incomeAmountText = getIntent().getStringExtra("incomeAmount");
        String incomeDateText = getIntent().getStringExtra("incomeDate");

        incomeName.setText(incomeNameText);
        incomeAmount.setText(incomeAmountText);
        btnChangeDate.setText(incomeDateText);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminIncomeEdit.this, AdminIncomeDetails.class);
                intent.putExtra("incomeId", incomeIdText);
                intent.putExtra("incomeName", incomeNameText);
                intent.putExtra("incomeAmount", incomeAmountText);
                intent.putExtra("incomeDate", incomeDateText);
                startActivity(intent);
                finish();
            }
        });

        btnChangeDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                LocalDate localDate = LocalDate.now();
                int currYear = localDate.getYear();
                int currMonth = localDate.getMonthValue();
                int currDay = localDate.getDayOfMonth();
                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminIncomeEdit.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String neededMonth = String.valueOf(month);
                        if (neededMonth.length() == 1){
                            neededMonth = "0" + neededMonth;
                        }
                        btnChangeDate.setText(String.valueOf(year) + "-" + neededMonth + "-" + String.valueOf(dayOfMonth));
                    }
                }, currYear, currMonth, currDay);
                datePickerDialog.show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editIncome(incomeIdText);
                Intent intent = new Intent(AdminIncomeEdit.this, AdminIncomeDetails.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void editIncome(String incomeIdText){
        String incomeNameText = incomeName.getText().toString();
        String incomeAmountText = incomeAmount.getText().toString();
        String incomeDateText = btnChangeDate.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.43.3:9080/app/income/update", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(AdminIncomeEdit.this, AdminIncomeDetails.class);
                intent.putExtra("incomeId", incomeIdText);
                intent.putExtra("incomeName", incomeNameText);
                intent.putExtra("incomeAmount", incomeAmountText);
                intent.putExtra("incomeDate", incomeDateText);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AdminIncomeEdit.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("name", incomeNameText);
                params.put("amount", incomeAmountText);
                params.put("date", incomeDateText);
                params.put("income_id", incomeIdText);
                return params;
            }
        };
        mRequestQueue.add(request);

    }

}