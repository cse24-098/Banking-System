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
            addTransaction("DEPOSIT", amount);
            System.out.println("Successfully deposited P" + amount + "into Investment Account" + getAccountNumber());
            return true;
        } else {
            System.out.println("Deposit amount must be at least P" + MIN_OPENING_BALANCE);
            return false;
        }
    }

    //override withdraw method - checks minimum withdraw amount
    @Override
    public boolean withdraw (double amount) {
        if (amount <= 0) {
            System.out.println("Error: Withdrawal amount must be positive.");
            return false;
        }
        if (amount > getBalance()) {
            System.out.println("Error: Insufficient funds for withdrawal. Available balance: " + getBalance() + ", Requested: P" + amount);
            return false;
        }

        //process withdrawal
        setBalance(getBalance() - amount);
        addTransaction("WITHDRAWAL", amount);
        System.out.println("Successfully withdrew P" + amount + " from Investment Account " + getAccountNumber());
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
        addTransaction("INTEREST", interestAmount);
        System.out.println("Monthly interest of P" + interestAmount + "applied to Investment Account");
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

    //override display method to show investment account details
    @Override
    public void displayAccountDetails() {
        super.displayAccountDetails(); // Call parent method to display common details
        System.out.println("Account Type: Investment Account");
        System.out.println("Interest Rate: " + interestRate);
        System.out.println("Minimum Opening Balance: P" + MIN_OPENING_BALANCE);
    }
}
