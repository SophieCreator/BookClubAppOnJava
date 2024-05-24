package com.example.bookclubapp;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminMeetingDetails extends AppCompatActivity {

    public TextView name, time, place, sum, users;
    ImageButton btnBack, btnEdit, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_meeting_details);

        name = findViewById(R.id.name);
        time = findViewById(R.id.time);
        place = findViewById(R.id.place);
        sum = findViewById(R.id.sum);
        users = findViewById(R.id.users);

        String meetingId = getIntent().getStringExtra("meetingId");
        String nameText = getIntent().getStringExtra("name");
        String timeText = getIntent().getStringExtra("date");
        String priceText = getIntent().getStringExtra("price");
        String placeText = getIntent().getStringExtra("place");
        String usersText = getIntent().getStringExtra("users");

        String priceInfo = String.valueOf(priceText + " p");

        name.setText(nameText);
        time.setText(timeText);
        place.setText(placeText);
        sum.setText(priceText);
        users.setText(usersText);





    }
}