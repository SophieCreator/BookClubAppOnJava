package com.example.bookclubapp.models;

import java.sql.Date;
import java.time.LocalDate;

public class Task {

    private int task_id;
    private Integer user_id;
    private String login;
    private String task_name;
    private String task_text;
    private LocalDate deadline;
    private String is_done;

    public Task(int task_id, String login, String task_name, String task_text, LocalDate deadline, String is_done){
        this.task_id = task_id;
        this.login = login;
        this.task_name = task_name;
        this.task_text = task_text;
        this.deadline = deadline;
        this.is_done = is_done;
    }

    public Task(int task_id, Integer user_id, String task_name, String task_text, LocalDate deadline, String is_done){
        this.task_id = task_id;
        this.user_id = user_id;
        this.task_name = task_name;
        this.task_text = task_text;
        this.deadline = deadline;
        this.is_done = is_done;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_text() {
        return task_text;
    }

    public void setTask_text(String task_text) {
        this.task_text = task_text;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getIs_done() {
        return is_done;
    }

    public void setIs_done(String is_done) {
        this.is_done = is_done;
    }
}
