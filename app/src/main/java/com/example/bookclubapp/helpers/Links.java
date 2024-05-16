package com.example.bookclubapp.helpers;

public class Links {
    private static final String BASE_URL = "http://192.168.0.200:9080/app/";

    public static String getAllBooks(){
            return BASE_URL + "book/getAll";
    }

    public static String addBook(){
            return BASE_URL + "book/add";
    }
    public static String deleteNoteApiUri(){
        return BASE_URL + "note/delete";
    }

    public static String getAllIncomes(){
        return BASE_URL + "income/getAll";
    }



}
