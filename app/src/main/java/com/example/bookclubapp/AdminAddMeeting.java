package com.example.bookclubapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AdminAddMeeting extends AppCompatActivity {

    Button btnAddDate, btnAddBook, btnAdd;
    ImageButton btnBack;
    TextInputEditText place, time, price;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_meeting);

        btnAddDate = findViewById(R.id.btnAddDate);
        btnAddBook = findViewById(R.id.btnAddBook);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        place = findViewById(R.id.place);
        time = findViewById(R.id.time);
        price = findViewById(R.id.price);

        mRequestQueue =  MyVolleySingletonUtil.getInstance(AdminAddMeeting.this).getRequestQueue();

        String oldPlace = getIntent().getStringExtra("place");
        String oldPrice = getIntent().getStringExtra("price");
        String oldDate = getIntent().getStringExtra("date");
        String oldTime = getIntent().getStringExtra("time");
        String bookId = getIntent().getStringExtra("bookId");
        String bookName = getIntent().getStringExtra("bookName");
        String bookAuthors = getIntent().getStringExtra("bookAuthors");

        place.setText(oldPlace);
        price.setText(oldPrice);
        if (oldDate != null){
            btnAddDate.setText(oldDate);
        }
        time.setText(oldTime);
        if (bookName != null){
            btnAddBook.setText(bookName);
        }

        btnAddDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                LocalDate localDate = LocalDate.now();
                int currYear = localDate.getYear();
                int currMonth = localDate.getMonthValue();
                int currDay = localDate.getDayOfMonth();
                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminAddMeeting.this, new DatePickerDialog.OnDateSetListener() {
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

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddMeeting.this, AdminListsMeetings.class);
                startActivity(intent);
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                addMeeting(bookId);
            }
        });

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminAddMeeting.this, AdminChooseBook.class);
                intent.putExtra("place", place.getText().toString());
                intent.putExtra("price", price.getText().toString());
                intent.putExtra("time", time.getText().toString());
                intent.putExtra("date", btnAddDate.getText().toString());
                startActivity(intent);
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addMeeting(String bookId){
        String placeText = place.getText().toString();
        String priceText = price.getText().toString();
        String timeText = time.getText().toString();
        String dateText = btnAddDate.getText().toString();
        String dateTimeS = dateText + "T" + timeText;

        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.43.3:9080/app/meeting/add", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(AdminAddMeeting.this, AdminListsMeetings.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AdminAddMeeting.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("bookId", bookId);
                params.put("place", placeText);
                params.put("price", priceText);
                params.put("datetime", dateTimeS);
                return params;
            }
        };

        mRequestQueue.add(request);

    }
}