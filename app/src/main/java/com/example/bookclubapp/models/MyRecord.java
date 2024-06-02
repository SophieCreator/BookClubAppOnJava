package com.example.bookclubapp.models;

public class MyRecord {

    private int record_id;

    private int meeting_id;
    private int user_id;
    private Boolean is_passed;
    private Float rating;
    private int sale;

    public MyRecord(int record_id, int meeting_id){
        this.record_id = record_id;
        this.meeting_id = meeting_id;
    }

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public int getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(int meeting_id) {
        this.meeting_id = meeting_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Boolean getIs_passed() {
        return is_passed;
    }

    public void setIs_passed(Boolean is_passed) {
        this.is_passed = is_passed;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }
}
