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
import java.util.List;
import javafx.scene.control.Alert;

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

    private Customer currentCustomer;
    private List<Account> accounts;
    private Account selectedAccount;

    //call this method when the customer logs in
    public void setCustomerData(String customerId) {

        this.currentCustomer = DataManager.findCustomerById(customerId);
        if (currentCustomer != null) {

            // Load customer's accounts
            this.accounts = DataManager.loadAccountByCustomerID(customerId);

            for (Account account : accounts) {
            }

            currentCustomer.getAccounts().addAll(accounts);
            updateDisplay();
            setupAccountButtons();
        }
    }

    private void updateDisplay() {
        if (currentCustomer != null) {
            welcomeLabel.setText("Welcome, " + currentCustomer.getFirstName() + "!");

            // Show account count in the main label
            if (accounts.isEmpty()) {
                selectedAccountLabel.setText("You have no accounts yet");
            } else {
                selectedAccountLabel.setText("You have " + accounts.size() + " account(s) - Select one from sidebar");
            }
        }
    }

    private void setupAccountButtons() {
        // Hide all buttons initially
        savingsButton.setVisible(false);
        investmentButton.setVisible(false);
        chequeButton.setVisible(false);

        // Show buttons only for account types the customer has
        for (Account account : accounts) {
            if (account instanceof SavingsAccount) {
                savingsButton.setVisible(true);
                savingsButton.setText("Savings - " + account.getAccountNumber());
            } else if (account instanceof InvestmentAccount) {
                investmentButton.setVisible(true);
                investmentButton.setText("Investment - " + account.getAccountNumber());
            } else if (account instanceof ChequeAccount) {
                chequeButton.setVisible(true);
                chequeButton.setText("Cheque - " + account.getAccountNumber());
            }
        }

        // Auto-select first account if available
        if (!accounts.isEmpty()) {
            selectAccount(accounts.get(0));
        }
    }

    private void selectAccount(Account account) {
        this.selectedAccount = account;
        displayAccountDetails(account);
    }

    private void displayAccountDetails(Account account) {
        accountNumberLabel.setText(account.getAccountNumber());
        accountBalanceLabel.setText("BWP " + String.format("%.2f", account.getBalance()));

        if (account instanceof SavingsAccount) {
            accountTypeLabel.setText("Savings Account");
            double interestRate = ((SavingsAccount) account).getInterestRate() * 100;
            interestRateLabel.setText("Interest Rate: " + interestRate + "% monthly • Minimum balance: BWP 100");
        } else if (account instanceof InvestmentAccount) {
            accountTypeLabel.setText("Investment Account");
            double interestRate = ((InvestmentAccount) account).getInterestRate() * 100;
            interestRateLabel.setText("Interest Rate: " + interestRate + "% monthly • 30-day notice period");
        } else {
            accountTypeLabel.setText("Cheque Account");
            interestRateLabel.setText("Account Type: Transaction Account • No interest");
        }

        selectedAccountLabel.setText("Viewing: " + accountTypeLabel.getText());
    }

    @FXML
    void showSavingsAccount(ActionEvent event) {
        for (Account account : accounts) {
            if (account instanceof SavingsAccount) {
                selectAccount(account);
                break;
            }
        }
    }

    @FXML
    void showInvestmentAccount(ActionEvent event) {
        for (Account account : accounts) {
            if (account instanceof InvestmentAccount) {
                selectAccount(account);
                break;
            }
        }
    }

    @FXML
    void showChequeAccount(ActionEvent event) {
        for (Account account : accounts) {
            if (account instanceof ChequeAccount) {
                selectAccount(account);
                break;
            }
        }
    }

    @FXML
    void handleDeposit(ActionEvent event) throws IOException {
        if (selectedAccount == null) {
            showAlert("No Account Selected", "Please select an account first.");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Deposit.fxml"));
        Parent root = loader.load();

        // Pass the selected account to deposit controller
        DepositController depositController = loader.getController();
        depositController.setAccountData(selectedAccount, currentCustomer.getCustomerID());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Deposit Money");
    }

    @FXML
    void handleWithdraw(ActionEvent event) throws IOException {
        if (selectedAccount == null) {
            showAlert("No Account Selected", "Please select an account first.");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Withdraw.fxml"));
        Parent root = loader.load();

        // Pass the selected account to withdraw controller
        WithdrawController withdrawController = loader.getController();
        withdrawController.setAccountData(selectedAccount, currentCustomer.getCustomerID());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Withdraw Money");
    }


    @FXML
    void backToDashboard(ActionEvent event) throws IOException {
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
}