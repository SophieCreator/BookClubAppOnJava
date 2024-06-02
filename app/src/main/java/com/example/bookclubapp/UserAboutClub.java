package com.example.bookclubapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class UserAboutClub extends AppCompatActivity {

    private Button btnAboutClub, btnMainWindow, btnTests, btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_aboutclub);

        btnAboutClub = findViewById(R.id.btnAboutClub);
        btnMainWindow = findViewById(R.id.btnMainWindow);
        btnTests = findViewById(R.id.btnTests);
        btnProfile = findViewById(R.id.btnProfile);

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

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                How1();
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                How2();
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { How3(); }
        });
    }

    public void goToHow1() {
        Intent intent = new Intent(UserAboutClub.this, UserAboutClub.class);
        startActivity(intent);
    }

    public void goToHow2() {
        Intent intent = new Intent(UserAboutClub.this, UserAboutClub.class);
        startActivity(intent);
    }

    public void goToHow3() {
        Intent intent = new Intent(UserAboutClub.this, UserAboutClub.class);
        startActivity(intent);
    }

    public void goToAboutClub() {
        Intent intent = new Intent(UserAboutClub.this, UserAboutClub.class);
        startActivity(intent);
    }
    public void goToMainWindow() {
        Intent intent = new Intent(UserAboutClub.this, UserAboutClub.class);
        startActivity(intent);
    }

    public void goToTests() {
        Intent intent = new Intent(UserAboutClub.this, UserAboutClub.class);
        startActivity(intent);
    }

    public void goToProfile() {
        Intent intent = new Intent(UserAboutClub.this, UserAboutClub.class);
        startActivity(intent);
    }
}