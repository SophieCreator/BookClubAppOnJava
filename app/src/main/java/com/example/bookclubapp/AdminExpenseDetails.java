package com.example.bookclubapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bookclubapp.utils.MyVolleySingletonUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AdminExpenseDetails extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnExpenseDelete, btnExpenseEdit;
    private TextView expenseName, expenseAmount, expenseDate, expenseUrl, expenseIsRegular;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_expense_details);

        expenseName = findViewById(R.id.expenseName);
        expenseUrl = findViewById(R.id.expenseUrl);
        expenseAmount = findViewById(R.id.expenseAmount);
        expenseDate = findViewById(R.id.expenseDate);
        expenseIsRegular = findViewById(R.id.expense_regular);

        btnBack = findViewById(R.id.btnBack);
        btnExpenseEdit = findViewById(R.id.btnExpenseEdit);
        btnExpenseDelete = findViewById(R.id.btnExpenseDelete);

        mRequestQueue =  MyVolleySingletonUtil.getInstance(AdminExpenseDetails.this).getRequestQueue();

        String expenseIdText   = getIntent().getStringExtra("expenseId");
        String expenseNameText   = getIntent().getStringExtra("expenseName");
        String expenseUrlText   = getIntent().getStringExtra("expenseUrl");
        String expenseAmountText = getIntent().getStringExtra("expenseSum");
        String expenseDateText = getIntent().getStringExtra("expenseDate");
        String expenseRegularText = getIntent().getStringExtra("expenseIsRegular");

        expenseName.setText(expenseNameText);
        expenseUrl.setText(expenseUrlText);
        expenseAmount.setText(expenseAmountText);
        expenseDate.setText(expenseDateText);
        if (Objects.equals(expenseRegularText, "1")){
            expenseIsRegular.setText("регулярный");
        } else {
            expenseIsRegular.setText("не регулярный");
        }

        btnExpenseDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(expenseIdText);
                Intent intent = new Intent(AdminExpenseDetails.this, AdminBudgetExpenseList.class);
                startActivity(intent);
                finish();
            }
        });

        btnExpenseEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminExpenseDetails.this, AdminExpenseEdit.class);
                intent.putExtra("expenseId", expenseIdText);
                intent.putExtra("expenseName", expenseNameText);
                intent.putExtra("expenseUrl", expenseUrlText);
                intent.putExtra("expenseAmount", expenseAmountText);
                intent.putExtra("expenseDate", expenseDateText);
                intent.putExtra("expenseIsRegular", expenseRegularText);
                startActivity(intent);
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminExpenseDetails.this, AdminBudgetExpenseList.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void delete(String expenseId){
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.43.3:9080/app/expense/delete", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("DELETE INCOME","SUCCESS");
                Toast.makeText(AdminExpenseDetails.this, response.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("DELETE INCOME", "FAIL");
                Toast.makeText(AdminExpenseDetails.this, "Failed", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("expense_id", expenseId);
                return params;
            }
        };

        mRequestQueue.add(request);
    }

}