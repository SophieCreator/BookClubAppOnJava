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

public class AdminIncomeDetails extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnIncomeDelete, btnIncomeEdit;
    private TextView incomeName, incomeAmount, incomeDate;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_income_details);

        incomeName = findViewById(R.id.incomeName);
        incomeAmount = findViewById(R.id.incomeAmount);
        incomeDate = findViewById(R.id.incomeDate);

        btnBack = findViewById(R.id.btnBack);
        btnIncomeEdit = findViewById(R.id.btnIncomeEdit);
        btnIncomeDelete = findViewById(R.id.btnIncomeDelete);

        mRequestQueue =  MyVolleySingletonUtil.getInstance(AdminIncomeDetails.this).getRequestQueue();

        String incomeIdText   = getIntent().getStringExtra("incomeId");
        String incomeNameText   = getIntent().getStringExtra("incomeName");
        String incomeAmountText = getIntent().getStringExtra("incomeAmount");
        String incomeDateText = getIntent().getStringExtra("incomeDate");

        incomeName.setText(incomeNameText);
        incomeAmount.setText(incomeAmountText);
        incomeDate.setText(incomeDateText);

        btnIncomeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(incomeIdText);
                Intent intent = new Intent(AdminIncomeDetails.this, AdminBudgetIncomeList.class);
                startActivity(intent);
                finish();
            }
        });

        btnIncomeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminIncomeDetails.this, AdminIncomeEdit.class);
                intent.putExtra("incomeId", incomeIdText);
                intent.putExtra("incomeName", incomeNameText);
                intent.putExtra("incomeAmount", incomeAmountText);
                intent.putExtra("incomeDate", incomeDateText);
                startActivity(intent);
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminIncomeDetails.this, AdminBudgetIncomeList.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void delete(String incomeId){
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.43.3:9080/app/income/delete", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("DELETE INCOME","SUCCESS");
                Toast.makeText(AdminIncomeDetails.this, response.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("DELETE INCOME", "FAIL");
                Toast.makeText(AdminIncomeDetails.this, "Failed", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("income_id", incomeId);
                return params;
            }
        };

        mRequestQueue.add(request);
    }

}