package com.example.banking_system_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class DepositController {

    @FXML
    private Button BackButton;

    @FXML
    private ComboBox<?> accountComboBox;

    @FXML
    private TextField amountField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Label currentBalanceLabel;

    @FXML
    void handleBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/banking_system_gui/Dashboard.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("My Accounts");
    }

    @FXML
    void handleCancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/banking_system_gui/Dashboard.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("My Accounts");
    }

    @FXML
    void handleConfirmDeposit(ActionEvent event) throws IOException {
        //  goes back to accounts page
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/banking_system_gui/Dashboard.fxml.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("My Accounts");
    }

    @FXML
    void handleQuickAmount100(ActionEvent event) {
        amountField.setText("100");
    }

    @FXML
    void handleQuickAmount500(ActionEvent event) {
        amountField.setText("500");
    }

    @FXML
    void handleQuickAmount1000(ActionEvent event) {
        amountField.setText("1000");
    }
}