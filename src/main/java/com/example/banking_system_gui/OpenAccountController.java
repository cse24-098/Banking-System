package com.example.banking_system_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

public class OpenAccountController {

    // Savings Account Fields
    @FXML private ChoiceBox<String> savingsCustomerType;
    @FXML private TextField savingsCustomerId;
    @FXML private TextField savingsFullName;
    @FXML private TextField savingsDeposit;
    @FXML private Button SavingsButton;

    // Investment Account Fields
    @FXML private ChoiceBox<String> investmentCustomerType;
    @FXML private TextField investmentCustomerId;
    @FXML private TextField investmentFullName;
    @FXML private TextField investmentDeposit;
    @FXML private Button InvestmentButton;

    // Cheque Account Fields
    @FXML private TextField chequeCustomerId;
    @FXML private TextField chequeFullName;
    @FXML private VBox employmentSection;
    @FXML private TextField companyName;
    @FXML private TextField companyAddress;
    @FXML private TextField chequeDeposit;
    @FXML private Button chequeButton;

    private Customer currentCustomer;

    public void setCustomerData(String customerId) {
        this.currentCustomer = DataManager.findCustomerById(customerId);
        if (currentCustomer != null) {
            // Autofill customer information in all tabs
            autoFillCustomerInfo();
            setupCustomerTypes();
        }
    }

    @FXML
    public void intialize() {
        setupCustomerTypes();
    }

    private void setupCustomerTypes() {
        savingsCustomerType.getItems().addAll("Individual", "Company");
        investmentCustomerType.getItems().addAll("Individual", "Company");

        //defaul value
        savingsCustomerType.setValue("Individual");
        savingsCustomerType.setValue("Individual");
    }

    private void autoFillCustomerInfo() {
        // Auto-fill customer info in all tabs
        String customerId = currentCustomer.getCustomerID();
        String fullName = currentCustomer.getFirstName() + " " + currentCustomer.getLastName();

        // Savings tab
        savingsCustomerId.setText(customerId);
        savingsFullName.setText(fullName);

        // Investment tab
        investmentCustomerId.setText(customerId);
        investmentFullName.setText(fullName);

        // Cheque tab
        chequeCustomerId.setText(customerId);
        chequeFullName.setText(fullName);
    }

    @FXML
    void openSavingsAccount(ActionEvent event) {
        try {

            if (DataManager.hasAccountType(currentCustomer.getCustomerID(), "SAVINGS")) {
                showAlert("Account Limit", "You already have a Savings Account.\n" + "Each customer can only have one account of each type.");
                return;
            }

            if (!validateCustomer(savingsCustomerId.getText(), savingsFullName.getText())) {
                return;
            }

            double depositAmount = Double.parseDouble(savingsDeposit.getText());
            if (depositAmount < 10.00) {
                showAlert("Error", "Minimum deposit for Savings Account is BWP 10.00");
                return;
            }

            double interestRate = 0.025;
            if ("Company".equals(savingsCustomerType.getValue())) {
                interestRate = 0.05;
            }

            String accountNumber = DataManager.generateAccountNumber("SAVINGS");
            SavingsAccount newAccount = new SavingsAccount(
                    accountNumber,
                    depositAmount,
                    "Main Branch",
                    new Date(),
                    currentCustomer.getCustomerID(),
                    interestRate
            );

            DataManager.saveAccount(newAccount, currentCustomer.getCustomerID());//saves account

            DataManager.saveTransaction(accountNumber, "DEPOSIT", depositAmount, depositAmount, "Intial deposit- Account opening");

            showAlert("Success", "Savings Account opened successfully!\n" +
                    "Account Number: " + accountNumber + "\n" +
                    "Initial Deposit: BWP " + String.format("%.2f", depositAmount) + "\n" +
                    "Interest Rate: " + (interestRate * 100) + "% monthly");
            clearForm();
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid deposit amount");
        }
    }

