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
            addTransaction("DEPOSIT", amount);
            System.out.println("Deposited : P" + amount + "into Savongs Account");
            return true;
        } else {
            System.out.println("Deposit amount must be positive");
            return false;
        }
    }

    // Override withdraw method
    @Override
    public boolean withdraw(double amount) {
        // Savings account does not allow withdrawals
        System.out.println("Error: Withdrawals are not allowed from Savings accounts");
        return false;
    }

    //interest interface implementation
    @Override
    public void applyInterest() {
        double currentBalance = getBalance();
        double interestAmount = currentBalance * interestRate;
        double newBalance = currentBalance + interestAmount;

        setBalance(newBalance);
        addTransaction("INTEREST", interestAmount);
        System.out.println("Monthly interest of P" + interestAmount + "applied to Savings Account");
    }

    @Override
    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    //Method to display savings-specific info
    public void displaySavingsInfo() {
        displayAccountDetails();
        System.out.println("Interest Rate: " + (interestRate * 100) + "%");
    }
}
