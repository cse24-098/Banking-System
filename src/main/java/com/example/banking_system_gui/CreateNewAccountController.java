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
    void handleCreateAccount(ActionEvent event) throws IOException {
        //goes back to dashboard
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/banking_system_gui/BankTellerDashboard.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Bank Teller Dashboard");
    }

    @FXML
    void handleSearch(ActionEvent event) {
        String searchTerm = searchField.getText().trim();

        if (searchTerm.isEmpty()) {
            System.out.println("Please enter a customer ID");
            return;
        }

        // customer search
        if (searchTerm.equals("C001") || searchTerm.equals("C002")) {
            if (searchTerm.equals("C001")) {
                customerNameLabel.setText("John Doe");
                customerIdLabel.setText("ID: C001");
                customerEmailLabel.setText("Email: john.doe@email.com");
            } else {
                customerNameLabel.setText("Jane Smith");
                customerIdLabel.setText("ID: C002");
                customerEmailLabel.setText("Email: jane.smith@email.com");
            }
            customerDetailsBox.setVisible(true);
            System.out.println("Customer found: " + searchTerm);
        } else {
            System.out.println("Customer not found: " + searchTerm);
            customerDetailsBox.setVisible(false);
        }
    }
}