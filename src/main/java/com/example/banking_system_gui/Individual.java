package com.example.banking_system_gui;

import java.util.Date;

public class Individual extends Customer {
    private int idNumber;
    private Date dateOfBirth;

    public Individual(int idNumber, Date dateOfBirth, String customerID, String firstName, String lastName, String email, String phoneNumber) {
        super(firstName, lastName, customerID, email, phoneNumber);
        this.idNumber = idNumber;
        this.dateOfBirth = dateOfBirth;
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
}
