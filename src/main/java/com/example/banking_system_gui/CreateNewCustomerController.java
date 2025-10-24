package com.example.banking_system_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateNewCustomerController {

    @FXML
    private Button backButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField compBusinessType;

    @FXML
    private TextField compContactPerson;

    @FXML
    private TextField compName;

    @FXML
    private TextField compPhone;

    @FXML
    private TextField compRegNumber;

    @FXML
    private TextField indEmail;

    @FXML
    private TextField indFirstName;

    @FXML
    private TextField indLastName;

    @FXML
    private TextField indOmangId;

    @FXML
    private TextField indPhone;

    @FXML
    private Button registerCustomerButton;

    @FXML
    void handleBack(ActionEvent event) {
        try {
            java.net.URL fxmlFile = getClass().getResource("/com/example/banking_system_gui/BankTellerDashboard.fxml");

            if (fxmlFile == null) {
                System.out.println("ERROR: Cannot find BankTellerDashboard.fxml");
                System.out.println("Looking in: " + getClass().getResource("/com/example/banking_system_gui/"));
                return;
            }

            System.out.println("Found FXML file: " + fxmlFile);

            Parent root = FXMLLoader.load(fxmlFile);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Bank Teller Dashboard");
            stage.show();
        } catch(IOException e) {
            System.out.println("Error loading dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleCancel(ActionEvent event) {
        // Clear all fields
        clearAllFields();
    }

    @FXML
    void handleRegister(ActionEvent event) {
        // Register customer logic
        System.out.println("Register button clicked");

        // Check if individual or company registration
        if (isIndividualRegistration()) {
            registerIndividualCustomer();
        } else if (isBusinessRegistration()) {
            registerBusinessCustomer();
        } else {
            System.out.println("Please fill in either individual or business details");
        }
    }

    private boolean isIndividualRegistration() {
        return !indFirstName.getText().isEmpty() ||
                !indLastName.getText().isEmpty() ||
                !indOmangId.getText().isEmpty();
    }

    private boolean isBusinessRegistration() {
        return !compName.getText().isEmpty() ||
                !compRegNumber.getText().isEmpty() ||
                !compContactPerson.getText().isEmpty();
    }

    private void registerIndividualCustomer() {
        String firstName = indFirstName.getText();
        String lastName = indLastName.getText();
        String omangId = indOmangId.getText();
        String email = indEmail.getText();
        String phone = indPhone.getText();

        System.out.println("Registering Individual: " + firstName + " " + lastName);
    }

    private void registerBusinessCustomer() {
        String companyName = compName.getText();
        String regNumber = compRegNumber.getText();
        String contactPerson = compContactPerson.getText();
        String businessType = compBusinessType.getText();
        String phone = compPhone.getText();

        System.out.println("Registering Business: " + companyName);
    }

    private void clearAllFields() {
        // Clear individual fields
        indFirstName.clear();
        indLastName.clear();
        indOmangId.clear();
        indEmail.clear();
        indPhone.clear();

        // Clear business fields
        compName.clear();
        compRegNumber.clear();
        compContactPerson.clear();
        compBusinessType.clear();
        compPhone.clear();
    }
}