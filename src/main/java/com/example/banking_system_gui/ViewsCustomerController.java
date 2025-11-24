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
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

import java.io.IOException;

public class ViewsCustomerController {

    @FXML private TableColumn<Customer, String> accountsColumn;
    @FXML private Button backButton;
    @FXML private TableColumn<Customer, String> balanceColumn;
    @FXML private Label businessCountLabel;
    @FXML private Button clearButton;
    @FXML private TableView<Customer> customersTable;
    @FXML private TableColumn<Customer, String> emailColumn;
    @FXML private ComboBox<String> filterCombo;
    @FXML private TableColumn<Customer, String> idColumn;
    @FXML private Label individualCountLabel;
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private Button newCustomerButton;
    @FXML private TableColumn<Customer, String> phoneColumn;
    @FXML private Button refreshButton;
    @FXML private Button searchButton;
    @FXML private TextField searchField;
    @FXML private Label totalCustomersLabel;
    @FXML private TableColumn<Customer, String> typeColumn;

    private ObservableList<Customer> allCustomers;

    @FXML
    public void initialize() {
        setupTableColumns();
        setupFilterComboBox();
        loadCustomers();
        updateCustomerCounts();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        nameColumn.setCellValueFactory(cellData -> {
            Customer customer = cellData.getValue();
            String fullName = customer.getFirstName() + " " + customer.getLastName();
            return new javafx.beans.property.SimpleStringProperty(fullName);
                });

        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        typeColumn.setCellValueFactory(cellData -> {
            Customer customer = cellData.getValue();
            String customerType;
            if (customer instanceof Company) {
                customerType = "Business";
            } else if (customer instanceof Individual) {
                customerType = "Individual";
            } else {
                customerType = "Unknown";
            }
            return new javafx.beans.property.SimpleStringProperty(customerType);
        });


        accountsColumn.setCellValueFactory(cellData -> {
            Customer customer = cellData.getValue();
            if(customer.getAccounts().isEmpty()) {
                return new javafx.beans.property.SimpleStringProperty("No Accounts");// This will show the list
            }

            //creates a string with account types
            StringBuilder accountInfo = new StringBuilder();
            for (Account account : customer.getAccounts()) {
                if (accountInfo.length() > 0) {
                    accountInfo.append(", ");
                }

                if (account instanceof SavingsAccount) {
                    accountInfo.append("Savings");
                } else if (account instanceof InvestmentAccount) {
                    accountInfo.append("Investment");
                } else if (account instanceof ChequeAccount) {
                    accountInfo.append("Cheque");
                }
            }
            return new javafx.beans.property.SimpleStringProperty(accountInfo.toString());
        });

        balanceColumn.setCellValueFactory(cellData -> {
            Customer customer = cellData.getValue();
            double totalBalance = 0.0;
            for (Account account : customer.getAccounts()) {
                totalBalance += account.getBalance();
            }
            return new javafx.beans.property.SimpleStringProperty("P" + String.format("%.2f", totalBalance));
        });
    }

