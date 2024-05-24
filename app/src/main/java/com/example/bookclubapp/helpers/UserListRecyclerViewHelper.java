package com.example.bookclubapp.helpers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.example.bookclubapp.models.User;


import java.util.List;
import java.util.Objects;

public class UserListRecyclerViewHelper extends RecyclerView.Adapter<UserListRecyclerViewHelper.UserListViewHolder> {

    private List<User> userListItems;
    private Context context;

    public UserListRecyclerViewHelper(List<User> userListItems, Context context){
        this.userListItems = userListItems;
        this.context = context;
    }

    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list, parent, false);
        return new UserListViewHolder(v);
    }

    public class UserListViewHolder extends RecyclerView.ViewHolder{

        // ________________________________________________________________________
        // объявляем view из user_list
        // ________________________________________________________________________

        public TextView userName, userEmail, userLogin, userFavouriteBooks, userFavouriteAuthors, userFavouriteGenres, userAdmin;
        private LinearLayout userItemLayout, userHeaderLayout;
        public UserListViewHolder(@NonNull View itemView) {
            super(itemView);

            // ________________________________________________________________________
            // находим view из user_list
            // ________________________________________________________________________

            userAdmin = itemView.findViewById(R.id.userAdmin);
            userName = itemView.findViewById(R.id.user_name);
            userLogin = itemView.findViewById(R.id.user_login);
            userEmail = itemView.findViewById(R.id.user_email);
            userFavouriteBooks = itemView.findViewById(R.id.user_favourite_books);
            userFavouriteAuthors = itemView.findViewById(R.id.user_favourite_authors);
            userFavouriteGenres = itemView.findViewById(R.id.user_favourite_genres);

            userItemLayout = itemView.findViewById(R.id.userItemLayout);
            userHeaderLayout = itemView.findViewById(R.id.user_header);
        }
    }

    @Override
    public int getItemCount() {
        return userListItems.size();
    }

    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder holder, int position) {
        User user = this.userListItems.get(position);

        // ________________________________________________________________________
        // ОТОБРАЖЕНИЕ карточки (сетим текст)
        // ________________________________________________________________________

        holder.userName.setText(user.getName());
        holder.userLogin.setText(user.getLogin());
        holder.userEmail.setText(user.getEmail());

        String is_admin = user.getIs_admin();
        if(Objects.equals(is_admin, "0")){
            holder.userAdmin.setVisibility(View.GONE);
        }

        // ________________________________________________________________________
        // отображаем поле с текстом, если данные не пустые
        // ________________________________________________________________________

        if (Objects.equals(user.getFavouriteAuthorsString(), "") && Objects.equals(user.getFavouriteAuthorsString(), "") && Objects.equals(user.getFavouriteAuthorsString(), "")){
            holder.userHeaderLayout.setVisibility(View.GONE);
        }

        if (Objects.equals(user.getFavouriteAuthorsString(), "")){
            holder.userFavouriteAuthors.setVisibility(View.GONE);
        } else {
            holder.userFavouriteAuthors.setText(user.getFavouriteAuthorsString());
        }

        if (Objects.equals(user.getFavouriteBooksString(), "")){
            holder.userFavouriteBooks.setVisibility(View.GONE);
        } else {
            holder.userFavouriteBooks.setText(user.getFavouriteBooksString());
        }

        if (Objects.equals(user.getFavouriteAuthorsString(), "")){
            holder.userFavouriteGenres.setVisibility(View.GONE);
        } else {
            holder.userFavouriteGenres.setText(user.getFavouriteGenresString());
        }

        // ________________________________________________________________________
        // переход на карточку по клику и проброс данных
        // ________________________________________________________________________

        /*
        holder.userItemLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, "You clicked: " + user.getUser_id(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, AdminUserDetails.class);
                intent.putExtra("userId", String.valueOf(user.getUser().getUser_id()));
                intent.putExtra("userName", user.getUser().getName());
                intent.putExtra("userLogin", user.getUser().getLogin());
                intent.putExtra("userEmail", user.getUser().getEmail());
                intent.putExtra("userBooks", String.valueOf(user.getFavouriteBooksString()));
                intent.putExtra("userAuthors", String.valueOf(user.getFavouriteBooksString()));
                intent.putExtra("userGenres", String.valueOf(user.getFavouriteGenresString()));
                context.startActivity(intent);


            }


        });
         */
    }
}





