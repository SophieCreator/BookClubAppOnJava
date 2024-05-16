package com.example.bookclubapp;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.bookclubapp.helpers.BookListRecyclerViewHelper;
import com.example.bookclubapp.helpers.IncomeListRecyclerViewHelper;
import com.example.bookclubapp.models.Author;
import com.example.bookclubapp.models.Book;
import com.example.bookclubapp.models.BookCard;
import com.example.bookclubapp.models.Genre;
import com.example.bookclubapp.models.Income;
import com.example.bookclubapp.utils.MyVolleySingletonUtil;
import com.example.bookclubapp.utils.SpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminListsBooks extends AppCompatActivity {

    ImageButton btnAddBook, btnMeetingList, btnUserList, btnTestList, btnPollList, btnRewardList;
    Button btnNews, btnTasks, btnBudget;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SearchView searchView;
    private RecyclerView.Adapter adapter;
    RequestQueue mRequestQueue;
    TextView txtNoBooks;

    List<BookCard> bookList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_lists_books);

        btnAddBook = findViewById(R.id.btnAddBook);

        btnMeetingList = findViewById(R.id.btnMeetingList);
        btnUserList = findViewById(R.id.btnUserList);
        btnTestList = findViewById(R.id.btnTestList);
        btnPollList = findViewById(R.id.btnPollList);
        btnRewardList = findViewById(R.id.btnRewardList);

        btnNews = findViewById(R.id.btnNews);
        btnTasks = findViewById(R.id.btnTasks);
        btnBudget = findViewById(R.id.btnBudget);

        txtNoBooks = findViewById(R.id.no_books);

        mRequestQueue =  MyVolleySingletonUtil.getInstance(AdminListsBooks.this).getRequestQueue();
        progressBar = findViewById(R.id.get_not_progress_bar);
        searchView = findViewById(R.id.bookSearch);

        recyclerView = findViewById(R.id.book_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminListsBooks.this));

        btnBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListsBooks.this, AdminBudgetIncomeList.class);
                startActivity(intent);
            }
        });

        btnTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListsBooks.this, AdminTasks.class);
                startActivity(intent);
            }
        });

        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListsBooks.this, AdminNews.class);
                startActivity(intent);
            }
        });

        btnUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListsBooks.this, AdminListsUsers.class);
                startActivity(intent);
                finish();
            }
        });

        getBooks();


    }

    public void getBooks(){

        JsonArrayRequest jsonArrayRequest
                = new JsonArrayRequest(Request.Method.POST, "http://192.168.43.3:9080/app/bookCard/getAll", null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                Log.d("THIS_BOOK IS", String.valueOf(response));

                for (int i = 0; i < response.length(); i++) {
                    try{
                        JSONObject responseObject = response.getJSONObject(i);
                        JSONObject book = responseObject.getJSONObject("book");
                        JSONArray authors = responseObject.getJSONArray("authors");
                        JSONArray genres = responseObject.getJSONArray("genres");

                        Log.d("THIS_BOOK_OBJECT IS", String.valueOf(book));
                        Log.d("THIS_AUTHORS_OBJECT IS", String.valueOf(authors));
                        Log.d("THIS_GENRE_OBJECT IS", String.valueOf(genres));

                        Book theBook = new Book(book.getInt("book_id"), book.getString("name"), book.getInt("pages"), book.getDouble("litres_rating"), book.getDouble("live_lib_rating"));
                        List<Author> theAuthors = new ArrayList<>();
                        for (int k = 0; k < authors.length(); k++){
                            JSONObject theAuthor = authors.getJSONObject(k);
                            theAuthors.add(new Author(theAuthor.getInt("author_id"), theAuthor.getString("name")));
                        }
                        List<Genre> theGenres = new ArrayList<>();
                        for (int k = 0; k < genres.length(); k++){
                            JSONObject theGenre = genres.getJSONObject(k);
                            theGenres.add(new Genre(theGenre.getInt("genre_id"), theGenre.getString("name")));
                        }
                        BookCard bookCard = new BookCard(theBook, theAuthors, theGenres);
                        Log.d("THIS_BOOK_CARD", String.valueOf(bookCard));

                        bookList.add(bookCard);

                    } catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(AdminListsBooks.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }
                adapter = new BookListRecyclerViewHelper(bookList, AdminListsBooks.this);
                SpacingItemDecoration spacingItemDecoration = new SpacingItemDecoration(30);
                recyclerView.addItemDecoration(spacingItemDecoration);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                progressBar.setVisibility(View.GONE);
                txtNoBooks.setVisibility(View.VISIBLE);
                Log.i("Book", volleyError.toString());
                Toast.makeText(AdminListsBooks.this, "Failed to get books", Toast.LENGTH_LONG).show();
            }
        }){
        };
        mRequestQueue.add(jsonArrayRequest);
    }




}