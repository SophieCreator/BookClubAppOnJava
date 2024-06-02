package com.example.bookclubapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class UserPoll extends AppCompatActivity {

    private CheckBox ans1, ans2, ans3, ans11, ans22, ans33, ans44;
    private Button btnAddPoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_poll);

        ans1 = findViewById(R.id.ans1);
        ans2 = findViewById(R.id.ans2);
        ans3 = findViewById(R.id.ans3);
        ans11 = findViewById(R.id.ans11);
        ans22 = findViewById(R.id.ans22);
        ans33 = findViewById(R.id.ans33);
        ans44 = findViewById(R.id.ans44);

        btnAddPoll = findViewById(R.id.btnAddPoll);

        btnAddPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPoll();
            }
        });
    }

    public void submitPoll() {

        boolean answer1 = ans1.isChecked();
        boolean answer2 = ans2.isChecked();
        boolean answer3 = ans3.isChecked();
        boolean answer4 = ans11.isChecked();
        boolean answer5 = ans22.isChecked();
        boolean answer6 = ans33.isChecked();
        boolean answer7 = ans44.isChecked();

        Poll response = new Poll(1, 1, answer1, answer2, answer3, answer4, answer5, answer6, answer7);

        Toast.makeText(this, "Ответы сохранены!", Toast.LENGTH_SHORT).show();
    }
}