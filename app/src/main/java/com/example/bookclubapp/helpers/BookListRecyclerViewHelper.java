package com.example.bookclubapp.helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookclubapp.R;
import com.example.bookclubapp.models.Book;
import com.example.bookclubapp.models.BookCard;

import java.util.List;

public class BookListRecyclerViewHelper extends RecyclerView.Adapter<BookListRecyclerViewHelper.BookListViewHolder> {

    private List<BookCard> bookListItems;
    private Context context;

    public BookListRecyclerViewHelper(List<BookCard> bookListItems, Context context){
        this.bookListItems = bookListItems;
        this.context = context;
    }

    @NonNull
    @Override
    public BookListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_list, parent, false);
        return new BookListViewHolder(v);
    }

    public class BookListViewHolder extends RecyclerView.ViewHolder{
        public TextView bookId, bookName, bookAuthors, litresRating, livelibRating;
        private LinearLayout bookItemLayout;
        LinearLayout rating1, rating2;
        public BookListViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.book_name);
            bookAuthors = itemView.findViewById(R.id.book_authors);
            litresRating = itemView.findViewById(R.id.litres_rating);
            livelibRating = itemView.findViewById(R.id.livelib_rating);
            bookItemLayout = itemView.findViewById(R.id.bookItemLayout);
            rating1 = itemView.findViewById(R.id.rating1);
            rating2 = itemView.findViewById(R.id.rating2);
        }
    }

    @Override
    public int getItemCount() {
        return bookListItems.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BookListViewHolder holder, int position) {
        BookCard book = this.bookListItems.get(position);

        Double litres_rating = book.getBook().getLitres_rating();
        Double livelib_rating = book.getBook().getLive_lib_rating();

        Log.d("THIS_PARAMS_BOOK", book.getBook().getName());
        Log.d("THIS_PARAMS_BOOK", book.getAuthorsString());
        Log.d("THIS_PARAMS_BOOK", litres_rating.toString());
        Log.d("THIS_PARAMS_BOOK", livelib_rating.toString());

        if (litres_rating == -1){
            holder.rating1.setVisibility(View.GONE);
            holder.rating2.setVisibility(View.GONE);
        } else {
            holder.litresRating.setText(litres_rating.toString());
            holder.livelibRating.setText(livelib_rating.toString());
        }

        holder.bookName.setText(book.getBook().getName());
        holder.bookAuthors.setText(book.getAuthorsString());

        /*
        holder.bookItemLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, "You clicked: " + book.getBook_id(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, AdminBookDetails.class);
                intent.putExtra("bookId", String.valueOf(book.getBook().getBook_id()));
                intent.putExtra("bookName", book.getBook().getName());
                intent.putExtra("bookAuthors", String.valueOf(book.getAuthors()));
                intent.putExtra("bookAuthors", String.valueOf(book.getGenres()));
                intent.putExtra("litresRating", String.valueOf(book.getBook().getLitres_rating()));
                intent.putExtra("livelibRating", String.valueOf(book.getBook().getLive_lib_rating()));
                context.startActivity(intent);
            }
        });

         */

    }

}


