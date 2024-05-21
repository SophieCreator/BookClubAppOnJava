package com.example.bookclubapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.bookclubapp.helpers.DinamicView;
import com.example.bookclubapp.helpers.IncomeListRecyclerViewHelper;
import com.example.bookclubapp.models.Income;
import com.example.bookclubapp.utils.MyVolleySingletonUtil;
import com.example.bookclubapp.utils.SpacingItemDecoration;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminAddBook extends AppCompatActivity {

    Button btnAddAuthor, btnAddGenre, btnAdd;
    LinearLayout authorList, genreList;
    TextInputEditText bookName, bookAuthor, bookPages, bookLitres, bookLivelib;
    private RequestQueue mRequestQueue;
    Context context;
    List<String> genres = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_book);

        btnAddAuthor = findViewById(R.id.btnAddAuthor);
        btnAddGenre = findViewById(R.id.btnAddGenre);

        bookName = findViewById(R.id.bookName);
        bookPages = findViewById(R.id.bookPages);
        bookLitres = findViewById(R.id.bookLitres);
        bookLivelib = findViewById(R.id.bookLivelib);

        bookAuthor = findViewById(R.id.bookMainAuthor);
        btnAdd = findViewById(R.id.btnAdd);

        authorList = findViewById(R.id.author_list);
        genreList = findViewById(R.id.genre_list);

        mRequestQueue =  MyVolleySingletonUtil.getInstance(AdminAddBook.this).getRequestQueue();

        genres.add("Без жанра");
        getGenres();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook();
            }
        });

        btnAddAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = getLayoutInflater().inflate(R.layout.add_author, null, false);
                TextInputEditText editText = (TextInputEditText)view.findViewById(R.id.bookAuthor);
                ImageButton delete = (ImageButton)view.findViewById(R.id.delete);

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        authorList.removeView(view);
                    }
                });
                authorList.addView(view);
            }
        });
        getGenres();

        btnAddGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = getLayoutInflater().inflate(R.layout.add_genre, null, false);
                AppCompatSpinner spinnerTeam = view.findViewById(R.id.genre);
                ImageButton delete = view.findViewById(R.id.delete);

                Log.d("THIS_LIST_GENRES", genres.toString());
                ArrayAdapter arrayAdapter = new ArrayAdapter(AdminAddBook.this, android.R.layout.simple_spinner_item, genres);
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
    }


    public void addBook(){
        List<String> authors = new ArrayList<>();
        List<String> genresInput = new ArrayList<>();

        String bookNameText = bookName.getText().toString();
        Log.d("THIS_BOOK", bookNameText);
        String bookPagesText = bookPages.getText().toString();
        Log.d("THIS_PAGES", bookPagesText);
        String bookLitresText = bookLitres.getText().toString();
        Log.d("THIS_LITRES", bookLitresText);
        String bookLivelibText = bookLivelib.getText().toString();
        Log.d("THIS_LIVELIB", bookLivelibText);
        authors.add(bookAuthor.getText().toString());


        for(int i = 0; i < authorList.getChildCount(); i++) {
            View view = authorList.getChildAt(i);
            TextInputEditText author = (TextInputEditText) view.findViewById(R.id.bookAuthor);
            authors.add(author.getText().toString());
        }
        Log.d("THIS_AUTHORS", authors.toString());

        for(int i = 0; i < genreList.getChildCount(); i++) {
            View view = genreList.getChildAt(i);
            AppCompatSpinner spinnerTeam = (AppCompatSpinner)view.findViewById(R.id.genre);
            if (spinnerTeam.getSelectedItemPosition() != 0) {
                Log.d("THIS_GENRE_ITEM", genres.get(spinnerTeam.getSelectedItemPosition()));
                genresInput.add(genres.get(spinnerTeam.getSelectedItemPosition()));
            }
        }
        Log.d("THIS_GENRES_ADD", genresInput.toString());

        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.43.3:9080/app/bookCard/add", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(AdminAddBook.this, AdminListsBooks.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AdminAddBook.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("name", bookNameText);

                String authorFormat = "[";
                for(int i = 0; i < authors.size(); i++){
                    authorFormat = authorFormat + "\"" + authors.get(i) + "\"";
                    if (i != authors.size() - 1){
                        authorFormat += ",";
                    }
                }
                authorFormat += "]";
                Log.d("THIS_FORMAT", authorFormat);

                String genreFormat = "[";
                for(int i = 0; i < genresInput.size(); i++){
                    genreFormat = genreFormat + "\"" + genresInput.get(i) + "\"";
                    if (i != genresInput.size() - 1){
                        genreFormat += ",";
                    }
                }
                genreFormat += "]";
                Log.d("THIS_FORMAT", genreFormat);

                params.put("authors", authorFormat);
                params.put("genres", genreFormat);
                params.put("pages", bookPagesText);
                params.put("litres_rating", bookLitresText);
                params.put("live_lib_rating", bookLivelibText);
                return params;
            }
        };

        mRequestQueue.add(request);

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
                        Toast.makeText(AdminAddBook.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                Log.i("Income", volleyError.toString());
                Toast.makeText(AdminAddBook.this, "Failed to get incomes", Toast.LENGTH_LONG).show();

            }
        }){
        };
        mRequestQueue.add(jsonArrayRequest);
    }


}