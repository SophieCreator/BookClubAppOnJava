package com.example.bookclubapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
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
import java.util.Objects;

public class AdminExpenseEdit extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnEdit, btnChangeDate;
    private TextInputEditText expenseName, expenseAmount, expenseUrl;
    private RequestQueue mRequestQueue;
    SwitchCompat expenseIsRegular;
    String RegularIsChecked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_expense_edit);

        expenseName = findViewById(R.id.expenseName);
        expenseUrl = findViewById(R.id.expenseUrl);
        expenseAmount = findViewById(R.id.expenseSum);


        btnBack = findViewById(R.id.btnBack);
        btnEdit = findViewById(R.id.btnEdit);
        btnChangeDate = findViewById(R.id.btnChangeDate);
        expenseIsRegular = findViewById(R.id.expenseIsRegular);
        mRequestQueue =  MyVolleySingletonUtil.getInstance(AdminExpenseEdit.this).getRequestQueue();

        String expenseIdText = getIntent().getStringExtra("expenseId");
        String expenseNameText = getIntent().getStringExtra("expenseName");
        String expenseUrlText = getIntent().getStringExtra("expenseUrl");
        String expenseAmountText = getIntent().getStringExtra("expenseAmount");
        String expenseDateText = getIntent().getStringExtra("expenseDate");
        String expenseIsRegularText = getIntent().getStringExtra("expenseIsRegular");

        expenseName.setText(expenseNameText);
        expenseUrl.setText(expenseUrlText);
        expenseAmount.setText(expenseAmountText);
        btnChangeDate.setText(expenseDateText);

        if (Objects.equals(expenseIsRegularText, "1")){
            expenseIsRegular.setChecked(true);
            RegularIsChecked = "1";
        } else {
            expenseIsRegular.setChecked(false);
            RegularIsChecked = "0";
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminExpenseEdit.this, AdminExpenseDetails.class);
                intent.putExtra("expenseId", expenseIdText);
                intent.putExtra("expenseName", expenseNameText);
                intent.putExtra("expenseUrl", expenseUrlText);
                intent.putExtra("expenseAmount", expenseAmountText);
                intent.putExtra("expenseDate", expenseDateText);
                intent.putExtra("expenseIsRegular", expenseIsRegularText);
                startActivity(intent);
                finish();
            }
        });

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

        btnChangeDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                LocalDate localDate = LocalDate.now();
                int currYear = localDate.getYear();
                int currMonth = localDate.getMonthValue();
                int currDay = localDate.getDayOfMonth();
                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminExpenseEdit.this, new DatePickerDialog.OnDateSetListener() {
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
                editExpense(expenseIdText, RegularIsChecked);
                Intent intent = new Intent(AdminExpenseEdit.this, AdminExpenseDetails.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void editExpense(String expenseIdText, String regularIsChecked){
        String expenseNameText = expenseName.getText().toString();
        String expenseUrlText = expenseUrl.getText().toString();
        String expenseAmountText = expenseAmount.getText().toString();
        String expenseDateText = btnChangeDate.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.43.3:9080/app/expense/update", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(AdminExpenseEdit.this, AdminExpenseDetails.class);
                intent.putExtra("expenseId", expenseIdText);
                intent.putExtra("expenseName", expenseNameText);
                intent.putExtra("expenseUrl", expenseUrlText);
                intent.putExtra("expenseSum", expenseAmountText);
                intent.putExtra("expenseDate", expenseDateText);
                intent.putExtra("expenseIsRegular", regularIsChecked);
                startActivity(intent);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("EDIT_EXPENSE", error.toString());
                Toast.makeText(AdminExpenseEdit.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("name", expenseNameText);
                params.put("url", expenseUrlText);
                params.put("amount", expenseAmountText);
                params.put("date", expenseDateText);
                Log.d("EDIT_EXPENSE_CHECKED", regularIsChecked);
                params.put("is_regular", regularIsChecked);
                params.put("expense_id", expenseIdText);
                return params;
            }
        };
        mRequestQueue.add(request);

    }

}