    @FXML
    void openChequeAccount(ActionEvent event) {
        try {

            if (DataManager.hasAccountType(currentCustomer.getCustomerID(), "CHEQUE")) {
                showAlert("Account Limit", "You already have a Cheque Account.\n" + "Each customer can only have one account of each type.");
                return;
            }

            // Validation
            if (!validateCustomer(chequeCustomerId.getText(), chequeFullName.getText())) {
                return;
            }

            double depositAmount = Double.parseDouble(chequeDeposit.getText());
            if (depositAmount < 10.00) {
                showAlert("Error", "Minimum deposit for Cheque Account is BWP 10.00");
                return;
            }

            // Create account
            String accountNumber = DataManager.generateAccountNumber("CHEQUE");
            ChequeAccount newAccount = new ChequeAccount(
                    accountNumber,
                    depositAmount,
                    "Main Branch",
                    new Date(),
                    currentCustomer.getCustomerID()
            );

            // Save account
            DataManager.saveAccount(newAccount, currentCustomer.getCustomerID());

            // Record initial deposit transaction
            DataManager.saveTransaction(accountNumber, "DEPOSIT", depositAmount,
                    depositAmount, "Initial deposit - Account opening");

            showAlert("Success", "Cheque Account opened successfully!\n" +
                    "Account Number: " + accountNumber + "\n" +
                    "Initial Deposit: BWP " + String.format("%.2f", depositAmount) + "\n" +
                    "Account Type: Transaction account");

            clearForm();

        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid deposit amount");
        }
    }

    @FXML
    void openInvestmentAccount(ActionEvent event) {
        try {

            if (DataManager.hasAccountType(currentCustomer.getCustomerID(), "INVESTMENT")) {
                showAlert("Account Limit", "You already have an Investment Account.\n" + "Each customer can only have one account of each type.");
                return;
            }

            // Validation
            if (!validateCustomer(investmentCustomerId.getText(), investmentFullName.getText())) {
                return;
            }

            double depositAmount = Double.parseDouble(investmentDeposit.getText());
            if (depositAmount < 500.00) {
                showAlert("Error", "Minimum deposit for Investment Account is BWP 500.00");
                return;
            }

            // Create account
            String accountNumber = DataManager.generateAccountNumber("INVESTMENT");
            InvestmentAccount newAccount = new InvestmentAccount(
                    accountNumber,
                    depositAmount,
                    "Main Branch",
                    new Date(),
                    currentCustomer.getCustomerID(),
                    0.05 // 5% interest for all
            );

            // Save account
            DataManager.saveAccount(newAccount, currentCustomer.getCustomerID());

            // Record initial deposit transaction
            DataManager.saveTransaction(accountNumber, "DEPOSIT", depositAmount,
                    depositAmount, "Initial deposit - Account opening");

            showAlert("Success", "Investment Account opened successfully!\n" +
                    "Account Number: " + accountNumber + "\n" +
                    "Initial Deposit: BWP " + String.format("%.2f", depositAmount) + "\n" +
                    "Interest Rate: 5% monthly");

            clearForm();

        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid deposit amount");
        }
    }

    private boolean validateCustomer(String enteredId, String enteredName) {
        // Verify customer ID matches logged-in customer
        if (!enteredId.equals(currentCustomer.getCustomerID())) {
            showAlert("Error", "Customer ID verification failed");
            return false;
        }

        // Verify name matches (basic check)
        String expectedName = currentCustomer.getFirstName() + " " + currentCustomer.getLastName();
        if (!enteredName.equalsIgnoreCase(expectedName)) {
            showAlert("Error", "Name verification failed");
            return false;
        }

        return true;
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

    private void clearForm() {
        // Clear all deposit fields
        savingsDeposit.clear();
        investmentDeposit.clear();
        chequeDeposit.clear();

        // Reset customer types
        savingsCustomerType.setValue("Individual");
        investmentCustomerType.setValue("Individual");

        // Clear employment fields
        companyName.clear();
        companyAddress.clear();
    }



}

