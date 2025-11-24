package com.example.banking_system_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import java.io.IOException;

public class BankTellerLoginController {

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
        try {
            // Close the current login window
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            // Load and show the landing page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
            Parent root = loader.load();

            Stage landingStage = new Stage();
            landingStage.setScene(new Scene(root));
            landingStage.setTitle("Banking System");
            landingStage.show();

        } catch (IOException e) {
            System.err.println("Error loading landing page: " + e.getMessage());
            // Fallback: just close the window
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        }
    }

    @FXML
    void handleTellerLogin(ActionEvent event) {
        String employeeId = usernameField.getText();
        String password = passwordField.getText();

        //authentication
        if (authenticateTeller(employeeId, password)) {
            //loads teller dashboard
            loadTellerDashboard();
            //closes login window
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
        } else {
            showAlert(Alert.AlertType.ERROR, "LOGIN FAILED", "INVALID EMPLOYEE ID OR PASSWORD");
        }
    }
    private boolean authenticateTeller(String employeeId, String password) {
        return "teller001".equals(employeeId) && "12345".equals(password);
    }

    private void loadTellerDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/banking_system_gui/BankTellerDashboard.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("BANK TELLER DASHBOARD");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "ERROR", "CANNOT LOAD DASHBOARD:" + e.getMessage());
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


