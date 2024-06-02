package com.example.bookclubapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookclubapp.models.Meeting;
import com.example.bookclubapp.models.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class UserMainWindow extends AppCompatActivity {

    private Button btnAddRecord;
    private ImageButton btnPool;
    private Button btnAboutClub;
    private Button btnMainWindow;
    private Button btnMeetings;
    private Button btnProfile;

    private TextView timemeeting;
    private TextView bookmeeting;
    private TextView placemeeting;
    private TextView pricemeeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_mainwindow);

        btnAddRecord = findViewById(R.id.btnAddRecord);
        btnPool = findViewById(R.id.btnPool);
        btnAboutClub = findViewById(R.id.btnAboutClub);
        btnMainWindow = findViewById(R.id.btnMainWindow);
        btnMeetings = findViewById(R.id.btnMeetings);
        btnProfile = findViewById(R.id.btnProfile);

        timemeeting = findViewById(R.id.timemeeting);
        bookmeeting = findViewById(R.id.bookmeeting);
        placemeeting = findViewById(R.id.placemeeting);
        pricemeeting = findViewById(R.id.pricemeeting);

        btnAddRecord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToRecord();
            }
        });

        btnPool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPoll();
            }
        });

        btnAboutClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAboutClub();
            }
        });

        btnMainWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainWindow();
            }
        });

        btnTests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTests();
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProfile();
            }
        });
    }

    private void displayMeetingDetails(Meeting meeting) {
        timemeeting.setText("Время: " + meeting.getTime().toString());
        bookmeeting.setText("Книга встречи: " + meeting.getBook());
        placemeeting.setText("Место встречи: " + meeting.getPlace());
        pricemeeting.setText("Стоимость: " + meeting.getPrice() + " руб.");
    }

    public void goToAboutClub() {
        Intent intent = new Intent(UserMainWindow.this, UserAboutClub.class);
        startActivity(intent);
    }
    public void goToMainWindow() {
        Intent intent = new Intent(UserMainWindow.this, UserAboutClub.class);
        startActivity(intent);
    }

    public void goToTests() {
        Intent intent = new Intent(UserMainWindow.this, UserAboutClub.class);
        startActivity(intent);
    }

    public void goToProfile() {
        Intent intent = new Intent(UserMainWindow.this, UserAboutClub.class);
        startActivity(intent);
    }
}