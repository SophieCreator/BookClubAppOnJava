package com.example.bookclubapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.bookclubapp.utils.MyVolleySingletonUtil;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class AdminBookFilter extends AppCompatActivity {

    private Button btnConfirm, btnAddGenre;
    private ImageButton btnBack;
    private TextInputEditText sizeMin, sizeMax, score;
    private RequestQueue mRequestQueue;
    private LinearLayout genreList;
    List<String> genres = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_book_filter);

        btnConfirm = findViewById(R.id.btnConfirm);
        btnBack = findViewById(R.id.btnBack);
        btnAddGenre = findViewById(R.id.btnAddGenre);
        sizeMin = findViewById(R.id.sizeMin);
        sizeMax = findViewById(R.id.sizeMax);
        score = findViewById(R.id.score);
        genreList = findViewById(R.id.genre_list);

        mRequestQueue =  MyVolleySingletonUtil.getInstance(AdminBookFilter.this).getRequestQueue();

        genres.add("Без жанра");
        getGenres();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminBookFilter.this, AdminListsBooks.class);
                startActivity(intent);
                finish();
            }
        });

        btnAddGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = getLayoutInflater().inflate(R.layout.add_genre, null, false);
                AppCompatSpinner spinnerTeam = view.findViewById(R.id.genre);
                ImageButton delete = view.findViewById(R.id.delete);

                Log.d("THIS_LIST_GENRES", genres.toString());
                ArrayAdapter arrayAdapter = new ArrayAdapter(AdminBookFilter.this, android.R.layout.simple_spinner_item, genres);
                spinnerTeam.setAdapter(arrayAdapter);

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        genreList.removeView(view);
                    }
                });
                genreList.addView(view);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> genresFiltered = new ArrayList<>();
                for(int i = 0; i < genreList.getChildCount(); i++) {
                    View view = genreList.getChildAt(i);
                    AppCompatSpinner spinnerTeam = (AppCompatSpinner)view.findViewById(R.id.genre);
                    if (spinnerTeam.getSelectedItemPosition() != 0) {
                        Log.d("THIS_GENRE_ITEM", genres.get(spinnerTeam.getSelectedItemPosition()));
                        genresFiltered.add(genres.get(spinnerTeam.getSelectedItemPosition()));
                    }
                }

                String scoreText = score.getText().toString();
                String sizeMinText = sizeMin.getText().toString();
                String sizeMaxText = sizeMax.getText().toString();
                String genresText = genresFiltered.toString();

                if(score.getText() == null){
                    scoreText = "-1";
                }
                if(sizeMin.getText() == null){
                    sizeMinText = "-1";
                }
                if(sizeMax.getText() == null){
                    sizeMaxText = "-1";
                }
                if(genresFiltered.isEmpty()){
                    genresText = "-1";
                }

                Intent intent = new Intent(AdminBookFilter.this, AdminListsBooks.class);
                intent.putExtra("score", scoreText);
                intent.putExtra("sizeMin", sizeMinText);
                intent.putExtra("sizeMax", sizeMaxText);
                intent.putExtra("genresFiltered", genresText);
                startActivity(intent);
                finish();
            }
        });

    }

    public void getGenres(){

        JsonArrayRequest jsonArrayRequest
                = new JsonArrayRequest(Request.Method.POST, "http://192.168.43.3:9080/app/bookCard/getGenres", null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try{
                        String name = response.getString(i);
                        genres.add(name);
                    } catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(AdminBookFilter.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                Log.i("Income", volleyError.toString());
                Toast.makeText(AdminBookFilter.this, "Failed to get incomes", Toast.LENGTH_LONG).show();

            }
        }){
        };
        mRequestQueue.add(jsonArrayRequest);
    }

}