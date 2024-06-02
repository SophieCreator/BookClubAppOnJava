package com.example.bookclubapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserTestFailed extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_testfailed);
        findViewById(R.id.UserTestFailed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUserTests();
            }
        });
    }

    private void goToUserTests() {
        Intent intent = new Intent(UserTestFailed.this, UserTests.class);
        startActivity(intent);
        finish();
    }
}
