package com.example.bookclubapp;
import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AdminListsBooks extends AppCompatActivity {

    private ImageButton btnAddBook, btnMeetingList, btnUserList, btnTestList, btnPollList, btnRewardList;
    private Button btnNews, btnTasks, btnBudget, btnFilter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SearchView searchView;
    private RecyclerView.Adapter adapter;
    private RequestQueue mRequestQueue;
    private TextView txtNoBooks;

    String score = "-2";
    String sizeMin = "-2";
    String sizeMax = "-2";
    String genresFiltered = "-2";
    List<BookCard> bookList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_lists_books);

        if(ContextCompat.checkSelfPermission(AdminListsBooks.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AdminListsBooks.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
        }

        //makeNotification();

        btnAddBook = findViewById(R.id.btnAddBook);

        btnMeetingList = findViewById(R.id.btnMeetingList);
        btnUserList = findViewById(R.id.btnUserList);
        btnTestList = findViewById(R.id.btnTestList);
        btnPollList = findViewById(R.id.btnPollList);
        btnRewardList = findViewById(R.id.btnRewardList);
        btnFilter = findViewById(R.id.btnFilter);

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


        if( getIntent().getStringExtra("score") != null){
            score = getIntent().getStringExtra("score");
        }
        Log.d("THIS_FILTER_SCORE_IS", score);

        if(getIntent().getStringExtra("sizeMin") != null){
            sizeMin = getIntent().getStringExtra("sizeMin");
        }
        Log.d("THIS_FILTER_SIZEMIN_IS", sizeMin);

        if(getIntent().getStringExtra("sizeMax") != null){
            sizeMax = getIntent().getStringExtra("sizeMax");
        }
        Log.d("THIS_FILTER_SIZEMAX_IS", sizeMax);

        if(getIntent().getStringExtra("genresFiltered") != null){
            genresFiltered = getIntent().getStringExtra("genresFiltered");
        }
        Log.d("THIS_FILTER_genresFiltered_IS", sizeMax);


        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListsBooks.this, AdminBookFilter.class);
                startActivity(intent);
            }
        });
        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListsBooks.this, AdminAddBook.class);
                startActivity(intent);
            }
        });
        btnBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListsBooks.this, AdminBudgetIncomeList.class);
                startActivity(intent);
            }
        });

        btnMeetingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListsBooks.this, AdminListsMeetings.class);
                startActivity(intent);
            }
        });

        btnTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListsBooks.this, AdminMyTask.class);
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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                List<BookCard> bookListFiltered = new ArrayList<>();
                for (BookCard book : bookList){
                    if(book.getBook().getName().toLowerCase().contains(newText.toLowerCase()) || book.getGenresString().toLowerCase().contains(newText.toLowerCase()) ||book.getAuthorsString().toLowerCase().contains(newText.toLowerCase())){
                        bookListFiltered.add(book);
                    }
                }
                adapter = new BookListRecyclerViewHelper(bookListFiltered, AdminListsBooks.this);
                recyclerView.setAdapter(adapter);
                return false;
            }
        });

        getBooks(score, sizeMin, sizeMax, genresFiltered);

    }

    public void getBooks(String score, String sizeMin, String sizeMax, String genresFiltered){

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


                Log.d("THIS_FILTER_IN_BOOKLIST", bookList.toString());
                Log.d("THIS_FILTER_IN_GENRELIST", genresFiltered);


                if (!Objects.equals(genresFiltered, "-2")){
                    Log.d("THIS_FILTER_IS_FILTERED", "IN");
                    if (!Objects.equals(genresFiltered, "-1")) {
                        Log.d("THIS_FILTER_THERE_ARE_GENRES", "IN");
                        List<String> genresFilteredParsed = Collections.singletonList(genresFiltered.replace("[", "").replace("]", ""));
                        Log.d("THIS_FILTER_genresParsed", genresFilteredParsed.toString());

                        for (int i = 0; i < bookList.size(); i++) {

                            List<String> thisGenres = new ArrayList<>();
                            for (int k = 0; k < bookList.get(i).getGenres().size(); k++) {
                                thisGenres.add(bookList.get(i).getGenres().get(k).getName());
                            }
                            Log.d("THIS_FILTER_CURR_BOOK_GENRES", thisGenres.toString());

                            Boolean ok = false;
                            for (String target : genresFilteredParsed) {
                                for (String thisGenre : thisGenres) {
                                    if (Objects.equals(target, thisGenre)) {
                                        Log.d("THIS_FILTER_BOOK_PASSED", bookList.get(i).getBook().getName());
                                        ok = true;
                                    }
                                }
                            }
                            if (!ok) {
                                bookList.remove(i);
                            }
                        }
                    }


                    Log.d("THIS_FILTER_ERRROR_SCORE", score);

                    if (!Objects.equals(score, "-1") || !Objects.equals(score, "-2")){
                        Log.d("THIS_FILTER_SCORE", "ISN'T EMPTY");

                        for(int i = 0; i < bookList.size(); i++){
                            BookCard bookCard = bookList.get(i);
                            Log.d("THIS_FILTER_DOUBLE_SCORE", String.valueOf(Double.valueOf(score)));
                            Log.d("THIS_FILTER_BOOK_SCORE", String.valueOf((bookCard.getBook().getLitres_rating() + bookCard.getBook().getLive_lib_rating())/2));
                            if (Double.valueOf(score) < ((bookCard.getBook().getLitres_rating() + bookCard.getBook().getLive_lib_rating()))/2){
                                bookList.remove(i);
                            }
                        }
                    }

                    if (!Objects.equals(sizeMin, "-1") || !Objects.equals(sizeMin, "-2")){
                        for(int i = 0; i < bookList.size(); i++){
                            BookCard bookCard = bookList.get(i);
                            Log.d("THIS_FILTER_PAGES_THIS_BOOK", String.valueOf(bookCard.getBook().getPages()));
                            Log.d("THIS_FILTER_PAGES_BOARD", sizeMin);

                            if (Integer.valueOf(sizeMin) > bookCard.getBook().getPages()){
                                bookList.remove(i);
                            }
                        }
                    }

                    if (!Objects.equals(sizeMax, "-1") || !Objects.equals(sizeMax, "-2")){
                        for(int i = 0; i < bookList.size(); i++){
                            BookCard bookCard = bookList.get(i);

                            if (Integer.valueOf(sizeMax) < bookCard.getBook().getPages()){
                                bookList.remove(i);
                            }
                        }
                    }




                }

                Log.d("THIS_LIIIST", bookList.toString());


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

    public void makeNotification(){
        String channelLID = "CHANNEL";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelLID);
        builder.setSmallIcon(R.drawable.notification_a);
        builder.setContentTitle("Дедлайн по выполению задачи походит к концу!");
        builder.setContentText("Выполни задачу");
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(getApplicationContext(), AdminMyTask.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_MUTABLE);

        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelLID);
            if (notificationChannel == null){
                int impportance = NotificationManager.IMPORTANCE_HIGH;
                notificationChannel = new NotificationChannel(channelLID, "DESC", impportance);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        notificationManager.notify(0, builder.build());

    }

}