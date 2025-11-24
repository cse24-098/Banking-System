package com.example.banking_system_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateNewAccountController implements Initializable {

    @FXML private ComboBox<String> accountTypeCombo;
    @FXML private Button cancelButton;
    @FXML private Button clearButton;
    @FXML private Button createButton;
    @FXML private VBox customerDetailsBox;
    @FXML private Label customerEmailLabel;
    @FXML private Label customerIdLabel;
    @FXML private Label customerNameLabel;
    @FXML private TextField initialDepositField;
    @FXML private Button searchButton;
    @FXML private TextField searchField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set up account type ComboBox
        ObservableList<String> accountTypes = FXCollections.observableArrayList(
                "Savings Account",
                "Investment Account",
                "Cheque Account"
        );
        accountTypeCombo.setItems(accountTypes);
    }

    @FXML
    void handleCancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/banking_system_gui/BankTellerDashboard.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Bank Teller Dashboard");
    }

    @FXML
    void handleClear(ActionEvent event) {
        // Clear all fields
        searchField.clear();
        initialDepositField.clear();
        accountTypeCombo.getSelectionModel().clearSelection();
        customerDetailsBox.setVisible(false);
    }

    @FXML
    void handleCreateAccount(ActionEvent event) {
        String customerId = customerIdLabel.getText().replace("ID:", "").trim();

        double initialDeposit;
        try {
            initialDeposit = Double.parseDouble(initialDepositField.getText());

            if (initialDeposit < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid deposit amount");
            return;
        }

        if (customerId.isEmpty()) {
            showAlert("Error", "Please search and select a customer first");
            return;
        }

        //validates input
        if (accountTypeCombo.getValue() == null) {
            showAlert("Error", "Please select an account type");
            return;
        }

        //create account based on type
        String accountNumber = generateAccountNumber(accountTypeCombo.getValue());
        Account newAccount = createAccountByType(accountTypeCombo.getValue(), accountNumber, initialDeposit, customerId);

        if (newAccount != null) {
            //sasves using DataManager
            DataManager.saveAccount(newAccount, customerId);

            showAlert("Success", "Account created!\n" + "Account:" + accountNumber + "\n" + "Type: " + accountTypeCombo.getValue() + "\n" + "Deposit: BWP " + initialDeposit);

            handleClear(event);
        }

        String selectedAccountType = accountTypeCombo.getValue();
        String accountTypeForCheck = "";

        switch (selectedAccountType) {
            case "Savings Account":
                accountTypeForCheck = "SAVINGS";
                break;
            case "Investment Account":
                accountTypeForCheck = "INVESTMENT";
                break;
            case "Cheque Account":
                accountTypeForCheck = "CHEQUE";
                break;
        }

        if (DataManager.hasAccountType(customerId, accountTypeForCheck)) {
            showAlert("Account Limit",
                    "Customer " + customerId + " already has a " + selectedAccountType + ".\n" +
                            "Each customer can only have one account of each type.");
            return;
        }


    }

    private Account createAccountByType(String type, String number, double balance, String customerId) {
        switch (type) {
            case "Savings Account":
                return new SavingsAccount(number,balance,"Main Branch", new java.util.Date(), customerId, 0.025);
            case "Investment Account":
                return new InvestmentAccount(number, balance, "Main Branch", new java.util.Date(), customerId, 0.05);
            case "Cheque Account":
                return new ChequeAccount(number, balance, "Main Branch", new java.util.Date(), customerId);
            default: return null;
        }
    }

    private String generateAccountNumber(String accountType) {
        // Convert the display name to the type string DataManager expects
        String typeForDataManager;
        switch (accountType) {
            case "Savings Account":
                typeForDataManager = "SAVINGS";
                break;
            case "Investment Account":
                typeForDataManager = "INVESTMENT";
                break;
            case "Cheque Account":
                typeForDataManager = "CHEQUE";
                break;
            default:
                typeForDataManager = "SAVINGS"; // fallback
        }
        return DataManager.generateAccountNumber(typeForDataManager);
    }

    @FXML
    void handleSearch(ActionEvent event) {
        String searchTerm = searchField.getText().trim();

        System.out.println("=== DEBUG: SEARCHING FOR CUSTOMER ===");
        System.out.println("Search term: '" + searchTerm + "'");

        if (searchTerm.isEmpty()) {
            showAlert("Error", "Please enter a customer ID");
            return;
        }

        // customer search
        Customer customer = DataManager.findCustomerById(searchTerm);

        System.out.println("Customer found: " + (customer != null));
        if (customer != null) {
            System.out.println("Customer ID: " + customer.getCustomerID());
            System.out.println("Customer Name: " + customer.getFirstName() + " " + customer.getLastName());
        }

       if (customer != null) {
           customerNameLabel.setText(customer.getFirstName() + " " + customer.getLastName());
           customerIdLabel.setText("ID: " + customer.getCustomerID());
           customerEmailLabel.setText("Email: " + customer.getEmail());
           customerDetailsBox.setVisible(true);
       } else {
           showAlert("Not Found", "Customer Not Found.");
           customerDetailsBox.setVisible(false);
       }
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}