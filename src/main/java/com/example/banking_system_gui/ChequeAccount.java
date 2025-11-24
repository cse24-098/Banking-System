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
            return true;
        }

        return false;
    }

    // withdrawal
    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= getBalance()) {
            setBalance(getBalance() - amount);

            return true;
        }
        return false;
    }

    // Withdrawable interface implementation
    @Override
    public double getAvailableBalance() {
        return getBalance();
    }

    @Override
    public double getInterestRate() {
        return 0.0;
    }
}
