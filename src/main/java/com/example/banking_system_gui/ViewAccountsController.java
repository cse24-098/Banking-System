package com.example.banking_system_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewAccountsController {

    @FXML private Label accountBalanceLabel;
    @FXML private VBox accountInfoBox;
    @FXML private Label accountNumberLabel;
    @FXML private Label accountTypeLabel;
    @FXML private Button backButton;
    @FXML private Button chequeButton;
    @FXML private Button depositButton;
    @FXML private Label interestRateLabel;
    @FXML private Button investmentButton;
    @FXML private Button savingsButton;
    @FXML private Label selectedAccountLabel;
    @FXML private Label welcomeLabel;
    @FXML private Button withdrawButton;

    @FXML
    void backToDashboard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Customer Dashboard");
    }

    @FXML
    void handleDeposit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Deposit.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Deposit Money");
    }

    @FXML
    void handleWithdraw(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Withdraw.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Withdraw Money");
    }

    @FXML
    void showChequeAccount(ActionEvent event) {
        accountTypeLabel.setText("Cheque Account");
        accountBalanceLabel.setText("BWP 2,500.00");
        accountNumberLabel.setText("CHQ-67890");
        interestRateLabel.setText("Account Type: Salary Account • No interest");
    }

    @FXML
    void showInvestmentAccount(ActionEvent event) {
        accountTypeLabel.setText("Investment Account");
        accountBalanceLabel.setText("BWP 5,000.00");
        accountNumberLabel.setText("INV-54321");
        interestRateLabel.setText("Interest Rate: 5% monthly • 30-day notice period");
    }

    @FXML
    void showSavingsAccount(ActionEvent event) {
        accountTypeLabel.setText("Savings Account");
        accountBalanceLabel.setText("BWP 1,500.00");
        accountNumberLabel.setText("SAV-12345");
        interestRateLabel.setText("Interest Rate: 2.5% monthly • Minimum balance: BWP 100");
    }
}