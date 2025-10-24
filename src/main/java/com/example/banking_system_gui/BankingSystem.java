package com.example.banking_system_gui;

import java.util.Date;

public class BankingSystem {
    public static void main(String[] args) {
        System.out.println("=== BANKING SYSTEM TEST ===\n");

        // Create Customers
        Individual individual = new Individual(123456, new Date(), "IND001", "John", "Doe", "john@email.com", "123-4567");
        Company company = new Company("Tech Corp", "123 Business Ave", 789012, new Date(), "COMP001", "", "", "info@techcorp.com", "987-6543");

        // Create Accounts with CORRECT interest rates based on customer type
        System.out.println("1. CREATING ACCOUNTS WITH CORRECT INTEREST RATES");

        // Individual Savings: 2.5%, Company Savings: 7.5%
        SavingsAccount indSavings = new SavingsAccount("SAV_IND", 1000.0, "Main Branch", new Date(), "IND001", 0.025);
        SavingsAccount compSavings = new SavingsAccount("SAV_COMP", 1000.0, "Main Branch", new Date(), "COMP001", 0.075);

        // Investment: Always 5% for both Individual and Company
        InvestmentAccount indInvestment = new InvestmentAccount("INV_IND", 1000.0, "Main Branch", new Date(), "IND001", 0.05);
        InvestmentAccount compInvestment = new InvestmentAccount("INV_COMP", 1000.0, "Main Branch", new Date(), "COMP001", 0.05);

        // Cheque accounts (no interest)
        ChequeAccount indCheque = new ChequeAccount("CHQ_IND", 1000.0, "Main Branch", new Date(), "IND001");
        ChequeAccount compCheque = new ChequeAccount("CHQ_COMP", 1000.0, "Main Branch", new Date(), "COMP001");

        System.out.println("Individual Savings Rate: " + (indSavings.getInterestRate() * 100) + "%");
        System.out.println("Company Savings Rate: " + (compSavings.getInterestRate() * 100) + "%");
        System.out.println("Investment Rate (both): " + (indInvestment.getInterestRate() * 100) + "%");
        System.out.println();

        // Add accounts to customers
        individual.addAccount(indSavings);
        individual.addAccount(indInvestment);
        individual.addAccount(indCheque);

        company.addAccount(compSavings);
        company.addAccount(compInvestment);
        company.addAccount(compCheque);

        // Test monthly interest application
        System.out.println("2. TESTING MONTHLY INTEREST APPLICATION");

        System.out.println("--- Before Interest ---");
        System.out.println("Individual Savings: P" + indSavings.getBalance());
        System.out.println("Company Savings: P" + compSavings.getBalance());
        System.out.println("Individual Investment: P" + indInvestment.getBalance());

        System.out.println("\n--- Applying Monthly Interest ---");
        indSavings.applyInterest();  // Individual: 2.5% on 1000 = 25
        compSavings.applyInterest(); // Company: 7.5% on 1000 = 75
        indInvestment.applyInterest(); // Both: 5% on 1000 = 50
        compInvestment.applyInterest(); // Both: 5% on 1000 = 50

        System.out.println("\n--- After Interest ---");
        System.out.println("Individual Savings: P" + indSavings.getBalance() + " (should be 1025)");
        System.out.println("Company Savings: P" + compSavings.getBalance() + " (should be 1075)");
        System.out.println("Individual Investment: P" + indInvestment.getBalance() + " (should be 1050)");
        System.out.println("Company Investment: P" + compInvestment.getBalance() + " (should be 1050)");

        System.out.println("\n=== TEST COMPLETED ===");
    }
}
