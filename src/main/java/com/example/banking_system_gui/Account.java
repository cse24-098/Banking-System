package com.example.banking_system_gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Account {
    private String accountNumber;
    private double balance;
    private String branch;
    private Date dateOpened;
    private String customerID; //Account HAS-A customerID
    private List<String> transactionHistory; //Account HAS-A transactionHistory

    // Constructor
    public Account(String accountNumber, double balance, String branch, Date dateOpened, String customerID) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.branch = branch;
        this.dateOpened = dateOpened;
        this.customerID = customerID;
        this.transactionHistory = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Date getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(Date dateOpened) {
        this.dateOpened = dateOpened;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    //abstract methods for deposit and withdraw, implemented in subclasses
    public abstract boolean deposit(double amount);
    public abstract boolean withdraw(double amount);

    //concrete method for all subclasses to view transaction history
    public void addTransaction(String type, double amount) {
        String transaction = type + ": P" + amount + " | New Balance: P" + balance;
        this.transactionHistory.add(transaction);

    }

    public abstract double getInterestRate();

}
