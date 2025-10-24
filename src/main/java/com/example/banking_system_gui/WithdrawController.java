package com.example.banking_system_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class WithdrawController {

    @FXML
    private ComboBox<?> accountComboBox;

    @FXML
    private TextField amountField;

    @FXML
    private Label balanceLabel;

    @FXML
    void handleCancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("My Accounts");
    }

    @FXML
    void handleConfirmWithdraw(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("My Accounts");
    }

    @FXML
    void handleQuick100(ActionEvent event) {
        amountField.setText("100");
    }

    @FXML
    void handleQuick1000(ActionEvent event) {
        amountField.setText("1000");
    }

    @FXML
    void handleQuick500(ActionEvent event) {
        amountField.setText("500");
    }

    @FXML
    void onAccountSelected(ActionEvent event) {
        // This will be used when an account is selected from the combo box
        System.out.println("Account selected");
    }
}