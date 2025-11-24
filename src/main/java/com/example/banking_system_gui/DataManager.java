package com.example.banking_system_gui;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DataManager {
    private static final String CUSTOMERS_FILE = "data/customers.txt";
    private static final String ACCOUNTS_FILE = "data/accounts.txt";
    private static final String TRANSACTIONS_FILE = "data/transactions.txt";
    private static final String PASSWORDS_FILE = "data/passwords.txt";

    //creates the data directory and files if they don't exist
    static {
        createDataDirectory();
    }

    private static void createDataDirectory() {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }

        try {
            new File(CUSTOMERS_FILE).createNewFile();
            new File(ACCOUNTS_FILE).createNewFile();
            new File(TRANSACTIONS_FILE).createNewFile();
            new File(PASSWORDS_FILE).createNewFile();
        } catch (IOException e) {
            System.err.println("Error creating data files: " + e.getMessage());
        }
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static void saveCustomer(Customer customer) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CUSTOMERS_FILE, true))) {
            if (customer instanceof Individual) {
                Individual ind = (Individual) customer;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                writer.println("INDIVIDUAL," +
                        customer.getCustomerID() + "," +
                        customer.getFirstName() + "," +
                        customer.getLastName() + "," +
                        customer.getEmail() + "," +
                        customer.getPhoneNumber() + "," +
                        ind.getIdNumber() + "," +
                        dateFormat.format(ind.getDateOfBirth()) + "," +
                        ind.getAddress() + "," +
                        ind.getResidence());

            } else if (customer instanceof Company) {
                Company comp = (Company) customer;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                writer.println("COMPANY," +
                        customer.getCustomerID() + "," +
                        customer.getEmail() + "," +
                        customer.getPhoneNumber() + "," +
                        comp.getCompanyName() + "," +
                        comp.getCompanyAddress() + "," +
                        comp.getLocation() + "," +
                        comp.getCompanyRegNumber() + "," +
                        dateFormat.format(comp.getDateOfIncorporation()) + "," +
                        comp.getContactPerson() + "," +
                        comp.getContactPhone());
            }

            showAlert("Success", "Customer saved: " + customer.getCustomerID());
        } catch (IOException e) {
            showAlert("Error", "Could not save customer: " + e.getMessage());
        }
    }

    public static List<Customer> loadAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");

                if (data[0].equals("INDIVIDUAL") && data.length == 10) {
                    try {
                        Individual individual = new Individual(
                                Integer.parseInt(data[6]),           // idNumber
                                dateFormat.parse(data[7]),           // dateOfBirth
                                data[1],                             // customerID
                                data[2],                             // firstName
                                data[3],                             // lastName
                                data[4],                             // email
                                data[5],                             // phoneNumber
                                data[8],                             // address
                                data[9]                              // residence
                        );
                        customers.add(individual);
                    } catch (Exception e) {
                        System.err.println("Error parsing individual customer date: " + data[7]);
                        System.err.println("Line: " + line);
                    }

                } else if (data[0].equals("COMPANY") && data.length == 11) {
                    try {
                        Company company = new Company(
                                data[4],                             // companyName
                                data[5],                             // companyAddress
                                data[6],                             // location
                                Integer.parseInt(data[7]),           // companyNumber
                                dateFormat.parse(data[8]),           // dateOfIncorporation
                                data[1],                             // customerID
                                data[2],                             // email
                                data[3],                             // phoneNumber
                                data[9],                             // contactPerson
                                data[10]                             // contactPhone
                        );
                        customers.add(company);
                    } catch (Exception e) {
                        System.err.println("Error parsing company customer date: " + data[8]);
                        System.err.println("Line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading customers: " + e.getMessage());
        }

        return customers;
    }

    public static Customer findCustomerById(String customerId) {
        System.out.println("=== DEBUG: FINDING CUSTOMER BY ID ===");
        System.out.println("Looking for: '" + customerId + "'");

        List<Customer> customers = loadAllCustomers();

        System.out.println("Total customers loaded: " + customers.size());

        for (Customer customer : customers) {
            System.out.println("Available customer: " + customer.getCustomerID());
            if (customer.getCustomerID().equals(customerId)) {
                System.out.println("✅ FOUND MATCH!");
                return customer;
            }
        }
        System.out.println("❌ NO MATCH FOUND");
        return null;
    }

    //accounts operations
    public static void saveAccount(Account account, String customerId) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNTS_FILE, true))) {
           //writes accounts in one line
           String type = "CHEQUE";
           double interestRate = 0.0;

           if (account instanceof SavingsAccount) {
               type = "SAVINGS";
               interestRate = ((SavingsAccount) account).getInterestRate();
           } else if (account instanceof InvestmentAccount) {
               type = "INVESTMENT";
               interestRate = ((InvestmentAccount) account).getInterestRate();
           }

           //format the date for saving
           String dateString = String.valueOf(account.getDateOpened().getTime()); //save as timestamp

           writer.println(account.getAccountNumber() + "," +
                         customerId + "," +
                         type +"," +
                         account.getBalance() + "," +
                         interestRate + "," +
                         dateString); //saves the actual date

           showAlert("Success", "Account created:" + account.getAccountNumber());
        } catch (IOException e) {
            showAlert("Error", "Could not create account");
        }
    }

    public static List<Account> loadAccountByCustomerID(String customerId) {
        List<Account> accounts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) !=null ) {

                String[] data = line.split(",");

                if (data.length == 6 && data[1].equals(customerId)) {

                    String accountNumber = data[0];
                    String type = data[2];
                    double balance = Double.parseDouble(data[3]);
                    double interestRate = Double.parseDouble(data[4]);
                    long timestamp = Long.parseLong(data[5]);
                    Date originalDate = new Date(timestamp);

                    Account account;

                    if (type.equals("SAVINGS")) {
                        account = new SavingsAccount(accountNumber, balance, "Main Branch", originalDate, customerId, interestRate);
                    } else if (type.equals("INVESTMENT")) {
                        account = new InvestmentAccount(accountNumber, balance, "Main Branch", originalDate, customerId, interestRate);
                    } else {
                        account = new ChequeAccount(accountNumber, balance, "Main Branch", originalDate, customerId);
                    }

                    accounts.add(account);
                }
            }
        } catch (IOException e) {
            //FILES MIGHT NOT EXIST
        }
        return accounts;
    }

    public static void saveTransaction(String accountNumber, String type, double amount, double newBalance, String description) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TRANSACTIONS_FILE, true))) {

            String transaction = accountNumber + "," +
                    type + "," +
                    amount + "," +
                    newBalance + "," +
                    System.currentTimeMillis() + "," +
                    description;
            writer.println(transaction);
        } catch (IOException e) {
            System.err.println("Error saving transactions:" + e.getMessage());
        }
    }

    public static List<String> loadTransactionHistory(String accountNumber) {
        List<String> transactions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTIONS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");
                if (data.length >= 5 && data[0].equals(accountNumber)) {
                    transactions.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading transactions: " + e.getMessage());
        }
        return transactions;
    }

    public static void updateAccountBalance(String accountNumber, double newBalance) {
        List<String> lines = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");
                if (data.length == 6 && data[0].equals(accountNumber)) {

                    //updates the balance
                    line = data[0] + "," +
                           data[1] + "," +
                           data[2] + "," +
                           newBalance + "," +
                           data[4] + "," +
                           data[5];
                    updated = true;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            showAlert("Error", "Could not update account balance: " + e.getMessage());
            return;
        }

        //writes back all lines if updated
        if (updated) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNTS_FILE))) {
                for (String line : lines) {
                    writer.println(line);
                }
            } catch (IOException e) {
                showAlert("Error", "Could not save updated account data: " + e.getMessage());
            }
        }
    }

    //checks if customer ID already exists
    public static boolean isCustomerIdExists(String customerId) {
        return findCustomerById(customerId) != null;
    }

    //checks if account number already exixts
    public static boolean isAccountNumberExists(String accountNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] data =line.split(",");
                if (data.length > 0 && data[0].equals(accountNumber)) {
                    return true;
                }
            }
        } catch (IOException e) {
            //file might not exist
        }
        return false;
    }

    //generates customer IDs and accountNumbers
    public static String generateAccountNumber(String accountType) {
        String prefix;
        switch (accountType.toUpperCase()) {
            case "SAVINGS": prefix = "SAV"; break;
            case "INVESTMENT": prefix = "INV"; break;
            case "CHEQUE": prefix = "CHQ"; break;
            default: prefix = "ACC";
        }

        // Count existing accounts of this specific type
        int typeCount = countAccountsByType(accountType);
        String newNumber = prefix + String.format("%04d", typeCount + 1);

        // Ensure it's unique
        while (isAccountNumberExists(newNumber)) {
            typeCount++;
            newNumber = prefix + String.format("%04d", typeCount + 1);
        }
        return newNumber;
    }

    private static int countAccountsByType(String accountType) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3 && data[2].equalsIgnoreCase(accountType)) {
                    count++;
                }
            }
        } catch (IOException e) {
            // File might not exist yet
        }
        return count;
    }


    public static String generateCustomerId() {
        List<Customer> customers = loadAllCustomers();
        int nextId = customers.size() + 1;
        return "C" + String.format("%04d", nextId);
    }

    public static void setPassword(String customerId, String password) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PASSWORDS_FILE, true))) {
            writer.println(customerId + ":" + password);
        } catch (IOException e) {
            throw new RuntimeException("Could not save password: " + e.getMessage());
        }
    }

    public static boolean hasPassword(String customerId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PASSWORDS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(customerId)) {
                    return true;
                }
            }
        } catch (IOException e) {
            // File might not exist yet
        }
        return false;
    }

    public static boolean verifyPassword(String customerId, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PASSWORDS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(customerId)) {
                    return parts[1].equals(password);
                }
            }
        } catch (IOException e) {
            // File might not exist yet
        }
        return false;
    }

    // Check if Omang ID already exists
    public static boolean isOmangIdExists(int omangId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals("INDIVIDUAL") && data.length >= 7) {
                    try {
                        int existingOmang = Integer.parseInt(data[6]);
                        if (existingOmang == omangId) {
                            return true;
                        }
                    } catch (NumberFormatException e) {
                        // Skip if Omang ID is not a number
                    }
                }
            }
        } catch (IOException e) {
            // File might not exist yet
        }
        return false;
    }

    // Check if Company Registration Number already exists
    public static boolean isCompanyRegNumberExists(int companyRegNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals("COMPANY") && data.length >= 8) {
                    try {
                        int existingRegNumber = Integer.parseInt(data[7]);
                        if (existingRegNumber == companyRegNumber) {
                            return true;
                        }
                    } catch (NumberFormatException e) {
                        // Skip if registration number is not a number
                    }
                }
            }
        } catch (IOException e) {
            // File might not exist yet
        }
        return false;
    }

    // Check if customer already has an account of specific type
    public static boolean hasAccountType(String customerId, String accountType) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3 && data[1].equals(customerId) && data[2].equals(accountType)) {
                    return true; // Customer already has this account type
                }
            }
        } catch (IOException e) {
            // File might not exist yet
        }
        return false;
    }

}
