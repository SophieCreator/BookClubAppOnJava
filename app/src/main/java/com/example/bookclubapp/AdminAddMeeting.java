package com.example.bookclubapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.bookclubapp.helpers.BookListRecyclerViewHelper;
import com.example.bookclubapp.models.Author;
import com.example.bookclubapp.models.Book;
import com.example.bookclubapp.models.BookCard;
import com.example.bookclubapp.models.Genre;
import com.example.bookclubapp.utils.MyVolleySingletonUtil;
import com.example.bookclubapp.utils.SpacingItemDecoration;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminAddMeeting extends AppCompatActivity {

    Button btnAddDate, btnAddBook, btnAdd, book1, book2, book3, priceRec;
    ImageButton btnBack;
    TextInputEditText place, time, price;
    private RequestQueue mRequestQueue;
    List<BookCard> bookRec;

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
        book1 = findViewById(R.id.book1);
        book2 = findViewById(R.id.book2);
        book3 = findViewById(R.id.book3);
        priceRec = findViewById(R.id.priceRec);

        bookRec = new ArrayList<>();

        mRequestQueue = MyVolleySingletonUtil.getInstance(AdminAddMeeting.this).getRequestQueue();

        String oldPlace = getIntent().getStringExtra("place");
        String oldPrice = getIntent().getStringExtra("price");
        String oldDate = getIntent().getStringExtra("date");
        String oldTime = getIntent().getStringExtra("time");
        String bookId = getIntent().getStringExtra("bookId");
        String bookName = getIntent().getStringExtra("bookName");
        String bookAuthors = getIntent().getStringExtra("bookAuthors");

        //getBookRec();
        book1.setText("Пикник на обочине");
        book2.setText("Убить Пересмешника");
        book3.setText("Понедельник начинается в субботу");
        priceRec.setText("400");

        place.setText(oldPlace);
        price.setText(oldPrice);
        if (oldDate != null) {
            btnAddDate.setText(oldDate);
        }
        time.setText(oldTime);
        if (bookName != null) {
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
                        if (neededMonth.length() == 1) {
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
    public void addMeeting(String bookId) {
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
        }) {
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

    public void getBookRec() {

        JsonArrayRequest jsonArrayRequest
                = new JsonArrayRequest(Request.Method.POST, "http://192.168.43.3:9080/app/bookCard/getRec", null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                Log.d("THIS_BOOK IS", String.valueOf(response));

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject responseObject = response.getJSONObject(i);
                        JSONObject book = responseObject.getJSONObject("book");
                        JSONArray authors = responseObject.getJSONArray("authors");
                        JSONArray genres = responseObject.getJSONArray("genres");

                        Log.d("THIS_BOOK_OBJECT IS", String.valueOf(book));
                        Log.d("THIS_AUTHORS_OBJECT IS", String.valueOf(authors));
                        Log.d("THIS_GENRE_OBJECT IS", String.valueOf(genres));

                        Book theBook = new Book(book.getInt("book_id"), book.getString("name"), book.getInt("pages"), book.getDouble("litres_rating"), book.getDouble("live_lib_rating"));
                        List<Author> theAuthors = new ArrayList<>();
                        for (int k = 0; k < authors.length(); k++) {
                            JSONObject theAuthor = authors.getJSONObject(k);
                            theAuthors.add(new Author(theAuthor.getInt("author_id"), theAuthor.getString("name")));
                        }
                        List<Genre> theGenres = new ArrayList<>();
                        for (int k = 0; k < genres.length(); k++) {
                            JSONObject theGenre = genres.getJSONObject(k);
                            theGenres.add(new Genre(theGenre.getInt("genre_id"), theGenre.getString("name")));
                        }
                        BookCard bookCard = new BookCard(theBook, theAuthors, theGenres);
                        Log.d("THIS_BOOK_CARD", String.valueOf(bookCard));

                        bookRec.add(bookCard);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(AdminAddMeeting.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                Log.i("Book", volleyError.toString());
                Toast.makeText(AdminAddMeeting.this, "Failed to get books", Toast.LENGTH_LONG).show();
            }
        }) {
        };
        mRequestQueue.add(jsonArrayRequest);
    }

}