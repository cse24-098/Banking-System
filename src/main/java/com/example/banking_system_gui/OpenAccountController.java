package com.example.banking_system_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class OpenAccountController {

    @FXML
    private Button InvestmentButton;

    @FXML
    private Button SavingsButton;

    @FXML
    private Button chequeButton;

    @FXML
    private TextField chequeCustomerId;

    @FXML
    private TextField chequeDeposit;

    @FXML
    private TextField chequeFullName;

    @FXML
    private TextField companyAddress;

    @FXML
    private TextField companyName;

    @FXML
    private VBox employmentSection;

    @FXML
    private TextField investmentCustomerId;

    @FXML
    private ChoiceBox<?> investmentCustomerType;

    @FXML
    private TextField investmentDeposit;

    @FXML
    private TextField investmentFullName;

    @FXML
    private TextField savingsCustomerId;

    @FXML
    private ChoiceBox<?> savingsCustomerType;

    @FXML
    private TextField savingsDeposit;

    @FXML
    private TextField savingsFullName;

    @FXML
    void handleBack(ActionEvent event) {

    }

    @FXML
    void openChequeAccount(ActionEvent event) {

    }

    @FXML
    void openInvestmentAccount(ActionEvent event) {

    }

    @FXML
    void openSavingsAccount(ActionEvent event) {

    }

}

