package com.example.bookclubapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.bookclubapp.helpers.ExpenseListRecyclerViewHelper;
import com.example.bookclubapp.models.Expense;
import com.example.bookclubapp.utils.MyVolleySingletonUtil;
import com.example.bookclubapp.utils.SpacingItemDecoration;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminUserStatistics extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView book1, book2, book3, book4, book5;
    private PieChart genreView, authorView;
    private SwitchCompat genre, author;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_statistics);

        btnBack = findViewById(R.id.btnBack);
        genreView = findViewById(R.id.genreChart);
        authorView = findViewById(R.id.authorChart);
        genre = findViewById(R.id.genre);
        author = findViewById(R.id.author);
        book1 = findViewById(R.id.book1);
        book2 = findViewById(R.id.book2);
        book3 = findViewById(R.id.book3);
        book4 = findViewById(R.id.book4);
        book5 = findViewById(R.id.book5);

        mRequestQueue =  MyVolleySingletonUtil.getInstance(AdminUserStatistics.this).getRequestQueue();

        List<String> topBooks = new ArrayList<>();
        List<String> genresAllUsers = new ArrayList<>();
        List<Float> countGenresAll = new ArrayList<>();

        List<String> genresSpecialUsers = new ArrayList<>();
        List<Float> countGenreSpecial = new ArrayList<>();

        List<String> authorsAllUsers = new ArrayList<>();
        List<Float> countAuthorsAll = new ArrayList<>();

        List<String> authorsSpecialUsers = new ArrayList<>();
        List<Float> countAuthorsSpecial = new ArrayList<>();

        genresAllUsers.add("Триллер");
        genresAllUsers.add("Фантастика");
        genresAllUsers.add("Ужасы");
        genresAllUsers.add("Классика");
        genresAllUsers.add("Детектив");

        countGenresAll.add(3F);
        countGenresAll.add(2F);
        countGenresAll.add(1F);
        countGenresAll.add(3F);
        countGenresAll.add(4F);

        genresSpecialUsers.add("Триллер");
        genresSpecialUsers.add("Фантастика");
        genresSpecialUsers.add("Классика");
        genresSpecialUsers.add("Детектив");

        countGenreSpecial.add(1F);
        countGenreSpecial.add(2F);
        countGenreSpecial.add(1F);
        countGenreSpecial.add(2F);

        authorsAllUsers.add("Достоевский");
        authorsAllUsers.add("Паланик");
        authorsAllUsers.add("Толстой");
        authorsAllUsers.add("Роулинг");
        authorsAllUsers.add("Толкин");

        countAuthorsAll.add(3F);
        countAuthorsAll.add(1F);
        countAuthorsAll.add(1F);
        countAuthorsAll.add(2F);
        countAuthorsAll.add(1F);

        authorsSpecialUsers.add("Достоевский");
        authorsSpecialUsers.add("Паланик");
        authorsSpecialUsers.add("Роулинг");

        countAuthorsSpecial.add(2F);
        countAuthorsSpecial.add(1F);
        countAuthorsSpecial.add(2F);

        topBooks.add("Властелин колец, Д.Толкин");
        topBooks.add("Преступление и Наказание, Ф.Достоевский");
        topBooks.add("Война и мир, Л.Толстой");
        topBooks.add("Гарри Поттер, Д.Роулинг");
        topBooks.add("Убить пересмешника, Х.Ли");

        book1.setText(topBooks.get(0));
        book2.setText(topBooks.get(1));
        book3.setText(topBooks.get(2));
        book4.setText(topBooks.get(3));
        book5.setText(topBooks.get(4));

        setupGenreChartView(genresAllUsers, countGenresAll);
        setupAuthorChartView(authorsAllUsers, countAuthorsAll);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminUserStatistics.this, AdminListsUsers.class);
                startActivity(intent);
                finish();
            }
        });

        genre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    setupGenreChartView(genresSpecialUsers, countGenreSpecial);
                } else {
                    setupGenreChartView(genresAllUsers, countGenresAll);
                }
            }
        });

        author.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    setupAuthorChartView(authorsSpecialUsers, countAuthorsSpecial);
                } else {
                    setupAuthorChartView(authorsAllUsers, countAuthorsAll);
                }
            }
        });

    }

    public void setupGenreChartView(List<String> genres, List<Float> count){
        ArrayList<PieEntry> input = new ArrayList<>();

        for (int i = 0; i < genres.size(); i++){
            input.add(new PieEntry(count.get(i), genres.get(i)));
        }

        PieDataSet genreDataSet = new PieDataSet(input, "");
        genreDataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData data = new PieData(genreDataSet);
        genreView.getDescription().setEnabled(false);
        genreView.setData(data);
        genreView.invalidate();
    }

    public void setupAuthorChartView(List<String> authors, List<Float> counter){
        ArrayList<PieEntry> input2 = new ArrayList<>();

        for (int i = 0; i < authors.size(); i++){
            input2.add(new PieEntry(counter.get(i), authors.get(i)));
        }

        PieDataSet authorDataSet = new PieDataSet(input2, "");
        authorDataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData data2 = new PieData(authorDataSet);
        authorView.getDescription().setEnabled(false);
        authorView.setData(data2);
        authorView.invalidate();
    }

    public void getStatistic(){

        String url = "http://192.168.43.3:9080/app/user/bookCard/getStatistics";

        Map<String, String> params = new HashMap<String, String>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("onResponse", response.toString());
                try {
                    HashMap<String, Integer> genreAll = (HashMap<String, Integer>) response.get("genreAll");
                    HashMap<String, Integer> genreSpecial = (HashMap<String, Integer>) response.get("genreSpecial");
                    HashMap<String, Integer> authorAll = (HashMap<String, Integer>) response.get("authorAll");
                    HashMap<String, Integer> authorSpecial = (HashMap<String, Integer>) response.get("authorSpecial");
                    List<String> books = (List<String>) response.get("books");

                } catch (JSONException e){

                    Toast.makeText(AdminUserStatistics.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body = "";
                int status = error.networkResponse.statusCode;
                Log.d("onErrorResponse", String.valueOf(status));

                if (error.networkResponse.data != null) {
                    try {
                        body = new String(error.networkResponse.data, "UTF-8");
                        Log.d("onErrorResponse", body);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(AdminUserStatistics.this, body, Toast.LENGTH_LONG).show();
                }
            }
        });
        mRequestQueue.add(jsonObjectRequest);

    }

}