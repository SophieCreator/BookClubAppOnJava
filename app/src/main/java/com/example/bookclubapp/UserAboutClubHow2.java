package com.example.bookclubapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class UserAboutClubHow2 extends AppCompatActivity {

    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_how2);

        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAboutClub();
            }
        });
    }

    public void goToAboutClub() {
        Intent intent = new Intent(UserAboutClubHow2.this, UserAboutClub.class);
        startActivity(intent);
        finish();
    }
}