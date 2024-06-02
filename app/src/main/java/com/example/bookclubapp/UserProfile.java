package com.example.bookclubapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookclubapp.models.User;

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


    public class UserProfileActivity extends AppCompatActivity {

        private TextView userNameTextView;
        private TextView userEmailTextView;
        private TextView userPhoneTextView;
        private TextView userUpcomingMeetingTextView;
        private TextView userFavouriteBooksTextView;
        private TextView userFavouriteAuthorsTextView;
        private TextView userFavouriteGenresTextView;

        private Button btnLogout;
        private Button btnMyMeetings;
        private Button btnMyRewards;
        private Button btnEditProfile;
        private Button btnAboutClub;
        private Button btnMainWindow;
        private Button btnMeetings;
        private Button btnProfile;

        private ImageButton showMoreButton;

        private int userId = 1;  // Replace this with the actual user ID from your authentication system

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user_profile);

            btnLogout = findViewById(R.id.btnLogout);
            btnMyMeetings = findViewById(R.id.btnMyMeetings);
            btnMyRewards = findViewById(R.id.btnMyRewards);
            btnEditProfile = findViewById(R.id.btnEditProfile);
            btnAboutClub = findViewById(R.id.btnAboutClub);
            btnMainWindow = findViewById(R.id.btnMainWindow);
            btnTests = findViewById(R.id.btnTests);
            btnProfile = findViewById(R.id.btnProfile);

            showMoreButton = findViewById(R.id.showmore);

            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToLogin();
                }
            });

            btnMyMeetings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToMyMeetings();
                }
            });

            btnMyRewards.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToMyRewards();
                }
            });

            btnEditProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToEditProfile();
                }
            });

            btnAboutClub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToAboutClub();
                }
            });

            btnMainWindow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToMainWindow();
                }
            });

            btnTests.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToTests();
                }
            });

            loadUserData(userId);
        }

        private void loadUserData(int userId) {
            User user = User.getUserById(userId);

            if (user != null) {
                userNameTextView.setText(user.getName());
                userEmailTextView.setText(user.getEmail());
                userUpcomingMeetingTextView.setText("Данные о предстоящей встрече");
                userFavouriteBooksTextView.setText(getListAsString(user.getFavouriteBooks()));
                userFavouriteAuthorsTextView.setText(getListAsString(user.getFavouriteAuthors()));
                userFavouriteGenresTextView.setText(getListAsString(user.getFavouriteGenres()));
            } else {
                Toast.makeText(this, "Пользователь не найден", Toast.LENGTH_SHORT).show();
            }
        }

        private String getListAsString(List<String> list) {
            if (list == null || list.isEmpty()) {
                return "";
            }
            return android.text.TextUtils.join(", ", list);
        }


        public void goToMyMeetings() {
            Intent intent = new Intent(this, UserMyMeetings.class);
            startActivity(intent);
        }

        public void goToMyRewards() {
            Intent intent = new Intent(this, UserMyRewards.class);
            startActivity(intent);
        }

        public void goToEditProfile() {
            Intent intent = new Intent(this, UserEditProfile.class);
            startActivity(intent);
        }

        public void goToAboutClub() {
            Intent intent = new Intent(this, UserAboutClub.class);
            startActivity(intent);
        }

        public void goToMainWindow() {
            Intent intent = new Intent(this, UserMainWindow.class);
            startActivity(intent);
        }

        public void goToTests() {
            Intent intent = new Intent(this, UserTests.class);
            startActivity(intent);
        }

    }

}