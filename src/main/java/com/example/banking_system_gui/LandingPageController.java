package com.example.banking_system_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class LandingPageController {

    @FXML
    private Button CustomerLoginButton;

    @FXML
    private Button bankTellerButton;

    @FXML
    void handleCustomerLogin(ActionEvent event) {
        try {
            // Load customer login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
            Parent root = loader.load();

            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.setTitle("Customer Login");
            loginStage.show();

            // Close the landing page
            Stage landingStage = (Stage) CustomerLoginButton.getScene().getWindow();
            landingStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "ERROR", "CANNOT LOAD CUSTOMER LOGIN:" + e.getMessage());
        }
    }

    @FXML
    void handleBankTellerLogin(ActionEvent event) {
        try {
            // Load teller login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/banking_system_gui/BankTellerLogin.fxml"));
            Parent root = loader.load();

            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.setTitle("Bank Teller Login");
            loginStage.show();

            // Close the landing page
            Stage landingStage = (Stage) bankTellerButton.getScene().getWindow();
            landingStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "ERROR", "CANNOT LOAD TELLER LOGIN:" + e.getMessage());
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
