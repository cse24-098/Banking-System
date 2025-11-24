package com.example.banking_system_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ViewBalanceController {

    @FXML private Label totalBalanceLabel;
    @FXML private Label accountCountLabel;
    @FXML private VBox accountsContainer;

    private Customer currentCustomer;
    private List<Account> accounts;

    public void setCustomerData(String customerId) {
        this.currentCustomer = DataManager.findCustomerById(customerId);
        if (currentCustomer != null) {
            this.accounts = DataManager.loadAccountByCustomerID(customerId);
            currentCustomer.getAccounts().addAll(accounts);
            updateDisplay();
        }
    }

    private void updateDisplay() {
        if (currentCustomer != null && accounts != null) {
            // Calculate total balance
            double totalBalance = 0.0;
            for (Account account : accounts) {
                totalBalance += account.getBalance();
            }

            // Update labels
            totalBalanceLabel.setText("BWP " + String.format("%.2f", totalBalance));
            accountCountLabel.setText("Across " + accounts.size() + " account" + (accounts.size() != 1 ? "s" : ""));

            // Clear and rebuild account cards
            accountsContainer.getChildren().clear();
            createAccountCards();
        }
    }

    private void createAccountCards() {
        for (Account account : accounts) {
            HBox accountCard = createAccountCard(account);
            accountsContainer.getChildren().add(accountCard);
        }
    }

    private HBox createAccountCard(Account account) {
        HBox card = new HBox();
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 2);");
        card.setSpacing(15);

        // Account icon based on type
        String emoji = "💳";
        String type = "Cheque";
        String color = "#3498db";

        if (account instanceof SavingsAccount) {
            emoji = "💰";
            type = "Savings";
            color = "#27ae60";
        } else if (account instanceof InvestmentAccount) {
            emoji = "📈";
            type = "Investment";
            color = "#e67e22";
        }

        // Icon/emoji
        Label iconLabel = new Label(emoji);
        iconLabel.setStyle("-fx-font-size: 24;");

        // Account info
        VBox infoBox = new VBox(5);
        infoBox.setStyle("-fx-pref-width: 200;");

        Label typeLabel = new Label(type + " Account");
        typeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16; -fx-text-fill: " + color + ";");

        Label numberLabel = new Label(account.getAccountNumber());
        numberLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #7f8c8d;");

        infoBox.getChildren().addAll(typeLabel, numberLabel);

        // Balance
        VBox balanceBox = new VBox(5);
        balanceBox.setStyle("-fx-alignment: CENTER_RIGHT;");

        Label balanceLabel = new Label("BWP " + String.format("%.2f", account.getBalance()));
        balanceLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label availableLabel = new Label("Available");
        availableLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #7f8c8d;");

        balanceBox.getChildren().addAll(balanceLabel, availableLabel);

        card.getChildren().addAll(iconLabel, infoBox, balanceBox);

        return card;
    }


    @FXML
    void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Parent root = loader.load();

        DashboardController dashboardController = loader.getController();
        dashboardController.setCustomerData(currentCustomer.getCustomerID());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Customer Dashboard");
    }

    @FXML
    void handleRefresh(ActionEvent event) {
        if (currentCustomer != null) {
            // Reload accounts to get latest balances
            this.accounts = DataManager.loadAccountByCustomerID(currentCustomer.getCustomerID());
            updateDisplay();
            showAlert("Refreshed", "Balances updated successfully!");
        }
    }

    @FXML
    void handleViewAccounts(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewAccounts.fxml"));
        Parent root = loader.load();

        ViewAccountsController accountsController = loader.getController();
        accountsController.setCustomerData(currentCustomer.getCustomerID());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("My Accounts");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
