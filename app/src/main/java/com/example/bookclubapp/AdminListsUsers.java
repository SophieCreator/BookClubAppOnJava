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
//import com.example.bookclubapp.helpers.UserListRecyclerViewHelper;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.bookclubapp.helpers.UserListRecyclerViewHelper;
import com.example.bookclubapp.models.Author;
import com.example.bookclubapp.models.Book;
import com.example.bookclubapp.models.Genre;
import com.example.bookclubapp.models.User;
import com.example.bookclubapp.utils.MyVolleySingletonUtil;
import com.example.bookclubapp.utils.SpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// убедитесь, что есть НУЖНАЯ модель с конструкторами, геттерами и сеттерами
// нужная = соотвествует запросу, пример: в модели пользователя есть любимые книги, авторы и тп

// СОЗДАЙТЕ ФАЙЛЫ
// user_list.xml
// UserListRecyclerViewHelper

public class AdminListsUsers extends AppCompatActivity {

    // объявляем все кнопки навигации
    private Button btnNews, btnTasks, btnBudget, btnLists;
    private ImageButton btnBookList, btnMeetingList, btnUserList, btnTestList, btnPollList, btnRewardList;

    // объявляем остальные кнопки: добавление и тп
    private Button btnMyProfile, btnAddAdmin;

    // объявляем TextView
    private TextView txtNoUsers, more;

    // объявляем InputView/TextInputEditText

    // объявляем RecyclerView, RecyclerView.Adapter, ProgressBar,SearchView и RequestQueue
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SearchView searchView;
    private RecyclerView.Adapter adapter;
    private RequestQueue mRequestQueue;
    private AnyChartView chartView;

