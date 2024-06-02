package com.example.bookclubapp.models;

public class Poll {

    private int poll_id;
    private String question;
    private String var1;
    private String var2;
    private String var3;
    private String var4;

    private int counter1;
    private int counter2;
    private int counter3;
    private int counter4;

    //question, var1, var2, var3, var4, counter1, counter2, counter3, counter4;
    //question, var1, var2, var3, var4, counterN1, counterN2, counterN3, counterN4;

    private int counterN1;
    private int counterN2;
    private int counterN3;
    private int counterN4;

    private boolean is_active;

    public Poll(int poll_id, String question){
        this.poll_id = poll_id;
        this.question = question;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public int getPoll_id() {
        return poll_id;
    }

    public void setPoll_id(int poll_id) {
        this.poll_id = poll_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getVar1() {
        return var1;
    }

    public void setVar1(String var1) {
        this.var1 = var1;
    }

    public String getVar2() {
        return var2;
    }

    public void setVar2(String var2) {
        this.var2 = var2;
    }

    public String getVar3() {
        return var3;
    }

    public void setVar3(String var3) {
        this.var3 = var3;
    }

    public String getVar4() {
        return var4;
    }

    public void setVar4(String var4) {
        this.var4 = var4;
    }

    public int getCounter1() {
        return counter1;
    }

    public void setCounter1(int counter1) {
        this.counter1 = counter1;
    }

    public int getCounter2() {
        return counter2;
    }

    public void setCounter2(int counter2) {
        this.counter2 = counter2;
    }

    public int getCounter3() {
        return counter3;
    }

    public void setCounter3(int counter3) {
        this.counter3 = counter3;
    }

    public int getCounter4() {
        return counter4;
    }

    public void setCounter4(int counter4) {
        this.counter4 = counter4;
    }

    public int getCounterN1() {
        return counterN1;
    }

    public void setCounterN1(int counterN1) {
        this.counterN1 = counterN1;
    }

    public int getCounterN2() {
        return counterN2;
    }

    public void setCounterN2(int counterN2) {
        this.counterN2 = counterN2;
    }

    public int getCounterN3() {
        return counterN3;
    }

    public void setCounterN3(int counterN3) {
        this.counterN3 = counterN3;
    }

    public int getCounterN4() {
        return counterN4;
    }

    public void setCounterN4(int counterN4) {
        this.counterN4 = counterN4;
    }
}
