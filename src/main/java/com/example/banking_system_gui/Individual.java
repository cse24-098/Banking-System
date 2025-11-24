package com.example.banking_system_gui;

import java.util.Date;

public class Individual extends Customer {
    private int idNumber;
    private Date dateOfBirth;
    private String address;
    private String residence;

    public Individual(int idNumber, Date dateOfBirth, String customerID, String firstName,
                      String lastName, String email, String phoneNumber, String address, String residence) {
        super(firstName, lastName, customerID, email, phoneNumber);
        this.idNumber = idNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.residence = residence;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }
}