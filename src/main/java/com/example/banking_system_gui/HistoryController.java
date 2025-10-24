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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {

    @FXML
    private ComboBox<String> accountFilterComboBox;

    @FXML
    private Button backButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button refreshButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<?> transactionsTable;

    @FXML
    private ComboBox<String> typeFilterComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set up account filter ComboBox
        ObservableList<String> accounts = FXCollections.observableArrayList(
                "All Accounts",
                "Savings Account",
                "Investment Account",
                "Cheque Account"
        );
        accountFilterComboBox.setItems(accounts);
        accountFilterComboBox.setValue("All Accounts");

        // Set up transaction type filter ComboBox
        ObservableList<String> types = FXCollections.observableArrayList(
                "All Types",
                "Deposit",
                "Withdrawal",
                "Transfer"
        );
        typeFilterComboBox.setItems(types);
        typeFilterComboBox.setValue("All Types");
    }

    @FXML
    void handleBack(ActionEvent event) {
        try {
            Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("/com/example/banking_system_gui/Dashboard.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Bank Teller Dashboard");
            stage.show();
        } catch(java.io.IOException e) {
            e.printStackTrace();
            System.out.println("Error loading dashboard: " + e.getMessage());
        }
    }

    @FXML
    void handleClearSearch(ActionEvent event) {
        searchField.clear();
        accountFilterComboBox.setValue("All Accounts");
        typeFilterComboBox.setValue("All Types");
    }

    @FXML
    void handleRefresh(ActionEvent event) {
        // Refresh the transaction table
        System.out.println("Refreshing transactions...");
    }

    @FXML
    void handleSearch(ActionEvent event) {
        String searchText = searchField.getText();
        String selectedAccount = accountFilterComboBox.getValue();
        String selectedType = typeFilterComboBox.getValue();

        System.out.println("Searching transactions:");
        System.out.println("Search text: " + searchText);
        System.out.println("Account filter: " + selectedAccount);
        System.out.println("Type filter: " + selectedType);
    }
}