package com.example.banking_system_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ViewBalanceController {

    @FXML
    private Label chequeAccountNumber;

    @FXML
    private Label chequeBalanceLabel;

    @FXML
    private Label investmentAccountNumber;

    @FXML
    private Label investmentBalanceLabel;

    @FXML
    private Label investmentInterestLabel;

    @FXML
    private Label lastUpdatedLabel;

    @FXML
    private Label savingsAccountNumber;

    @FXML
    private Label savingsBalanceLabel;

    @FXML
    private Label savingsInterestLabel;

    @FXML
    private Label totalBalanceLabel;

    // Initialize method that runs when the scene loads
    public void initialize() {
        loadBalanceData();
        updateLastUpdatedTime();
    }

    @FXML
    void handleBack(ActionEvent event) {
        // Close the current window and return to dashboard
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleRefresh(ActionEvent event) {
        // Refresh the balance data
        loadBalanceData();
        updateLastUpdatedTime();

        // Show confirmation message
        showRefreshConfirmation();
    }

    private void loadBalanceData() {
        // Sample data
        double savingsBalance = 12500.75;
        double investmentBalance = 50000.00;
        double chequeBalance = 2500.50;
        double totalBalance = savingsBalance + investmentBalance + chequeBalance;

        // Update labels with formatted currency
        savingsBalanceLabel.setText(String.format("BWP %.2f", savingsBalance));
        investmentBalanceLabel.setText(String.format("BWP %.2f", investmentBalance));
        chequeBalanceLabel.setText(String.format("BWP %.2f", chequeBalance));
        totalBalanceLabel.setText(String.format("BWP %.2f", totalBalance));

        // Calculate and display interest
        double savingsInterest = savingsBalance * 0.015; // 1.5% monthly interest
        double investmentInterest = investmentBalance * 0.05; // 5% monthly interest

        savingsInterestLabel.setText(String.format("Next interest: BWP %.2f", savingsInterest));
        investmentInterestLabel.setText(String.format("Next interest: BWP %.2f", investmentInterest));

        // Set account numbers
        savingsAccountNumber.setText("ACC-SV001");
        investmentAccountNumber.setText("ACC-IN001");
        chequeAccountNumber.setText("ACC-CH001");
    }

    private void updateLastUpdatedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss");
        String currentTime = LocalDateTime.now().format(formatter);
        lastUpdatedLabel.setText("Last updated: " + currentTime);
    }

    private void showRefreshConfirmation() {
        // You could show a small notification here
        // For now, we'll just update the last updated time which is already done
        System.out.println("Balances refreshed successfully!");
    }
}