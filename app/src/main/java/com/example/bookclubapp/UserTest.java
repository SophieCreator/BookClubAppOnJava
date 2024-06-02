package com.example.bookclubapp;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserTest extends AppCompatActivity {

    private TextView questionTextView;
    private CheckBox ans1CheckBox, ans2CheckBox, ans3CheckBox, ans4CheckBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_test);

        questionTextView = findViewById(R.id.recordmeeting);
        ans1CheckBox = findViewById(R.id.ans1);
        ans2CheckBox = findViewById(R.id.ans2);
        ans3CheckBox = findViewById(R.id.ans3);
        ans4CheckBox = findViewById(R.id.ans4);

        ImageButton backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadQuestion();

        setupCheckBoxListeners();
    }

    private void loadQuestion() {

        RetrofitClient retrofitClient = RetrofitClient.getInstance();

        ApiService apiService = retrofitClient.getApiService();

        Call<String> call = apiService.getQuestion(1); // Предположим, что id теста равен 1

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    questionTextView.setText(response.body());
                } else {
                    Toast.makeText(UserTestActivity.this, "Ошибка при загрузке вопроса", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(UserTestActivity.this, "Ошибка сети: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupCheckBoxListeners() {
        CompoundButton.OnCheckedChangeListener checkBoxListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean isCorrectAnswer = checkAnswer();

                if (isChecked) {

                    if (!isCorrectAnswer) {
                        goToTestFailed();
                    } else {
                        goToTestOk();
                    }
                }
            }
        };

        ans1CheckBox.setOnCheckedChangeListener(checkBoxListener);
        ans2CheckBox.setOnCheckedChangeListener(checkBoxListener);
        ans3CheckBox.setOnCheckedChangeListener(checkBoxListener);
        ans4CheckBox.setOnCheckedChangeListener(checkBoxListener);
    }
}

