package com.example.banking_system_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HistoryController {

    @FXML private ComboBox<String> accountFilterComboBox;
    @FXML private ComboBox<String> typeFilterComboBox;
    @FXML private TableView<Transaction> transactionsTable;
    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private Button clearButton;
    @FXML private Button refreshButton;
    @FXML private Button backButton;

    private Customer currentCustomer;
    private List<Account> accounts;
    private ObservableList<Transaction> allTransactions;
    private ObservableList<Transaction> filteredTransactions;

    // Transaction class for table display
    public static class Transaction {
        private final String date;
        private final String account;
        private final String type;
        private final String amount;
        private final String balance;
        private final String description;

        public Transaction(String date, String account, String type, String amount, String balance, String description) {
            this.date = date;
            this.account = account;
            this.type = type;
            this.amount = amount;
            this.balance = balance;
            this.description = description;
        }

        public String getDate() { return date; }
        public String getAccount() { return account; }
        public String getType() { return type; }
        public String getAmount() { return amount; }
        public String getBalance() { return balance; }
        public String getDescription() { return description; }
    }

    public void setCustomerData(String customerId) {
        this.currentCustomer = DataManager.findCustomerById(customerId);
        if (currentCustomer != null) {
            this.accounts = DataManager.loadAccountByCustomerID(customerId);
            setupFilters();
            loadTransactions();
        }
    }

    @FXML
    public void initialize() {
        setupTableColumns();
    }

    private void setupTableColumns() {
        // Set up table columns
        TableColumn<Transaction, String> dateColumn = (TableColumn<Transaction, String>) transactionsTable.getColumns().get(0);
        TableColumn<Transaction, String> accountColumn = (TableColumn<Transaction, String>) transactionsTable.getColumns().get(1);
        TableColumn<Transaction, String> typeColumn = (TableColumn<Transaction, String>) transactionsTable.getColumns().get(2);
        TableColumn<Transaction, String> amountColumn = (TableColumn<Transaction, String>) transactionsTable.getColumns().get(3);
        TableColumn<Transaction, String> balanceColumn = (TableColumn<Transaction, String>) transactionsTable.getColumns().get(4);

        System.out.println("Columns found: " + transactionsTable.getColumns().size());

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        accountColumn.setCellValueFactory(new PropertyValueFactory<>("account"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        System.out.println("Table columns configured");

        // Add description as tooltip
        transactionsTable.setRowFactory(tv -> new TableRow<Transaction>() {
            @Override
            protected void updateItem(Transaction transaction, boolean empty) {
                super.updateItem(transaction, empty);
                if (transaction == null || empty) {
                    setTooltip(null);
                } else {
                    Tooltip tooltip = new Tooltip(transaction.getDescription());
                    setTooltip(tooltip);
                }
            }
        });
    }

    private void setupFilters() {
        // Setup account filter
        accountFilterComboBox.getItems().clear();
        accountFilterComboBox.getItems().add("All Accounts");
        for (Account account : accounts) {
            accountFilterComboBox.getItems().add(account.getAccountNumber());
        }
        accountFilterComboBox.setValue("All Accounts");

        // Setup type filter
        typeFilterComboBox.getItems().clear();
        typeFilterComboBox.getItems().addAll("All Types", "DEPOSIT", "WITHDRAW");
        typeFilterComboBox.setValue("All Types");

        // Add filter listeners
        accountFilterComboBox.valueProperty().addListener((observable, oldValue, newValue) -> applyFilters());
        typeFilterComboBox.valueProperty().addListener((observable, oldValue, newValue) -> applyFilters());
    }

    private void loadTransactions() {
        allTransactions = FXCollections.observableArrayList();

        // Load transactions for all customer accounts
        for (Account account : accounts) {
            List<String> transactionStrings = DataManager.loadTransactionHistory(account.getAccountNumber());

            for (String transactionString : transactionStrings) {
                // Parse the transaction string (format: "TYPE: BWPamount | Balance: BWPbalance | Date | Description")
                Transaction transaction = parseTransactionString(transactionString, account.getAccountNumber());
                if (transaction != null) {
                    allTransactions.add(transaction);
                }
            }
        }

        // Sort by date (newest first)
        allTransactions.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate()));

        filteredTransactions = FXCollections.observableArrayList(allTransactions);
        transactionsTable.setItems(filteredTransactions);
    }

    private Transaction parseTransactionString(String transactionString, String accountNumber) {
        System.out.println("Parsing Transaction:" + transactionString);

        try {
            // The transaction is stored as: accountNumber,type,amount,newBalance,timestamp,description
            String[] data = transactionString.split(",");

            if (data.length >= 6) {
                String type = data[1].trim();
                String amount = data[2].trim();
                String balance = data[3].trim();
                long timestamp = Long.parseLong(data[4].trim());
                String description = data[5].trim();

                System.out.println("Parsed - Type: " + type + ", Amount: " + amount + ", Balance: " + balance + ", Timestamp: " + timestamp);

                // Format date from timestamp
                String formattedDate = formatDateFromTimestamp(timestamp);

                // Format amount with BWP
                String formattedAmount = "BWP " + String.format("%.2f", Double.parseDouble(amount));
                String formattedBalance = "BWP " + String.format("%.2f", Double.parseDouble(balance));

                return new Transaction(formattedDate, accountNumber, type, formattedAmount, formattedBalance, description);
            } else {
                System.out.println("❌ INVALID DATA LENGTH: Expected 6, got " + data.length);
            }
        } catch (Exception e) {
            System.err.println("Error parsing transaction: " + transactionString);
            e.printStackTrace();
        }
        return null;
    }

    private String formatDateFromTimestamp(long timestamp) {
        try {
            Date date = new Date(timestamp);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return outputFormat.format(date);
        } catch (Exception e) {
            return "Unknown Date";
        }
    }

    private void applyFilters() {
        String selectedAccount = accountFilterComboBox.getValue();
        String selectedType = typeFilterComboBox.getValue();
        String searchText = searchField.getText().toLowerCase();

        filteredTransactions.clear();

        for (Transaction transaction : allTransactions) {
            boolean accountMatch = selectedAccount.equals("All Accounts") ||
                    transaction.getAccount().equals(selectedAccount);
            boolean typeMatch = selectedType.equals("All Types") ||
                    transaction.getType().equals(selectedType);
            boolean searchMatch = searchText.isEmpty() ||
                    transaction.getDescription().toLowerCase().contains(searchText) ||
                    transaction.getType().toLowerCase().contains(searchText) ||
                    transaction.getAccount().toLowerCase().contains(searchText);

            if (accountMatch && typeMatch && searchMatch) {
                filteredTransactions.add(transaction);
            }
        }
    }

    @FXML
    void handleSearch(ActionEvent event) {
        applyFilters();
    }

    @FXML
    void handleClearSearch(ActionEvent event) {
        searchField.clear();
        accountFilterComboBox.setValue("All Accounts");
        typeFilterComboBox.setValue("All Types");
        applyFilters();
    }

    @FXML
    void handleRefresh(ActionEvent event) {
        loadTransactions();
        showAlert("Refreshed", "Transaction history updated!");
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
}