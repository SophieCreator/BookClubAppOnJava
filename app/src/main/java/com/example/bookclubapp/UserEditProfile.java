package com.example.bookclubapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserEditProfile extends AppCompatActivity {

    private EditText etPhoneNumber, etEmail, etFavouriteGenres, etFavouriteBooks, etFavouriteAuthors;
    private Button btnSave;

    private SharedPreferences sharedPreferences;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_profile);

        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        userId = sharedPreferences.getString("user_id", null);

        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etEmail = findViewById(R.id.etEmail);
        etFavouriteGenres = findViewById(R.id.etFavouriteGenres);
        etFavouriteBooks = findViewById(R.id.etFavouriteBooks);
        etFavouriteAuthors = findViewById(R.id.etFavouriteAuthors);
        btnSave = findViewById(R.id.btnSave);

        loadUserProfile();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserProfile();
            }
        });
    }

    private void loadUserProfile() {
        if (userId != null) {
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        User user = response.body();
                        if (user != null) {
                            etPhoneNumber.setText(user.getPhoneNumber());
                            etEmail.setText(user.getEmail());
                            etFavouriteGenres.setText(user.getFavouriteGenres());
                            etFavouriteBooks.setText(user.getFavouriteBooks());
                            etFavouriteAuthors.setText(user.getFavouriteAuthors());
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(UserEditProfile.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void saveUserProfile() {
        String phoneNumber = etPhoneNumber.getText().toString();
        String email = etEmail.getText().toString();
        String favouriteGenres = etFavouriteGenres.getText().toString();
        String favouriteBooks = etFavouriteBooks.getText().toString();
        String favouriteAuthors = etFavouriteAuthors.getText().toString();

        User user = new User();
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setFavouriteGenres(favouriteGenres);
        user.setFavouriteBooks(favouriteBooks);
        user.setFavouriteAuthors(favouriteAuthors);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UserEditProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserEditProfile.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UserEditProfile.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