    private void setupFilterComboBox() {
        filterCombo.getItems().addAll("All Customers", "With Accounts", "No Accounts");
        filterCombo.setValue("All Customers");

        filterCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            applyFilter(newValue);
        });
    }

    private void applyFilter(String filterType) {
        switch (filterType) {
            case "All Customers" :
                customersTable.setItems(allCustomers);
                break;
            case "With Accounts" :
                ObservableList<Customer> withAccounts = FXCollections.observableArrayList();
                for (Customer customer : allCustomers) {
                    if (!customer.getAccounts().isEmpty()) {
                        withAccounts.add(customer);
                    }
                }
                customersTable.setItems(withAccounts);
                break;
            case "No Accounts":
                ObservableList<Customer> noAccounts = FXCollections.observableArrayList();
                for (Customer customer : allCustomers) {
                    if (customer.getAccounts().isEmpty()) {
                        noAccounts.add(customer);
                    }
                }
                customersTable.setItems(noAccounts);
                break;
        }
    }

    private void loadCustomers() {
        // load customers from DataManager
        allCustomers = FXCollections.observableArrayList(DataManager.loadAllCustomers());

        //load and link accounts for each customer
        for (Customer customer : allCustomers) {
            List<Account> customerAccounts = DataManager.loadAccountByCustomerID(customer.getCustomerID());
            for (Account account : customerAccounts) {
                customer.addAccount(account);
            }
        }

        customersTable.setItems(allCustomers);
        updateCustomerCounts();
    }

    private void updateCustomerCounts() {
        int individualCount = 0;
        int businessCount = 0;

        for (Customer customer : allCustomers) {
            if (customer instanceof Company) {
                businessCount++;
            } else {
                individualCount++;
            }
        }

        totalCustomersLabel.setText("Total: " + allCustomers.size());
        individualCountLabel.setText("Individuals: " + individualCount);
        businessCountLabel.setText("Businesses: " + businessCount);
    }

    @FXML
    void handleBack(ActionEvent event) {
        navigateToScene("BankTellerDashboard.fxml", "Bank Teller Dashboard", event);
    }

    @FXML
    void handleClearSearch(ActionEvent event) {
        searchField.clear();
        customersTable.setItems(allCustomers);//resets to show all customers
    }

    @FXML
    void handleCreateAccount(ActionEvent event) {
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showAlert("No Customer Selected", "Please select a customer first.");
            return;
        }
        showAlert("Create Account", "Create account for: " + selectedCustomer.getFirstName() + " " + selectedCustomer.getLastName());
    }

    @FXML
    void handleEditCustomer(ActionEvent event) {
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showAlert("No Customer Selected", "Please select a customer first.");
            return;
        }
        showAlert("Edit Customer", "Edit customer: " + selectedCustomer.getCustomerID());
    }

    @FXML
    void handleNewCustomer(ActionEvent event) {
        navigateToScene("/com/example/banking_system_gui/CreateNewCustomer.fxml", "Register New Customer", event);
    }

    @FXML
    void handleRefresh(ActionEvent event) {
        loadCustomers();
        showAlert("Refreshed", "Customer list refreshed.");
    }

    @FXML
    void handleSearch(ActionEvent event) {
        String searchTerm = searchField.getText().trim().toLowerCase();

        if (searchTerm.isEmpty()){
            customersTable.setItems(allCustomers);
            showAlert("Search", "Showing all customers");
            return;
        }

        ObservableList<Customer> filteredCustomers = FXCollections.observableArrayList();

        for (Customer customer : allCustomers) {
            if (customer.getCustomerID().toLowerCase().contains(searchTerm) ||
                customer.getFirstName().toLowerCase().contains(searchTerm) ||
                customer.getLastName().toLowerCase().contains(searchTerm) ||
                customer.getEmail().toLowerCase().contains(searchTerm) ||
                customer.getPhoneNumber().toLowerCase().contains(searchTerm)) {
                filteredCustomers.add(customer);
            }
        }

        customersTable.setItems(filteredCustomers);

        if (filteredCustomers.isEmpty()) {
            showAlert("Search", "No customers found matching: " + searchTerm);
        } else {
            showAlert("Search","found" + filteredCustomers.size() + "customer(s) matching:" + searchTerm);
        }
    }

    @FXML
    void handleViewAccounts(ActionEvent event) {
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showAlert("No Customer Selected", "Please select a customer first.");
            return;
        }

        if (selectedCustomer.getAccounts().isEmpty()) {
            showAlert("No Account", " This customers has no accounts");
            return;
        }

        //shows detailed account information
        StringBuilder accountsInfo = new StringBuilder();
        accountsInfo.append("Accounts for " + selectedCustomer.getFirstName() + " " + selectedCustomer.getLastName() + ":\n\n");

        for (Account account : selectedCustomer.getAccounts()) {
            String accountType = getAccountType(account);
            accountsInfo.append(String.format("Account: %s\nType: %s\nBalance: P%.2f\nBranch: %s\n\n",
                    account.getAccountNumber(),
                    accountType,
                    account.getBalance(),
                    account.getBranch()));
        }
        showAlert("Customer Accounts", accountsInfo.toString());
    }


    private String getAccountType(Account account) {
        if (account instanceof SavingsAccount) return "Savings";
        if (account instanceof InvestmentAccount) return "Investment";
        if (account instanceof ChequeAccount) return "Cheque";
        return "Unknown";
    }

    private void navigateToScene(String fxmlFile, String title, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/banking_system_gui/BankTellerDashboard.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Cannot load page: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}