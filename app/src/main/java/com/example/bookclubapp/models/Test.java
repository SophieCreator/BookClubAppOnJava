package com.example.bookclubapp.models;

public class Test {
    private int test_id;

    private String test_name;
    private String complexity;

    public Test(int test_id, String test_name){
        this.test_id = test_id;
        this.test_name = test_name;
    }

    public int getTest_id() {
        return test_id;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public String getComplexity() {
        return complexity;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }
}