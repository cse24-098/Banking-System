package com.example.banking_system_gui;

import java.util.Date;

public class InvestmentAccount extends Account implements Interest, Withdrawable{
    private double interestRate;
    private static final double MIN_OPENING_BALANCE = 500.00;

    // Constructor
    public InvestmentAccount(String accountNumber, double balance, String branch, Date dateOpened, String customerID, double interestRate) {
        super(accountNumber, balance, branch, dateOpened, customerID);

        if (balance < MIN_OPENING_BALANCE) {
            throw new IllegalArgumentException("Opening balance must be at least " + MIN_OPENING_BALANCE);
        }
        this.interestRate = interestRate;
    }

    //override deposit method- checks minimum deposit amount
    @Override
    public boolean deposit(double amount) {
        if (amount >= MIN_OPENING_BALANCE){
            setBalance(getBalance() + amount);

            return true;
        } else {
            return false;
        }
    }

    //override withdraw method - checks minimum withdraw amount
    @Override
    public boolean withdraw (double amount) {
        if (amount <= 0) {
            return false;
        }
        if (amount > getBalance()) {
            return false;
        }

        //process withdrawal
        setBalance(getBalance() - amount);

        return true;
    }

    // Withdrawable interface implementation
    @Override
    public double getAvailableBalance() {
        return getBalance();
    }

    //interest interface implementation
    @Override
    public void applyInterest() {
        double currentBalance = getBalance();
        double interestAmount = currentBalance * interestRate;
        double newBalance = currentBalance + interestAmount;

        setBalance(newBalance);

    }

    @Override
    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    //getter for minimum opening balance
    public static double getMinOpeningBalance() {
        return MIN_OPENING_BALANCE;
    }

}
