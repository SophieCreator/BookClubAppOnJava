package com.example.bookclubapp.models;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Meeting {

    private int meeting_id;
    private int book_id;
    private String place;
    private LocalDate date;
    private LocalTime time;
    private String datetime;
    private String is_passed;
    private int price;
    private String book;
    private List<User> users;

    public Meeting(int meeting_id, int book_id, String place, LocalDate date, LocalTime time, String datetime, String is_passed, int price, String book, List<User> users) {
        this.meeting_id = meeting_id;
        this.book_id = book_id;
        this.place = place;
        this.date = date;
        this.time = time;
        this.datetime = datetime;
        this.is_passed = is_passed;
        this.price = price;
        this.book = book;
        this.users = users;
    }

    public String getUserList(){
        String res = "";
        for (int i = 0; i < users.size(); i++){
            res += users.get(i).getLogin();
            if (i != users.size() - 1){
                res += ", ";
            }
        }
        return res;
    }

    public int getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(int meeting_id) {
        this.meeting_id = meeting_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getIs_passed() {
        return is_passed;
    }

    public void setIs_passed(String is_passed) {
        this.is_passed = is_passed;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
