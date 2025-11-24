package com.example.banking_system_gui;

import java.util.Date;

public class SavingsAccount extends Account implements Interest {
    private double interestRate;

    // Constructor
    public SavingsAccount(String accountNumber, double balance, String branch, Date dateOpened, String customerID, double interestRate) {
        super(accountNumber, balance, branch, dateOpened, customerID);
        this.interestRate = interestRate;
    }

    // Override deposit method
    @Override
    public boolean deposit(double amount) {
        if (amount > 0) {
            setBalance(getBalance() + amount);
            return true;
        } else {
            return false;
        }
    }

    // Override withdraw method
    @Override
    public boolean withdraw(double amount) {
        // Savings account does not allow withdrawals
        return false;
    }

    //interest interface implementation
    @Override
    public void applyInterest() {
        double currentBalance = getBalance();
        double interestAmount = currentBalance * interestRate;
        double newBalance = currentBalance + interestAmount;

        setBalance(newBalance);;
    }

    @Override
    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

}
