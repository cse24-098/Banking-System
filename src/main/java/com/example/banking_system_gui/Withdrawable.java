package com.example.banking_system_gui;

public interface Withdrawable {
    boolean withdraw(double amount);
    double getAvailableBalance();
}

