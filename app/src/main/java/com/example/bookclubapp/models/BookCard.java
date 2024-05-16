package com.example.bookclubapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class BookCard {
    private Book book;
    private List<Genre> genres;

    private List<Author> authors;
    public BookCard(Book book, List<Author> authors, List<Genre> genres){
        this.book = book;
        this.authors = authors;
        this.genres = genres;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public String getAuthorsString() {
        String authorList = "";
        for (int i = 0; i < authors.size(); i++){
            authorList = authorList + authors.get(i).getName();
            if (i != authors.size() - 1){
                authorList = authorList + " ,";
            }
        }
        return authorList;
    }

    public String getGenresString() {
        String genreList = "";
        for (int i = 0; i < genres.size(); i++){
            genreList = genreList + genres.get(i).getName();
            if (i != genres.size() - 1){
                genreList = genreList + " ,";
            }
        }
        return genreList;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

}
