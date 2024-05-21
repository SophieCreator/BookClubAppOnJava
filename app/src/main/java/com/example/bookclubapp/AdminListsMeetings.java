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
//import com.example.bookclubapp.helpers.MeetingListRecyclerViewHelper;
import com.example.bookclubapp.helpers.MeetingListRecyclerViewHelper;
import com.example.bookclubapp.models.Author;
import com.example.bookclubapp.models.Book;
import com.example.bookclubapp.models.Genre;
import com.example.bookclubapp.models.Meeting;
import com.example.bookclubapp.models.User;
import com.example.bookclubapp.utils.MyVolleySingletonUtil;
import com.example.bookclubapp.utils.SpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// убедитесь, что есть НУЖНАЯ модель с конструкторами, геттерами и сеттерами
// нужная = соотвествует запросу, пример: в модели пользователя есть любимые книги, авторы и тп

// СОЗДАЙТЕ ФАЙЛЫ
// meeting_list.xml
// MeetingListRecyclerViewHelper

public class AdminListsMeetings extends AppCompatActivity {

    // объявляем все кнопки навигации
    private Button btnNews, btnTasks, btnBudget, btnLists;
    private ImageButton btnBookList, btnMeetingList, btnUserList, btnTestList, btnPollList, btnRewardList;

    // объявляем остальные кнопки: добавление и тп

    // объявляем TextView
    private TextView txtNoMeetings;

    // объявляем InputView/TextInputEditText

    // объявляем RecyclerView, RecyclerView.Adapter, ProgressBar,SearchView и RequestQueue
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SearchView searchView;
    private RecyclerView.Adapter adapter;
    private RequestQueue mRequestQueue;

    // объявляем использующиеся списки, в том числе моделей
    private List<Meeting> meetingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_lists_meetings); // не забываем поменять на нужный view
        Log.d("THIS_MEETING", "I'M IN ONCREATE");

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
        

        // TextView
        txtNoMeetings = findViewById(R.id.no_meetings);

        // InputView/TextInputEditText

        // RecyclerView, RecyclerView.Adapter, ProgressBar,SearchView и RequestQueue
        recyclerView = findViewById(R.id.meeting_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminListsMeetings.this));
        progressBar = findViewById(R.id.get_not_progress_bar);
        searchView = findViewById(R.id.search);
        mRequestQueue =  MyVolleySingletonUtil.getInstance(AdminListsMeetings.this).getRequestQueue();

        // инициализируем использующиеся списки
        meetingList = new ArrayList<>();
        Log.d("THIS_MEETING", "I'M ININICIALIZED");

        // ________________________________________________________________________
        // вешаем прослушиватели на все кнопки (как вариант, их можно выделить в отдельные функции)
        // ________________________________________________________________________

        btnLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListsMeetings.this, AdminListsBooks.class);
                startActivity(intent);
                finish();
            }
        });
        btnTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListsMeetings.this, AdminMyTask.class);
                startActivity(intent);
                finish();
            }
        });
        btnBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListsMeetings.this, AdminBudgetIncomeList.class);
                startActivity(intent);
                finish();
            }
        });
        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListsMeetings.this, AdminNews.class);
                startActivity(intent);
                finish();
            }
        });
        btnBookList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListsMeetings.this, AdminListsBooks.class);
                startActivity(intent);
                finish();
            }
        });
        btnUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListsMeetings.this, AdminListsUsers.class);
                startActivity(intent);
                finish();
            }
        });

        // ________________________________________________________________________
        // объявляем функции, связанные с запросами (у нас - отображение списка)
        // ________________________________________________________________________

        getMeetings();


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
                List<Meeting> meetingListFiltered = new ArrayList<>();
                for (Meeting meeting : meetingList){
                    if(meeting.getBook().toLowerCase().contains(newText.toLowerCase()) || meeting.getPlace().toLowerCase().contains(newText.toLowerCase()) || meeting.getDatetime().toLowerCase().contains(newText.toLowerCase())){
                        meetingListFiltered.add(meeting);
                    }
                }
                adapter = new MeetingListRecyclerViewHelper(meetingListFiltered, AdminListsMeetings.this);
                recyclerView.setAdapter(adapter);

                return false;
            }
        });

    }


    public void getMeetings(){
        Log.d("THIS_MEETING", "I'M IN getMeetings");

        // ________________________________________________________________________
        // Пишем ссылку на запрос
        // ________________________________________________________________________
        String url = "http://192.168.43.3:9080/app/meeting/getAllInfo";

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

                         */
                        Log.d("THIS_MEETING OBJECT IS", String.valueOf(responseObject));

                        // по полям ответа формируем пользователя
                        int meeting_id = responseObject.getInt("meeting_id");
                        int book_id = responseObject.getInt("book_id");
                        String place = responseObject.getString("place");

                        LocalDateTime datetimeInput = LocalDateTime.parse(responseObject.getString("datetime"));

                        LocalTime time = LocalTime.of(datetimeInput.getHour(), datetimeInput.getMinute());
                        LocalDate date = LocalDate.of(datetimeInput.getYear(), datetimeInput.getMonth(), datetimeInput.getDayOfMonth());

                        String[] monthNames = {"января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"};
                        String month = monthNames[date.getMonth().getValue() - 1];

                        String datetime = date.getDayOfMonth() + " " + month + " в " + time.getHour() + ":" + time.getMinute();
                        String is_passed = responseObject.getString("is_passed");

                        int price = responseObject.getInt("price");
                        String book = responseObject.getString("book");

                        List<User> users = new ArrayList<>();
                        JSONArray userArray = responseObject.getJSONArray("users");
                        Log.d("THIS_MEETING USER_ARRAY", userArray.toString());
                        for (int k = 0; k < userArray.length(); k++){
                            JSONObject theUser = userArray.getJSONObject(k);
                            Log.d("THIS_MEETING USER", theUser.toString());
                            users.add(new User(theUser.getInt("user_id"), theUser.getString("name"), theUser.getString("email")));
                        }

                        Meeting meeting
                                = new Meeting(meeting_id, book_id, place, date, time, datetime, is_passed, price, book, users);
                        Log.d("THIS_MEETING", String.valueOf(meeting));

                        // добавляем пользователя в список
                        meetingList.add(meeting);

                    } catch (JSONException e){
                        e.printStackTrace();
                        //Toast.makeText(AdminListsMeetings.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }

                adapter = new MeetingListRecyclerViewHelper(meetingList, AdminListsMeetings.this);
                SpacingItemDecoration spacingItemDecoration = new SpacingItemDecoration(30);
                recyclerView.addItemDecoration(spacingItemDecoration);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                progressBar.setVisibility(View.GONE);
                txtNoMeetings.setVisibility(View.VISIBLE);
                Log.i("Meeting", volleyError.toString());
                Toast.makeText(AdminListsMeetings.this, "Failed to get meetings", Toast.LENGTH_LONG).show();

            }
        }){
        };
        mRequestQueue.add(jsonArrayRequest);
    }

    // ________________________________________________________________________
    // остальное делаем в файле MeetingListRecyclerViewHelper
    // ________________________________________________________________________

}
