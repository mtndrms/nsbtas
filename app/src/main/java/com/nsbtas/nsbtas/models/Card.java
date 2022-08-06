package com.nsbtas.nsbtas.models;

public class Card {
    private int id; // comes from db
    private String provider;
    private String cardNumber;
    private String cardOwner;
    private String expirationDate;

    public Card() {
    }

    public Card(int id, String provider, String cardNumber, String cardOwner, String expirationDate) {
        this.id = id;
        this.provider = provider;
        this.cardNumber = cardNumber;
        this.cardOwner = cardOwner;
        this.expirationDate = expirationDate;
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

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