    // объявляем использующиеся списки, в том числе моделей
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_lists_users); // не забываем поменять на нужный view
        Log.d("THIS_USER", "I'M IN ONCREATE");

        // ________________________________________________________________________
        // ВСЕ объявленые конструкции находим в разметке
        // ________________________________________________________________________

        //кнопки навигации
        btnNews = findViewById(R.id.btnNews);
        btnTasks = findViewById(R.id.btnTasks);
        btnBudget = findViewById(R.id.btnBudget);
        btnLists = findViewById(R.id.btnLists);

        btnBookList = findViewById(R.id.btnBookList);
        btnMeetingList = findViewById(R.id.btnMeetingList);
        btnUserList = findViewById(R.id.btnUserList);
        btnTestList = findViewById(R.id.btnTestList);
        btnPollList = findViewById(R.id.btnPollList);
        btnRewardList = findViewById(R.id.btnRewardList);

        // остальные кнопки
        btnMyProfile = findViewById(R.id.btnMyProfile);
        btnAddAdmin = findViewById(R.id.btnAddAdmin);

        // TextView
        txtNoUsers = findViewById(R.id.no_users);
        more = findViewById(R.id.more);

        // InputView/TextInputEditText

        // RecyclerView, RecyclerView.Adapter, ProgressBar,SearchView и RequestQueue
        recyclerView = findViewById(R.id.user_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminListsUsers.this));
        progressBar = findViewById(R.id.get_not_progress_bar);
        searchView = findViewById(R.id.search);
        mRequestQueue =  MyVolleySingletonUtil.getInstance(AdminListsUsers.this).getRequestQueue();

        chartView = findViewById(R.id.userChart);
        setupChartView();

        // инициализируем использующиеся списки
        userList = new ArrayList<>();
        Log.d("THIS_USER", "I'M ININICIALIZED");

        // ________________________________________________________________________
        // объявляем функции, связанные с запросами (у нас - отображение списка)
        // ________________________________________________________________________

        getUsers();

        // ________________________________________________________________________
        // вешаем прослушиватели на все кнопки (как вариант, их можно выделить в отдельные функции)
        // ________________________________________________________________________

        btnLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListsUsers.this, AdminListsBooks.class);
                startActivity(intent);
                finish();
            }
        });
        btnTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListsUsers.this, AdminMyTask.class);
                startActivity(intent);
                finish();
            }
        });
        btnBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListsUsers.this, AdminBudgetIncomeList.class);
                startActivity(intent);
                finish();
            }
        });
        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListsUsers.this, AdminNews.class);
                startActivity(intent);
                finish();
            }
        });
        btnBookList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListsUsers.this, AdminListsBooks.class);
                startActivity(intent);
                finish();
            }
        });
        btnMeetingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListsUsers.this, AdminListsMeetings.class);
                startActivity(intent);
                finish();
            }
        });

        // ________________________________________________________________________
        // вешаем прослушиватель на поиск
        // ________________________________________________________________________
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<User> userListFiltered = new ArrayList<>();
                for (User user : userList){
                    if(user.getName().toLowerCase().contains(newText.toLowerCase()) || user.getLogin().toLowerCase().contains(newText.toLowerCase()) || user.getEmail().toLowerCase().contains(newText.toLowerCase())){
                        userListFiltered.add(user);
                    }
                }
                adapter = new UserListRecyclerViewHelper(userListFiltered, AdminListsUsers.this);
                recyclerView.setAdapter(adapter);

                return false;
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListsUsers.this, AdminUserStatistics.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void setupChartView(){
        Pie pie = AnyChart.pie();

        List<String> genres = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        genres.add("Триллер");
        genres.add("Фантастика");
        genres.add("Ужасы");
        genres.add("Классика");
        genres.add("Детектив");

        count.add(3);
        count.add(2);
        count.add(1);
        count.add(3);
        count.add(4);

        List<DataEntry> input = new ArrayList<>();
        for (int i = 0; i < genres.size(); i++){
            input.add(new ValueDataEntry(genres.get(i), count.get(i)));
        }
        pie.data(input);
        pie.title("Любимые жанры пользователей");

        chartView.setChart(pie);
    }

    public void getUsers(){
        Log.d("THIS_USER", "I'M IN getUsers");

        // ________________________________________________________________________
        // Пишем ссылку на запрос
        // ________________________________________________________________________
        String url = "http://192.168.43.3:9080/app/user/getAllInfo";

        JsonArrayRequest jsonArrayRequest
                = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                Log.d("THIS_RESPONSE IS", String.valueOf(response));

                for (int i = 0; i < response.length(); i++) {

                    try{
                        JSONObject responseObject = response.getJSONObject(i);
                        // ________________________________________________________________________
                        // смотрим структуру ответа запроса от сервера (в Postman) - это responseObject
                        // ________________________________________________________________________

                        /*
                        [{
                        "user_id": 1,
                        "name": "a",
                        "login": "a",
                        "email": "a@a.com",
                        "is_admin": "0",
                        "visited_meetings": "1",
                        "favouriteBooks": [
                            {
                                "book_id": 24,
                                "name": "kiki",
                                "pages": -1,
                                "litres_rating": -1.0,
                                "live_lib_rating": -1.0
                            }
                        ],
                        "favouriteAuthors": [
                            {
                                "author_id": 17,
                                "name": "Brodski"
                            }
                        ],
                        "favouriteGenres": [
                            {
                                "genre_id": 1,
                                "name": "horror"
                            }
                        ]
                        }]
                         */
                        Log.d("THIS_USER OBJECT IS", String.valueOf(responseObject));

                        // по полям ответа формируем пользователя
                        int user_id = responseObject.getInt("user_id");
                        String name = responseObject.getString("name");
                        String login = responseObject.getString("login");
                        String email = responseObject.getString("email");
                        String is_admin = responseObject.getString("is_admin");
                        String visited_meetings = responseObject.getString("visited_meetings");

                        List<Book> books = new ArrayList<>();
                        JSONArray bookArray = responseObject.getJSONArray("favouriteBooks");
                        Log.d("THIS_USER BOOK_ARRAY", bookArray.toString());
                        for (int k = 0; k < bookArray.length(); k++){
                            JSONObject theBook = bookArray.getJSONObject(k);
                            Log.d("THIS_USER BOOK", theBook.toString());
                            books.add(new Book(theBook.getInt("book_id"), theBook.getString("name")));
                        }

                        List<Author> authors = new ArrayList<>();
                        JSONArray authorArray = responseObject.getJSONArray("favouriteAuthors");
                        for (int k = 0; k < authorArray.length(); k++){
                            JSONObject theAuthor = authorArray.getJSONObject(k);
                            authors.add(new Author(theAuthor.getInt("author_id"), theAuthor.getString("name")));
                        }

                        List<Genre> genres = new ArrayList<>();
                        JSONArray genreArray = responseObject.getJSONArray("favouriteGenres");
                        for (int k = 0; k < genreArray.length(); k++){
                            JSONObject theGenre = genreArray.getJSONObject(k);
                            genres.add(new Genre(theGenre.getInt("genre_id"), theGenre.getString("name")));
                        }

                        User user
                                = new User(user_id, name, login, email, is_admin, books, authors, genres, visited_meetings);
                        Log.d("THIS_USER", String.valueOf(user));

                        // добавляем пользователя в список
                        userList.add(user);

                    } catch (JSONException e){
                        e.printStackTrace();
                        //Toast.makeText(AdminListsUsers.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }

                adapter = new UserListRecyclerViewHelper(userList, AdminListsUsers.this);
                SpacingItemDecoration spacingItemDecoration = new SpacingItemDecoration(30);
                recyclerView.addItemDecoration(spacingItemDecoration);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                progressBar.setVisibility(View.GONE);
                txtNoUsers.setVisibility(View.VISIBLE);
                Log.i("User", volleyError.toString());
                Toast.makeText(AdminListsUsers.this, "Failed to get users", Toast.LENGTH_LONG).show();

            }
        }){
        };
        mRequestQueue.add(jsonArrayRequest);
    }

    // ________________________________________________________________________
    // остальное делаем в файле UserListRecyclerViewHelper
    // ________________________________________________________________________

}
