package com.nsbtas.nsbtas.models;

public class Expense {
    private String firm;
    private String date;
    private int amount;

    public Expense() {
    }

    public Expense(String firm, String date, int amount) {
        this.firm = firm;
        this.date = date;
        this.amount = amount;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
