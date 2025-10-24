package com.example.banking_system_gui;

import java.util.Date;

public class ChequeAccount extends Account implements Withdrawable {

    // Constructor
    public ChequeAccount(String accountNumber, double balance, String branch, Date dateOpened, String customerID) {
        super(accountNumber, balance, branch, dateOpened, customerID);
    }

    // deposit
    @Override
    public boolean deposit(double amount) {
        if (amount > 0) {
            setBalance(getBalance() + amount);
            addTransaction("DEPOSIT", amount);
            System.out.println("Deposited P" + amount + " to Cheque Account " + getAccountNumber());
            return true;
        }
        System.out.println("Deposit amount must be positive");
        return false;
    }

    // withdrawal
    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= getBalance()) {
            setBalance(getBalance() - amount);
            addTransaction("WITHDRAWAL", amount);
            System.out.println("Withdrew P" + amount + " from Cheque Account " + getAccountNumber());
            return true;
        }
        System.out.println("Withdrawal failed - insufficient funds or invalid amount");
        return false;
    }

    // Withdrawable interface implementation
    @Override
    public double getAvailableBalance() {
        return getBalance();
    }

}
