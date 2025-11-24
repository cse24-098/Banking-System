package com.example.banking_system_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreatePasswordController {

    @FXML private Button backButton;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button createPasswordButton;
    @FXML private TextField customerIdField;
    @FXML private Button loginButton;
    @FXML private PasswordField passwordField;

    @FXML void handleBack(ActionEvent event) { //takes you back to the landing page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Banking System");
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Cannot load landing page: " + e.getMessage());
        }
    }

    @FXML
    void handleCreatePassword(ActionEvent event) {
        String customerId = customerIdField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        //validation
        if (customerId.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "Please fill in all fields");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match");
            confirmPasswordField.clear();
            confirmPasswordField.requestFocus();
            return;
        }

        if (password.length() < 6) {
            showAlert("Error", "Password must be at least 6 characters long");
            passwordField.clear();
            confirmPasswordField.clear();
            passwordField.requestFocus();
            return;
        }

        //checks if the customer exists
        Customer customer = DataManager.findCustomerById(customerId);
        if (customer == null) {
            showAlert("Error", "Customer ID not found. Please check your ID and try again.");
            customerIdField.clear();
            customerIdField.requestFocus();
            return;
        }

        //checks if the password already exists
        if (DataManager.hasPassword(customerId)) {
            showAlert("Error", "Password already set for this customer ID. Please login instead.");
            return;
        }

        //saves password
        try {
            DataManager.setPassword(customerId, password);
            showAlert("Success", "Password created successfully! You can now login.");

            // Clear fields
            customerIdField.clear();
            passwordField.clear();
            confirmPasswordField.clear();

            // Optionally go to login page
            handleLogin(event);

        } catch (Exception e) {
            showAlert("Error", "Failed to create password: " + e.getMessage());
        }

    }

    @FXML
    void handleLogin(ActionEvent event) { //takes you to the customer login view
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/banking_system_gui/CustomerLoginView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Customer Login");
            stage.show();

        } catch (IOException e) {
            showAlert("Error", "Cannot load login page: " + e.getMessage());
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

