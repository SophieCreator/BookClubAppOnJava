package com.example.bookclubapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class UserRecordDone extends AppCompatActivity {

    private Button btnAboutClub;
    private Button btnMainWindow;
    private Button btnTests;
    private Button btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_recorddone);

        btnAboutClub = findViewById(R.id.btnAboutClub);
        btnMainWindow = findViewById(R.id.btnMainWindow);
        btnTests = findViewById(R.id.btnTests);
        btnProfile = findViewById(R.id.btnProfile);

        btnAboutClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAboutClub(v);
            }
        });

        btnMainWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainWindow(v);
            }
        });

        btnTests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTests(v);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProfile(v);
            }
        });
    }

    public void goToAboutClub(View view) {
        Intent intent = new Intent(this, UserAboutClub.class);
        startActivity(intent);
    }

    public void goToMainWindow(View view) {
        Intent intent = new Intent(this, UserMainWindow.class);
        startActivity(intent);
    }

    public void goToTests(View view) {
        Intent intent = new Intent(this, UserTests.class);
        startActivity(intent);
    }

    public void goToProfile(View view) {
        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);
    }
}