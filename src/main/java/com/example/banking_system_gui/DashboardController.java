package com.example.banking_system_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.Node;

import java.io.IOException;

public class DashboardController {

    @FXML private Button OpenAccountButton;
    @FXML private Button accountsButton;
    @FXML private Button balanceButton;
    @FXML private Button depositButton;
    @FXML private Button historyButton;
    @FXML private Button logoutButton;
    @FXML private Button withdrawButton;
    @FXML private Label welcomeLabel;

    private String currentCustomerId;
    private Customer currentCustomer;

    @FXML
    void handleAccount(ActionEvent event) {
        try {
            // Load the open account scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OpenAccount.fxml"));
            Parent root = loader.load();

            OpenAccountController openAccountController = loader.getController();
            openAccountController.setCustomerData(currentCustomerId);

            Stage currentStage = (Stage) OpenAccountButton.getScene().getWindow();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Open New Account");
            stage.show();

            currentStage.close();

        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Navigation Error", "Cannot load open account form: " + e.getMessage());
        }
    }

    @FXML
    void handleDeposit(ActionEvent event) {
        try {
            // Load the deposit scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Deposit.fxml"));
            Parent root = loader.load();

            DepositController depositController = loader.getController();
            depositController.setCustomerData(currentCustomerId);

            Stage currentStage = (Stage) depositButton.getScene().getWindow();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Make a Deposit");
            stage.show();

            currentStage.close();

        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Navigation Error", "Cannot load deposit form: " + e.getMessage());
        }
    }

    @FXML
    void handleLogout(ActionEvent event) {
        // Show confirmation dialog
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Confirm Logout");
        alert.setContentText("Are you sure you want to logout?");

        // If user confirms, close the dashboard
        if (alert.showAndWait().get().getText().equals("OK")) {
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.close();

            // Return to login screen
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerLoginView.fxml"));
                Parent root = loader.load();

                Stage loginStage = new Stage();
                loginStage.setScene(new Scene(root));
                loginStage.setTitle("Customer Login");
                loginStage.show();

            } catch (IOException e) {
                System.out.println("Login screen not available");
            }
        }
    }

    @FXML
    void handleTransactionHistory(ActionEvent event) {
        try {
            // Load the transaction history scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TransactionHistory.fxml"));
            Parent root = loader.load();

            HistoryController historyController = loader.getController();
            historyController.setCustomerData(currentCustomerId);

            Stage currentStage = (Stage) historyButton.getScene().getWindow();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Transaction History");
            stage.show();

            currentStage.close();

        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Navigation Error", "Cannot load transaction history: " + e.getMessage());
        }
    }

    @FXML
    void handleViewAccounts(ActionEvent event) {
        try {
            // Load the view accounts scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewAccounts.fxml"));
            Parent root = loader.load();

            //PASSESS THE CUSTOMER DATA
            ViewAccountsController accountsController = loader.getController();
            accountsController.setCustomerData(currentCustomerId);

            Stage currentStage = (Stage) accountsButton.getScene().getWindow();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("My Accounts");
            stage.show();

            currentStage.close();

        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Navigation Error", "Cannot load accounts view: " + e.getMessage());
        }
    }

    @FXML
    void handleViewBalance(ActionEvent event) {
        try {
            // Load the view balance scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewBalance.fxml"));
            Parent root = loader.load();

            ViewBalanceController balanceController = loader.getController();
            balanceController.setCustomerData(currentCustomerId);

            Stage currentStage = (Stage) balanceButton.getScene().getWindow();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Account Balance");
            stage.show();

            currentStage.close();

        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Navigation Error", "Cannot load balance view: " + e.getMessage());
        }
    }

    @FXML
    void handleWithdraw(ActionEvent event) {
        try {
            // Load the withdraw scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/banking_system_gui/Withdraw.fxml"));
            Parent root = loader.load();

            WithdrawController withdrawController = loader.getController();
            withdrawController.setCustomerData(currentCustomerId);

            Stage currentStage = (Stage) withdrawButton.getScene().getWindow();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Make a Withdrawal");
            stage.show();

            currentStage.close();

        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Navigation Error", "Cannot load withdrawal form: " + e.getMessage());
        }
    }

    public void setCustomerData(String customerId) {
        this.currentCustomerId = customerId;
        this.currentCustomer = DataManager.findCustomerById(customerId);

        if (currentCustomer != null) {
            welcomeLabel.setText("Welcome, " + currentCustomer.getFirstName() + "!");
            System.out.println("Dashboard loaded for:" + currentCustomer.getFirstName());
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}