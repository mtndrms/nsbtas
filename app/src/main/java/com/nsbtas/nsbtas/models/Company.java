package com.nsbtas.nsbtas.models;

public class Company {
    private String companyName;
    private String customerName;
    private String phoneNumber;
    private String emailAddress;
    private String note;
    private String address;

    public Company() {
    }

    public Company(String companyName, String phoneNumber) {
    }

    public Company(String companyName, String customerName, String phoneNumber, String emailAddress, String note, String address) {
        this.companyName = companyName;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.note = note;
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
