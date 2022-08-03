package com.nsbtas.nsbtas.models;

public class Card {
    private int id; // comes from db
    private String provider;
    private String cardNumber;
    private String cardOwner;

    public Card() {
    }

    public Card(int id, String provider, String cardNumber, String cardOwner) {
        this.id = id;
        this.provider = provider;
        this.cardNumber = cardNumber;
        this.cardOwner = cardOwner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }
}
