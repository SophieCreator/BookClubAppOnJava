package com.example.bookclubapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserTestDone extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_testdone);

        String rewardName = getIntent().getStringExtra("rewardName");

        TextView rewardTextView = findViewById(R.id.reward);
        rewardTextView.setText(rewardName);

        findViewById(R.id.UserTestDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUserTests();
            }
        });
    }

    private void goToUserTests() {
        Intent intent = new Intent(UserTestDone.this, UserTests.class);
        startActivity(intent);
        finish();
    }
}
