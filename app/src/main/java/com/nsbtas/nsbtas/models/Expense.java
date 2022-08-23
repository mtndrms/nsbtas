package com.nsbtas.nsbtas.models;

public class Expense {
    private String service;
    private String date;
    private String amount;

    public Expense(String service, String date, String amount) {
        this.service = service;
        this.date = date;
        this.amount = amount;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
