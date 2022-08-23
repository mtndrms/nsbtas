package com.nsbtas.nsbtas.models;

public class Card {
    private String provider;
    private String cardNumber;
    private String cardOwner;
    private String expirationDate;
    private String CVV;

    public Card(String provider, String cardNumber, String cardOwner, String expirationDate, String CVV) {
        this.provider = provider;
        this.cardNumber = cardNumber;
        this.cardOwner = cardOwner;
        this.expirationDate = expirationDate;
        this.CVV = CVV;
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

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }
}
