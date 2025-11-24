package com.example.banking_system_gui;

import java.util.Date;

public class Company extends Customer {
    private String companyName;
    private String companyAddress;
    private String location;
    private int companyRegNumber;
    private Date dateOfIncorporation;
    private String contactPerson;
    private String contactPhone;

    public Company(String companyName, String companyAddress, String location, int companyRegNumber,
                   Date dateOfIncorporation, String customerID, String email, String phoneNumber,
                   String contactPerson, String contactPhone) {
        super("", "", customerID, email, "");
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.location = location;
        this.companyRegNumber = companyRegNumber;
        this.dateOfIncorporation = dateOfIncorporation;
        this.contactPerson = contactPerson;
        this.contactPhone = contactPhone;
    }

    @Override
    public String getFirstName() {
        return companyName;
    }

    @Override
    public String getLastName() {
        return "";
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCompanyRegNumber() {
        return companyRegNumber;
    }

    public void setCompanyRegNumber(int companyRegNumber) {
        this.companyRegNumber = companyRegNumber;
    }

    public Date getDateOfIncorporation() {
        return dateOfIncorporation;
    }

    public void setDateOfIncorporation(Date dateOfIncorporation) {
        this.dateOfIncorporation = dateOfIncorporation;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}