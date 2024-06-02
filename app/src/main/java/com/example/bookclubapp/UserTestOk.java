package com.example.bookclubapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserTestOk extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_testok);
        findViewById(R.id.UserTestOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextQuestion();
            }
        });
    }

    private void goToNextQuestion() {
        Intent intent = new Intent(UserTestOk.this, UserTest.class);
        startActivity(intent);
        finish();
    }
}
