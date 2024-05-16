package com.example.bookclubapp.models;

import java.time.LocalDate;
import java.util.Date;

public class Income {

    private int income_id;
    private String name;
    private int amount;
    private LocalDate date_get;


    public Income(int income_id, String name, int amount, LocalDate date_get) {
        this.income_id = income_id;
        this.name = name;
        this.amount = amount;
        this.date_get = date_get;
    }

    public Income(int income_id, String name, int amount) {
        this.income_id = income_id;
        this.name = name;
        this.amount = amount;
    }

    public int getIncome_id() {
        return income_id;
    }

    public void setIncome_id(int income_id) {
        this.income_id = income_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDate getDate_get() {
        return date_get;
    }

    public void setDate_get(LocalDate date_get) {
        this.date_get = date_get;
    }
}
