package com.example.bookclubapp.models;

import java.sql.Date;
import java.time.LocalDate;

public class Expense {

    private int expense_id;
    private String name;
    private LocalDate date;

    private String url;

    private int sum;
    String is_regular;

    public Expense(int expense_id, String name, String url, LocalDate date, int sum, String is_regular) {
        this.expense_id = expense_id;
        this.url = url;
        this.name = name;
        this.date = date;
        this.sum = sum;
        this.is_regular = is_regular;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getExpense_id() {
        return expense_id;
    }

    public void setExpense_id(int expense_id) {
        this.expense_id = expense_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getIs_regular() {
        return is_regular;
    }

    public void setIs_regular(String is_regular) {
        this.is_regular = is_regular;
    }
}
