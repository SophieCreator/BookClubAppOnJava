package com.example.bookclubapp.models;

import java.util.List;

public class User {

    private int user_id;
    private String name;
    private String login;
    private String email;
    private String is_admin;
    private String visited_meetings;
    List<Book> favouriteBooks;
    List<Author> favouriteAuthors;
    List<Genre> favouriteGenres;

    public User(int user_id, String name, String login, String email, String is_admin, List<Book> favouriteBooks, List<Author> favouriteAuthors, List<Genre> favouriteGenres, String visited){
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.is_admin = is_admin;
        this.visited_meetings = visited;
        this.favouriteBooks = favouriteBooks;
        this.favouriteGenres = favouriteGenres;
        this.favouriteAuthors = favouriteAuthors;
    }

    public String getVisited_meetings() {
        return visited_meetings;
    }

    public void setVisited_meetings(String visited_meetings) {
        this.visited_meetings = visited_meetings;
    }

    public List<Book> getFavouriteBooks() {
        return favouriteBooks;
    }

    public String getFavouriteBooksString() {
        String bookList = "";
        for (int i = 0; i < favouriteBooks.size(); i++){
            bookList = bookList + favouriteBooks.get(i).getName();
            if (i != favouriteBooks.size() - 1){
                bookList = bookList + " ,";
            }
        }
        return bookList;
    }

    public void setFavouriteBooks(List<Book> favouriteBooks) {
        this.favouriteBooks = favouriteBooks;
    }

    public List<Author> getFavouriteAuthors() {
        return favouriteAuthors;
    }

    public String getFavouriteAuthorsString() {
        String authorList = "";
        for (int i = 0; i < favouriteAuthors.size(); i++){
            authorList = authorList + favouriteAuthors.get(i).getName();
            if (i != favouriteAuthors.size() - 1){
                authorList = authorList + " ,";
            }
        }
        return authorList;
    }

    public void setFavouriteAuthors(List<Author> favouriteAuthors) {
        this.favouriteAuthors = favouriteAuthors;
    }

    public List<Genre> getFavouriteGenres() {
        return favouriteGenres;
    }

    public String getFavouriteGenresString() {
        String genreList = "";
        for (int i = 0; i < favouriteGenres.size(); i++){
            genreList = genreList + favouriteGenres.get(i).getName();
            if (i != favouriteGenres.size() - 1){
                genreList = genreList + " ,";
            }
        }
        return genreList;
    }
    

    public void setFavouriteGenres(List<Genre> favouriteGenres) {
        this.favouriteGenres = favouriteGenres;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(String is_admin) {
        this.is_admin = is_admin;
    }
}
