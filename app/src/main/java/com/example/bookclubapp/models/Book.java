package com.example.bookclubapp.models;

public class Book {

    private int book_id;

    private String name;
    private Integer pages;
    private Double litres_rating;
    private Double live_lib_rating;

    public Book(int book_id, String name, Integer pages, Double litres_rating, Double live_lib_rating){
        this.book_id = book_id;
        this.name = name;
        this.pages = pages;
        this.litres_rating = litres_rating;
        this.live_lib_rating = live_lib_rating;
    }

    public Book(int book_id, String name){
        this.book_id = book_id;
        this.name = name;
    }

    public Book(String name, Integer pages, Double litres_rating, Double live_lib_rating){
        this.name = name;
        this.pages = pages;
        this.litres_rating = litres_rating;
        this.live_lib_rating = live_lib_rating;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Double getLitres_rating() {
        return litres_rating;
    }

    public void setLitres_rating(Double litres_rating) {
        this.litres_rating = litres_rating;
    }

    public Double getLive_lib_rating() {
        return live_lib_rating;
    }

    public void setLive_lib_rating(Double live_lib_rating) {
        this.live_lib_rating = live_lib_rating;
    }


}
