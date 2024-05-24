package com.example.bookclubapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;
import com.example.bookclubapp.utils.MyVolleySingletonUtil;

public class AdminBookDetails extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnDelete, btnEdit;
    private TextView name, authors, genres, pages, litres, livelib;
    LinearLayout genreLayout;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_book_details);

        name = findViewById(R.id.name);
        authors = findViewById(R.id.authors);
        genres = findViewById(R.id.genres);
        pages = findViewById(R.id.pages);
        litres = findViewById(R.id.litres);
        livelib = findViewById(R.id.livelib);

        btnBack = findViewById(R.id.btnBack);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        genreLayout = findViewById(R.id.genreLayout);

        mRequestQueue =  MyVolleySingletonUtil.getInstance(AdminBookDetails.this).getRequestQueue();

        String bookId = getIntent().getStringExtra("bookId");
        String nameText = getIntent().getStringExtra("name");
        String authorText = getIntent().getStringExtra("authors");
        String genreText = getIntent().getStringExtra("genres");
        String pagesText = getIntent().getStringExtra("pages");
        String litresText = getIntent().getStringExtra("litres");
        String livelibText = getIntent().getStringExtra("livelib");

        name.setText(nameText);
        authors.setText(authorText);

        if (pagesText == null){
            pages.setVisibility(View.GONE);
        } else {
            String pageInfo = "";
            Integer pageCount = Integer.valueOf(pagesText);
            int m = pageCount % 10;
            if (m == 1){
                pageInfo = pagesText + " страница";
            } else if (m == 2 || m == 3 || m == 4){
                pageInfo = pagesText + " страницы";
            } else {
                pageInfo = pagesText + " страниц";
            }
            pages.setText(pageInfo);
        }

        if (litresText == null){
            litres.setVisibility(View.GONE);
        } else {
            litres.setText(litresText);
        }

        if (livelibText == null){
            livelib.setVisibility(View.GONE);
        } else {
            livelib.setText(livelibText);
        }

        genreLayout.setVisibility(View.GONE);


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete(bookIdText);
                Intent intent = new Intent(AdminBookDetails.this, AdminListsBooks.class);
                startActivity(intent);
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminBookDetails.this, AdminListsBooks.class);
                intent.putExtra("bookId", bookId);
                intent.putExtra("name", nameText);
                intent.putExtra("authors", authorText);
                intent.putExtra("genres", genreText);
                intent.putExtra("pages", pagesText);
                intent.putExtra("litres", litresText);
                intent.putExtra("livelib", livelibText);
                startActivity(intent);
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminBookDetails.this, AdminListsBooks.class);
                startActivity(intent);
                finish();
            }
        });

    }
}