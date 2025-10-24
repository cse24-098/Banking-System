package com.example.banking_system_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    void handleCancel(ActionEvent event) {
        //closes the login window
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        //returns to the landing page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
            Parent root = loader.load();

            Stage landingStage = new Stage();
            landingStage.setScene(new Scene(root));
            landingStage.setTitle("Banking System");
            landingStage.show();
        } catch (IOException e) {
            System.out.println("Landing Page Not Available");
        }
    }

    @FXML
    void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticationCustomer(username, password)) {
            openDashboard();

            //closes login window
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
        } else {
            //when login has failed
            showAlert(AlertType.ERROR, "LOGIN FAILED", "INVALID USERNAME OR PASSWORD. PLEASE TRY AGAIN");

            //clears the password field
            passwordField.clear();
            passwordField.requestFocus();
        }
    }

    private boolean authenticationCustomer(String username, String password) {
        if("customer".equals(username) && "12345".equals(password)) {
            return true;
        }

        if ("customer1".equals(username) && "1234".equals(password)) {
            return true;
        }

        return false;
    }

    private void openDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Customer Dashboard");
            stage.show();

        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Navigation Error", "Cannot load customer dashboard: " + e.getMessage());
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
