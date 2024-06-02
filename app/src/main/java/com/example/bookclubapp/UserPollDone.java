package com.example.bookclubapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class UserPollDone extends AppCompatActivity {

    private ConstraintLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_polldone);

        mainLayout = findViewById(R.id.main);
        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    goToMainWindow();
                    return true;
                }
                return false;
            }
        });
    }

    private void goToMainWindow() {
        Intent intent = new Intent(this, UserMainWindow.class);
        startActivity(intent);
        finish();
    }
}