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

public class WithdrawController {

    @FXML private ComboBox<Account> accountComboBox;
    @FXML private TextField amountField;
    @FXML private Label balanceLabel;
    @FXML private Button confirmButton;
    @FXML private Button cancelButton;
    @FXML private Button BackButton;

    private Customer currentCustomer;
    private List<Account> accounts;

    public void setAccountData(Account account, String customerId) {
        this.currentCustomer = DataManager.findCustomerById(customerId);
        if (currentCustomer != null) {
            this.accounts = DataManager.loadAccountByCustomerID(customerId);
            setupAccountComboBox();

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
                    setText("Choose account to withdraw from");
                } else {
                    String type = "Cheque";
                    if (account instanceof SavingsAccount) type = "Savings";
                    else if (account instanceof InvestmentAccount) type = "Investment";
                    setText(type + " - " + account.getAccountNumber());
                }
            }
        });

        accountComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        updateBalanceDisplay(newValue);
                    }
                }
        );
    }

    private void updateBalanceDisplay(Account account) {
        balanceLabel.setText("BWP " + String.format("%.2f", account.getBalance()));
    }

    @FXML
    void handleCancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("My Accounts");
    }

    @FXML
    void handleConfirmWithdraw(ActionEvent event) {
        Account selectedAccount = accountComboBox.getValue();
        String amountText = amountField.getText().trim();

        if (selectedAccount == null) {
            showAlert("Error", "Please select an account to withdraw from.");
            return;
        }

        if (amountText.isEmpty()) {
            showAlert("Error", "Please enter a withdrawal amount.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);

            if (amount <= 0) {
                showAlert("Error", "Withdrawal amount must be positive.");
                return;
            }

            // KEY DIFFERENCE FROM DEPOSIT: Check sufficient funds
            if (amount > selectedAccount.getBalance()) {
                showAlert("Error", "Insufficient funds!\nAvailable: BWP " +
                        String.format("%.2f", selectedAccount.getBalance()) +
                        "\nRequested: BWP " + String.format("%.2f", amount));
                return;
            }

            // Perform the withdrawal
            if (selectedAccount.withdraw(amount)) {
                // Update balance in data file
                DataManager.updateAccountBalance(selectedAccount.getAccountNumber(), selectedAccount.getBalance());

                // Record transaction
                DataManager.saveTransaction(selectedAccount.getAccountNumber(), "WITHDRAW", amount,
                        selectedAccount.getBalance(), "Cash withdrawal");

                showAlert("Success", "Withdrawal successful!\n" +
                        "Amount: BWP " + String.format("%.2f", amount) + "\n" +
                        "New Balance: BWP " + String.format("%.2f", selectedAccount.getBalance()));

                amountField.clear();
                updateBalanceDisplay(selectedAccount);

            } else {
                showAlert("Error", "Withdrawal failed. Please try again.");
            }

        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid amount.");
        }
    }


    @FXML
    void handleQuick100(ActionEvent event) {
        amountField.setText("100");
    }

    @FXML
    void handleQuick1000(ActionEvent event) {
        amountField.setText("1000");
    }

    @FXML
    void handleQuick500(ActionEvent event) {
        amountField.setText("500");
    }

    @FXML
    void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Parent root = loader.load();

        DashboardController dashboardController = loader.getController();
        dashboardController.setCustomerData(currentCustomer.getCustomerID());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Customer Dashboard");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void onAccountSelected(ActionEvent event) {
        // This will be used when an account is selected from the combo box
        System.out.println("Account selected");
    }
}