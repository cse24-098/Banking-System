package com.example.banking_system_gui;

import java.util.Date;

public class Company extends Customer {
    private String companyName;
    private String companyAddress;
    private int companyNumber;
    private Date dateOfIncorporation;

    public Company(String companyName, String companyAddress, int companyNumber, Date dateOfIncorporation, String customerID, String firstName, String lastName, String email, String phoneNumber) {
        super(firstName, lastName, customerID, email, phoneNumber);
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyNumber = companyNumber;
        this.dateOfIncorporation = dateOfIncorporation;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public int getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(int companyNumber) {
        this.companyNumber = companyNumber;
    }

    public Date getDateOfIncorporation() {
        return dateOfIncorporation;
    }

    public void setDateOfIncorporation(Date dateOfIncorporation) {
        this.dateOfIncorporation = dateOfIncorporation;
    }

}

