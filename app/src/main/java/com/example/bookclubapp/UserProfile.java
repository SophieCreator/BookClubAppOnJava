package com.example.bookclubapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserProfile extends AppCompatActivity {

    TextView uName, uEmail;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // захват полей
        uName = findViewById(R.id.name);
        uEmail = findViewById(R.id.email);

        // получаем проброшенные значения
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");

        // устанавливаем в поля значения
        uName.setText(name);
        uEmail.setText(email);


        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUserOut();
            }
        });

    }

    public void signUserOut(){

        uName.setText(null);
        uEmail.setText(null);

        Intent goToLogin = new Intent(UserProfile.this, Login.class);
        startActivity(goToLogin);
        finish();

    }
    public void goToLogin(View view){
        Intent intent = new Intent(UserProfile.this, Login.class);
        startActivity(intent);
        finish();
    }
}