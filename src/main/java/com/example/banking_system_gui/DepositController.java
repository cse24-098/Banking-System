package com.example.banking_system_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DepositController {

    @FXML
    private Button BackButton;

    @FXML
    private ComboBox<Account> accountComboBox;

    @FXML
    private TextField amountField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Label currentBalanceLabel;

    private Customer currentCustomer;
    private List<Account> accounts;

    public void setAccountData(Account account, String customerId) {
        this.currentCustomer = DataManager.findCustomerById(customerId);
        if (currentCustomer != null) {
            this.accounts = DataManager.loadAccountByCustomerID(customerId);
            setupAccountComboBox();

            // Auto-select the passed account if provided
            if (account != null) {
                accountComboBox.getSelectionModel().select(account);
                updateBalanceDisplay(account);
            }
        }
    }

    public void setCustomerData(String customerId) {
        this.currentCustomer = DataManager.findCustomerById(customerId);
        if (currentCustomer != null) {
            this.accounts = DataManager.loadAccountByCustomerID(customerId);
            setupAccountComboBox();
        }
    }

    private void setupAccountComboBox() {
        accountComboBox.getItems().clear();
        accountComboBox.getItems().addAll(accounts);

        // Set display text for accounts
        accountComboBox.setCellFactory(param -> new ListCell<Account>() {
            @Override
            protected void updateItem(Account account, boolean empty) {
                super.updateItem(account, empty);
                if (empty || account == null) {
                    setText(null);
                } else {
                    String type = "Cheque";
                    if (account instanceof SavingsAccount) type = "Savings";
                    else if (account instanceof InvestmentAccount) type = "Investment";
                    setText(type + " - " + account.getAccountNumber() + " (BWP " + String.format("%.2f", account.getBalance()) + ")");
                }
            }
        });

        accountComboBox.setButtonCell(new ListCell<Account>() {
            @Override
            protected void updateItem(Account account, boolean empty) {
                super.updateItem(account, empty);
                if (empty || account == null) {
                    setText("Choose account to deposit to");
                } else {
                    String type = "Cheque";
                    if (account instanceof SavingsAccount) type = "Savings";
                    else if (account instanceof InvestmentAccount) type = "Investment";
                    setText(type + " - " + account.getAccountNumber());
                }
            }
        });

        // Listen for account selection changes
        accountComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        updateBalanceDisplay(newValue);
                    }
                }
        );
    }

    private void updateBalanceDisplay(Account account) {
        currentBalanceLabel.setText("BWP " + String.format("%.2f", account.getBalance()));
    }

    @FXML
    void handleConfirmDeposit(ActionEvent event) {
        Account selectedAccount = accountComboBox.getValue();
        String amountText = amountField.getText().trim();

        // Validation
        if (selectedAccount == null) {
            showAlert("Error", "Please select an account to deposit to.");
            return;
        }

        if (amountText.isEmpty()) {
            showAlert("Error", "Please enter a deposit amount.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);

            if (amount <= 0) {
                showAlert("Error", "Deposit amount must be positive.");
                return;
            }

            if (amount < 10.00) {
                showAlert("Error", "Minimum deposit amount is BWP 10.00");
                return;
            }

            // Perform the deposit
            if (selectedAccount.deposit(amount)) {
                // Update balance in data file
                DataManager.updateAccountBalance(selectedAccount.getAccountNumber(), selectedAccount.getBalance());

                // Record transaction
                DataManager.saveTransaction(selectedAccount.getAccountNumber(), "DEPOSIT", amount,
                        selectedAccount.getBalance(), "Cash deposit");

                showAlert("Success", "Deposit successful!\n" +
                        "Amount: BWP " + String.format("%.2f", amount) + "\n" +
                        "New Balance: BWP " + String.format("%.2f", selectedAccount.getBalance()));

                // Clear amount field and update display
                amountField.clear();
                updateBalanceDisplay(selectedAccount);

            } else {
                showAlert("Error", "Deposit failed. Please try again.");
            }

        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid amount.");
        }
    }

    @FXML
    void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/banking_system_gui/Dashboard.fxml"));
        Parent root = loader.load();

        DashboardController dashboardController = loader.getController();
        dashboardController.setCustomerData(currentCustomer.getCustomerID());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Customer Dashboard");

    }

    @FXML
    void handleCancel(ActionEvent event) throws IOException {
        clearFormFields();
    }

    private void clearFormFields() {
        // Clear the amount field
        amountField.clear();

        // Clear and reset the account selection if needed
        accountComboBox.getSelectionModel().clearSelection();

        // Reset the balance display
        currentBalanceLabel.setText("BWP 0.00");

        // Optional: Show a confirmation message
        showAlert("Cancelled", "Transaction cancelled. All fields cleared.");
    }

    @FXML
    void handleQuickAmount100(ActionEvent event) {
        amountField.setText("100");
    }

    @FXML
    void handleQuickAmount500(ActionEvent event) {
        amountField.setText("500");
    }

    @FXML
    void handleQuickAmount1000(ActionEvent event) {
        amountField.setText("1000");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}