package com.example.bookclubapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UserSherlock extends AppCompatActivity {

    private TextView sherlockTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sherlock);

        sherlockTextView = findViewById(R.id.sherLOCK);

        LinearLayout containerLayout = findViewById(R.id.main);

        containerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserSherlockActivity.this, UserRegDone.class);
                startActivity(intent);
                finish();
            }
        });

        retrieveSherlockReward();
    }

    private void retrieveSherlockReward() {
        RetrofitClient retrofitClient = RetrofitClient.getInstance();

        ApiService apiService = retrofitClient.getApiService();

        Call<String> call = apiService.getRewardName(1);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sherlockTextView.setText("Получено достижение: " + response.body());
                } else {
                    Toast.makeText(UserSherlockActivity.this, "Ошибка при получении данных", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(UserSherlockActivity.this, "Ошибка сети: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
