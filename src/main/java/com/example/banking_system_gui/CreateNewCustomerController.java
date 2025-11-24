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
import javafx.scene.control.Alert;
import java.util.Date;

import java.io.IOException;

public class CreateNewCustomerController {

    @FXML
    private Button backButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField compAddress;

    @FXML
    private TextField compContactPerson;

    @FXML
    private TextField compContactPhone;

    @FXML
    private TextField compEmail;

    @FXML
    private TextField compLocation;

    @FXML
    private TextField compName;

    @FXML
    private TextField compPhone;

    @FXML
    private TextField compRegNumber;

    @FXML
    private TextField dateOfIncorporation;

    @FXML
    private TextField indAddress;

    @FXML
    private TextField indDateOfBirth;

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
    private TextField indResidence;

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

        // Check if individual or company registration
        if (isIndividualRegistration()) {
            registerIndividualCustomer();
        } else if (isBusinessRegistration()) {
            registerBusinessCustomer();
        } else {
            showAlert("Error", "Please fill in either individual or business details");
        }
    }

    private boolean isIndividualRegistration() {
        return !indFirstName.getText().isEmpty() ||
                !indLastName.getText().isEmpty() ||
                !indOmangId.getText().isEmpty();
    }

    private boolean isBusinessRegistration() {
        return !compName.getText().isEmpty() ||
                !compAddress.getText().isEmpty() ||
                !compEmail.getText().isEmpty() ||
                !dateOfIncorporation.getText().isEmpty();
    }

    private void registerIndividualCustomer() {
        String firstName = indFirstName.getText();
        String lastName = indLastName.getText();
        String dateOfBirth = indDateOfBirth.getText();
        String omangId = indOmangId.getText();
        String phone = indPhone.getText();
        String email = indEmail.getText();
        String address = indAddress.getText();
        String residence = indResidence.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || dateOfBirth.isEmpty() ||
                omangId.isEmpty() || phone.isEmpty() || address.isEmpty() || residence.isEmpty()) {
            showAlert("Error", "Please fill in all required fields: First Name, Last Name, Date of Birth, Omang ID, Phone Number, Address, and Residence");
            return;
        }

        try {
            int omangIdNum = Integer.parseInt(omangId);

            // Validate Omang ID length (should be 9 digits)
            if (omangId.length() != 9) {
                showAlert("Error", "Omang ID must be exactly 9 digits");
                return;
            }

            // Check if Omang ID already exists
            if (DataManager.isOmangIdExists(omangIdNum)) {
                showAlert("Error", "Omang ID " + omangId + " already exists in the system!");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Omang ID must be a valid number");
            return;
        }


        String customerId = DataManager.generateCustomerId();

        // Create Individual customer with all the new fields
        Individual individual = new Individual(
                Integer.parseInt(omangId),  // idNumber
                parseDate(dateOfBirth),     // dateOfBirth (you'll need to create parseDate method)
                customerId,                 // customerID
                firstName,                  // firstName
                lastName,                   // lastName
                email,                      // email
                phone,                       // phoneNumber
                address,
                residence
        );

        DataManager.saveCustomer(individual);
        showAlert("Success", "Customer registered successfully!\nCustomer ID: " + customerId + "\nGive this ID to the customer.");
        clearAllFields();

    }

    private Date parseDate(String dateString) {
        try {
            // Simple date parsing - you might want to use a specific format
            // For now, return current date
            return new Date();
        } catch (Exception e) {
            return new Date(); // Return current date as fallback
        }
    }

    private void registerBusinessCustomer() {
        String companyName = compName.getText();
        String companyAddress = compAddress.getText();
        String location = compLocation.getText();
        String companyPhone = compPhone.getText();
        String contactPerson = compContactPerson.getText();
        String contactPhone = compContactPhone.getText();
        String email = compEmail.getText();
        String incorporationDate = dateOfIncorporation.getText();
        String companyRegNumberStr = compRegNumber.getText();

        if (companyName.isEmpty() || companyAddress.isEmpty() ||
                location.isEmpty() || companyPhone.isEmpty() || contactPerson.isEmpty() || email.isEmpty()) {
            showAlert("Error", "Please fill in all required fields");
            return;
        }

        int companyRegistrationNumber;
        try {
            companyRegistrationNumber = Integer.parseInt(companyRegNumberStr);

            // Check if company registration number already exists
            if (DataManager.isCompanyRegNumberExists(companyRegistrationNumber)) {
                showAlert("Error", "Company Registration Number " + companyRegNumberStr + " already exists in the system!");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Company registration number must be a valid number");
            return;
        }

        String customerId = DataManager.generateCustomerId();

        // Parse registration number
        int companyRegNumber;
        try {
            companyRegNumber = Integer.parseInt(companyRegNumberStr);
        } catch (NumberFormatException e) {
            showAlert("Error", "Company registration number must be a valid number");
            return;
        }

        // Create Company customer
        Company company = new Company(
                companyName,                    // companyName
                companyAddress,                 // companyAddress
                location,                       //location
                companyRegNumber,               // company Registration Number
                parseDate(incorporationDate),   // dateOfIncorporation
                customerId,                     // customerID
                email,                          // email
                companyPhone,                   // company phoneNumber
                contactPerson,                  //contact person
                contactPhone                    //contact phone for the contact person
        );

        DataManager.saveCustomer(company);
        showAlert("Success", "Business customer registered successfully!\nCustomer ID: " + customerId + "\nGive this ID to the customer.");
        clearAllFields();
    }

    private void clearAllFields() {
        // Clear individual fields
        indFirstName.clear();
        indLastName.clear();
        indDateOfBirth.clear();
        indOmangId.clear();
        indPhone.clear();
        indEmail.clear();
        indAddress.clear();
        indResidence.clear();

        // Clear business fields
        compName.clear();
        compRegNumber.clear();
        compAddress.clear();
        compLocation.clear();
        compPhone.clear();
        compContactPerson.clear();
        compContactPhone.clear();
        compEmail.clear();
        dateOfIncorporation.clear();
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}