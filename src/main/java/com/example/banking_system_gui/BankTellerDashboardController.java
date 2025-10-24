package com.example.banking_system_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class BankTellerDashboardController {

    @FXML
    private Button createAccountButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button manageAccountsButton;

    @FXML
    private Button registerCustomerButton;

    @FXML
    private Button viewCustomersButton;

    @FXML
    void handleCreateAccount(ActionEvent event) {
        try {
            // Load the Create Account scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateNewAccount.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Create New Account");
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Cannot load Create Account page", "Please try again later.");
            e.printStackTrace();
        }
    }

    @FXML
    void handleLogout(ActionEvent event) {
        // Show confirmation dialog before logging out
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("You will be returned to the login screen.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Load the login scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/banking_system_gui/BankTellerLogin.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Banking System - Login");
                stage.show();

            } catch (IOException e) {
                showErrorAlert("Cannot load Login page", "Please try again later.");
                e.printStackTrace();
            }
        }
    }


    @FXML
    void handleRegisterCustomer(ActionEvent event) {
        try {
            // Load the Create New Customer scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/banking_system_gui/CreateNewCustomer.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Register New Customer");
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Cannot load Register Customer page", "Please try again later.");
            e.printStackTrace();
        }
    }

    @FXML
    void handleViewCustomers(ActionEvent event) {
        try {
            // Load the View Customers scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/banking_system_gui/ViewsCustomer.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("View All Customers");
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Cannot load View Customers page", "Please try again later.");
            e.printStackTrace();
        }
    }

    // Helper method to show error alerts
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to show information alerts
    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}