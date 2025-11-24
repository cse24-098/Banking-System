package com.example.banking_system_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class BankTellerDashboardController {

    @FXML
    private Button createAccountButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button manageAccountsButton;

    @FXML
    private Button registerCustomerButton;

    @FXML
    private Button viewCustomersButton;

    @FXML
    void handleCreateAccount(ActionEvent event) {
        try {
            // Load the Create Account scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateNewAccount.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Create New Account");
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Cannot load Create Account page", "Please try again later.");
            e.printStackTrace();
        }
    }

    @FXML
    void handleLogout(ActionEvent event) {
        // Show confirmation dialog before logging out
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("You will be returned to the login screen.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Load the login scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/banking_system_gui/BankTellerLogin.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Banking System - Login");
                stage.show();

            } catch (IOException e) {
                showErrorAlert("Cannot load Login page", "Please try again later.");
                e.printStackTrace();
            }
        }
    }


    @FXML
    void handleRegisterCustomer(ActionEvent event) {
        try {
            // Load the Create New Customer scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/banking_system_gui/CreateNewCustomer.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Register New Customer");
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Cannot load Register Customer page", "Please try again later.");
            e.printStackTrace();
        }
    }

    @FXML
    void handleViewCustomers(ActionEvent event) {
        try {
            // Load the View Customers scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/banking_system_gui/ViewsCustomer.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("View All Customers");
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Cannot load View Customers page", "Please try again later.");
            e.printStackTrace();
        }
    }

    @FXML
    void handleApplyInterest(ActionEvent event) {
        try {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Apply Monthly Interest");
            confirmAlert.setHeaderText("Apply Monthly Interest to All Accounts");
            confirmAlert.setContentText("This will calculate and apply monthly interest to all accounts:\n\n" +
                    "• Individual Savings: 2.5% monthly interest\n" +
                    "• Company Savings: 5.0% monthly interest\n" +
                    "• All Investment Accounts: 5.0% monthly interest\n\n" +
                    "Continue?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int accountsUpdated = applyInterestToAllAccounts();
                showAlert("Interest Applied",
                        "Monthly interest applied to " + accountsUpdated + " accounts successfully!\n\n" +
                                "Interest Rates Applied:\n" +
                                "• Individual Savings Accounts: 2.5%\n" +
                                "• Company Savings Accounts: 5.0%\n" +
                                "• All Investment Accounts: 5.0%");
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to apply interest: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int applyInterestToAllAccounts() {
        int count = 0;
        List<Customer> allCustomers = DataManager.loadAllCustomers();

        for (Customer customer : allCustomers) {
            List<Account> accounts = DataManager.loadAccountByCustomerID(customer.getCustomerID());
            for (Account account : accounts) {
                if (account instanceof SavingsAccount) {
                    SavingsAccount savings = (SavingsAccount) account;
                    double oldBalance = savings.getBalance();

                    // Different rates based on customer types
                    double interestRate;
                    String interestDescription;

                    if (customer instanceof Individual) {
                        interestRate = 0.025; // 2.5% for individuals
                        interestDescription = "Monthly interest applied (2.5%)";
                    } else { // Company
                        interestRate = 0.05;  // 5.0% for companies
                        interestDescription = "Monthly interest applied (5.0%)";
                    }

                    // Apply the appropriate interest rate
                    double interestAmount = oldBalance * interestRate;
                    savings.setBalance(oldBalance + interestAmount);

                    DataManager.updateAccountBalance(savings.getAccountNumber(), savings.getBalance());
                    DataManager.saveTransaction(savings.getAccountNumber(), "INTEREST",
                            interestAmount, savings.getBalance(), interestDescription);
                    count++;

                } else if (account instanceof InvestmentAccount) {
                    InvestmentAccount investment = (InvestmentAccount) account;
                    double oldBalance = investment.getBalance();
                    investment.applyInterest();
                    double interestAmount = investment.getBalance() - oldBalance;

                    DataManager.updateAccountBalance(investment.getAccountNumber(), investment.getBalance());
                    DataManager.saveTransaction(investment.getAccountNumber(), "INTEREST",
                            interestAmount, investment.getBalance(),
                            "Monthly interest applied (5.0%)");
                    count++;
                }
            }
        }
        return count;
    }

    // Helper method to show error alerts
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}