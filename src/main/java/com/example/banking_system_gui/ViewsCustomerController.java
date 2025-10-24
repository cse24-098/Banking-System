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

import java.io.IOException;

public class ViewsCustomerController {

    @FXML private TableColumn<Customer, String> idColumn;
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Customer, String> emailColumn;
    @FXML private TableColumn<Customer, String> phoneColumn;
    @FXML private TableColumn<Customer, String> accountsColumn;
    @FXML private Button backButton;
    @FXML private Button clearButton;
    @FXML private Button createAccountBtn;
    @FXML private VBox customerDetailsBox;
    @FXML private TableView<Customer> customersTable;
    @FXML private Label detailEmail;
    @FXML private Label detailId;
    @FXML private Label detailName;
    @FXML private Label detailPhone;
    @FXML private Button editCustomerBtn;
    @FXML private ComboBox<String> filterCombo;
    @FXML private Button newCustomerButton;
    @FXML private Button refreshButton;
    @FXML private Button searchButton;
    @FXML private TextField searchField;
    @FXML private Button viewAccountsBtn;

    private ObservableList<Customer> allCustomers;

    @FXML
    public void initialize() {
        setupTableColumns();
        setupFilterComboBox();
        loadCustomers();

        customersTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCustomerDetails(newValue)
        );
    }

    private void setupTableColumns() {
        // Match these to your Customer class getter methods
        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName")); // We'll fix this
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        accountsColumn.setCellValueFactory(new PropertyValueFactory<>("accounts")); // This will show the list
    }

    private void setupFilterComboBox() {
        filterCombo.getItems().addAll("All Customers", "With Accounts", "No Accounts");
        filterCombo.setValue("All Customers");
    }

    private void loadCustomers() {
        // For testing - add some customers manually
        allCustomers = FXCollections.observableArrayList();

        // Add test customers (remove this later)
        allCustomers.add(new Customer("John", "Doe", "C001", "john@email.com", "123-4567"));
        allCustomers.add(new Customer("Jane", "Smith", "C002", "jane@email.com", "123-4568"));

        customersTable.setItems(allCustomers);
    }

    @FXML
    void handleBack(ActionEvent event) {
        navigateToScene("BankTellerDashboard.fxml", "Bank Teller Dashboard", event);
    }

    @FXML
    void handleClearSearch(ActionEvent event) {
        searchField.clear();
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
        navigateToScene("register-customer.fxml", "Register New Customer", event);
    }

    @FXML
    void handleRefresh(ActionEvent event) {
        loadCustomers();
        showAlert("Refreshed", "Customer list refreshed.");
    }

    @FXML
    void handleSearch(ActionEvent event) {
        showAlert("Search", "Search functionality to be implemented.");
    }

    @FXML
    void handleViewAccounts(ActionEvent event) {
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showAlert("No Customer Selected", "Please select a customer first.");
            return;
        }
        showAlert("View Accounts", "View accounts for: " + selectedCustomer.getFirstName() + " " + selectedCustomer.getLastName());
    }

    private void showCustomerDetails(Customer customer) {
        if (customer == null) {
            customerDetailsBox.setVisible(false);
            return;
        }

        // Use the exact method names from your Customer class
        detailId.setText("ID: " + customer.getCustomerID());
        detailName.setText("Name: " + customer.getFirstName() + " " + customer.getLastName());
        detailEmail.setText("Email: " + customer.getEmail());
        detailPhone.setText("Phone: " + customer.getPhoneNumber());
        customerDetailsBox.setVisible(true);
    }

    private void navigateToScene(String fxmlFile, String title, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/banking_system_gui/LandingPage.fxml"));
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