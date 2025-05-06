import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankSystem {

    // user class to store common fields like username and password
    public static class User {
        protected String username;
        protected String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    // account class extends user and adds fields like balance, transaction history, etc.
    public static class account extends User {
        private double balance;
        private ArrayList<String> transactionHistory; // stores transaction logs
        private int failedLoginAttempts; // tracks failed login attempts
        private boolean isLocked; // indicates if the account is locked

        public account(String username, String password) {
            super(username, password); // calls the constructor of the user class
            this.balance = 0.0;
            this.transactionHistory = new ArrayList<>();
            this.failedLoginAttempts = 0;
            this.isLocked = false;
        }

        public double gatBalance() {
            return balance;
        }

        public boolean isLocked() {
            return isLocked;
        }

        public void incrementFailedLoginAttempts() {
            failedLoginAttempts++;
            if (failedLoginAttempts >= 3) { // locks the account after 3 failed attempts
                isLocked = true;
            }
        }

        public void resetFailedLoginAttempts() {
            failedLoginAttempts = 0; // resets failed login attempts
        }

        public void unlockAccount() {
            isLocked = false; // unlocks the account
            resetFailedLoginAttempts();
        }

        public void deposit(double amount) {
            if (amount > 0) {
                balance += amount;
                transactionHistory.add("Deposited: $" + amount); // logs the deposit
            }
        }

        public boolean withdraw(double amount) {
            if (amount > 0 && amount <= balance) {
                balance -= amount;
                transactionHistory.add("Withdrew: $" + amount); // logs the withdrawal
                return true;
            }
            return false;
        }

        public void addTransfer(String message) {
            transactionHistory.add(message); // logs a transfer
        }

        public void viewTransactionHistory() {
            if (transactionHistory.isEmpty()) {
                System.out.println("No transactions found.");
            } else {
                System.out.println("Transaction History:");
                for (String transaction : transactionHistory) {
                    System.out.println(transaction);
                }
            }
        }

        @Override
        public String toString() {
            return "Username: " + username + ", Balance: $" + balance;
        }
    }

    static ArrayList<account> accounts = new ArrayList<>(); // stores all accounts

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        while (true) {
            displayMainMenu(); // shows the main menu
            try {
                System.out.print("Choice: ");
                int choice = scnr.nextInt();

                if (choice == 1) {
                    handleLogin(scnr); // handles user login
                } else if (choice == 2) {
                    handleAccountCreation(scnr); // handles account creation
                } else if (choice == 3) {
                    handleAdminLogin(scnr); // handles admin login
                } else if (choice == 4) {
                    System.out.println("\nThank you for using our bank system");
                    break; // exits the program
                } else {
                    System.out.println("\nPLEASE ENTER ONE OF THE CHOICES!!! (1,2,3,4)\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nPLEASE ENTER ONE OF THE CHOICES!!!\n");
                scnr.next(); // clears invalid input
            }
        }
    }

    public static void displayMainMenu() {
        // displays the main menu options
        System.out.println("WELCOME TO YOUR BANK SYSTEM");
        System.out.println();
        System.out.println("1. Login");
        System.out.println("2. Create an Account");
        System.out.println("3. Admin Login");
        System.out.println("4. Exit");
    }

    public static void handleLogin(Scanner scnr) {
        // handles user login
        System.out.println("Login: ");
        System.out.print("Enter your username: ");
        String usernameLogin = scnr.next();
        System.out.print("Enter your password: ");
        String passwordLogin = scnr.next();

        account loggedIn = findAccountByUsername(usernameLogin);

        if (loggedIn == null) {
            System.out.println("Invalid username! Returning to main menu.");
            return;
        }

        if (loggedIn.isLocked()) {
            System.out.println("This account is locked due to too many failed login attempts. Please contact an admin to unlock it.");
            return;
        }

        if (!loggedIn.getPassword().equals(passwordLogin)) {
            loggedIn.incrementFailedLoginAttempts();
            int attemptsLeft = 3 - loggedIn.failedLoginAttempts; // calculates remaining attempts
            if (loggedIn.isLocked()) {
                System.out.println("Your account has been locked due to too many failed login attempts.");
            } else {
                System.out.println("Invalid password! You have " + attemptsLeft + " attempt(s) left.");
            }
            return;
        }

        loggedIn.resetFailedLoginAttempts(); // resets failed attempts on successful login
        System.out.println("Login successful! Welcome, " + loggedIn.getUsername());
        handleAccountMenu(scnr, loggedIn); // opens the account menu
    }

    public static void handleAccountCreation(Scanner scnr) {
        // handles account creation
        while (true) {
            System.out.println("Enter new Username: ");
            String username = scnr.next();

            if (findAccountByUsername(username) != null) {
                System.out.println("Username already exists. Please choose a different username.");
                continue;
            }

            System.out.println("Enter new Password: ");
            System.out.println("Password must be at least 10 characters long, have a number, have a letter, and have a special character (.!@#$%^&*?)");
            String password = scnr.next();

            if (isPasswordComplex(password)) {
                System.out.println("Successfully created password!!");
                account acc1 = new account(username, password);
                accounts.add(acc1); // adds the new account to the list
                break;
            } else {
                System.out.println("Password is not complicated enough");
            }
        }
    }

    public static void handleAccountMenu(Scanner scnr, account loggedIn) {
        // handles the account menu for logged-in users
        while (true) {
            System.out.println("\nAccount Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. View Transaction History");
            System.out.println("6. Delete Account");
            System.out.println("7. Logout");
            System.out.print("Choice: ");

            int action = scnr.nextInt();

            if (action == 1) {
                System.out.printf("Balance: $%.2f\n", loggedIn.gatBalance());
            } else if (action == 2) {
                System.out.print("Enter deposit amount: ");
                double amount = scnr.nextDouble();
                loggedIn.deposit(amount); // deposits money
                System.out.println("Deposit successful!");
            } else if (action == 3) {
                System.out.print("Enter withdrawal amount: ");
                double amount = scnr.nextDouble();
                if (loggedIn.withdraw(amount)) { // withdraws money
                    System.out.println("Withdrawal successful!");
                } else {
                    System.out.println("Insufficient balance.");
                }
            } else if (action == 4) {
                System.out.print("Enter recipient username: ");
                String recipientName = scnr.next();
                account recipient = findAccountByUsername(recipientName);

                if (recipient == null || recipient == loggedIn) {
                    System.out.println("Invalid recipient.");
                    continue;
                }

                System.out.print("Enter amount to transfer: ");
                double amount = scnr.nextDouble();
                if (loggedIn.withdraw(amount)) { // transfers money
                    recipient.deposit(amount);
                    loggedIn.addTransfer("Transferred $" + amount + " to " + recipient.getUsername());
                    recipient.addTransfer("Received $" + amount + " from " + loggedIn.getUsername());
                    System.out.println("Transfer successful!");
                } else {
                    System.out.println("Insufficient balance for transfer.");
                }
            } else if (action == 5) {
                loggedIn.viewTransactionHistory(); // views transaction history
            } else if (action == 6) {
                System.out.print("Are you sure you want to delete your account? Type 'yes' to confirm: ");
                String confirmation = scnr.next();
                if (confirmation.equalsIgnoreCase("yes")) {
                    accounts.remove(loggedIn); // deletes the account
                    System.out.println("Your account has been deleted. Logging out...");
                    break;
                } else {
                    System.out.println("Account deletion canceled.");
                }
            } else if (action == 7) {
                System.out.println("Logging out...");
                break;
            } else {
                System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static boolean isPasswordComplex(String password) {
        // checks if the password meets complexity requirements
        boolean digit = false;
        boolean letter = false;
        boolean length = password.length() >= 10;
        boolean spChar = false;
        String specialChars = ".!@#$%^&*?";

        for (int i = 0; i < password.length(); i++) {
            char character = password.charAt(i);
            if (Character.isDigit(character)) {
                digit = true;
            }
            if (Character.isLetter(character)) {
                letter = true;
            }
            if (specialChars.contains(String.valueOf(character))) {
                spChar = true;
            }
        }
        return digit && letter && spChar && length;
    }

    public static account findAccountByUsername(String username) {
        // finds an account by username
        for (account acc : accounts) {
            if (acc.getUsername().equals(username)) {
                return acc;
            }
        }
        return null;
    }

    public static void handleAdminLogin(Scanner scnr) {
        // handles admin login
        System.out.println("Admin Login:");
        System.out.print("Enter admin username: ");
        String adminUsername = scnr.next();
        System.out.print("Enter admin password: ");
        String adminPassword = scnr.next();

        // hardcoded admin credentials
        if (adminUsername.equals("admin") && adminPassword.equals("admin123")) {
            System.out.println("Admin login successful!");
            handleAdminMenu(scnr); // opens the admin menu
        } else {
            System.out.println("Invalid admin credentials! Returning to main menu.");
        }
    }

    public static void handleAdminMenu(Scanner scnr) {
        // handles the admin menu
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View All Accounts");
            System.out.println("2. Unlock an Account");
            System.out.println("3. Delete an Account");
            System.out.println("4. Display Top Balances");
            System.out.println("5. Logout");
            System.out.print("Choice: ");

            int choice = scnr.nextInt();

            if (choice == 1) {
                viewAllAccounts(); // views all accounts
            } else if (choice == 2) {
                unlockAccount(scnr); // unlocks an account
            } else if (choice == 3) {
                deleteAccount(scnr); // deletes an account
            } else if (choice == 4) {
                System.out.print("Enter the number of top accounts to display: ");
                int n = scnr.nextInt();
                displayTopBalances(n); // displays top balances
            } else if (choice == 5) {
                System.out.println("Logging out of admin mode...");
                break;
            } else {
                System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void viewAllAccounts() {
        // displays all accounts
        System.out.println("\nAll Accounts:");
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            for (account acc : accounts) {
                System.out.println(acc);
            }
        }
    }

    public static void unlockAccount(Scanner scnr) {
        // unlocks a locked account
        System.out.print("Enter the username of the account to unlock: ");
        String username = scnr.next();
        account acc = findAccountByUsername(username);

        if (acc == null) {
            System.out.println("Account not found.");
        } else if (!acc.isLocked()) {
            System.out.println("Account is not locked.");
        } else {
            acc.unlockAccount();
            System.out.println("Account unlocked successfully.");
        }
    }

    public static void deleteAccount(Scanner scnr) {
        // deletes an account
        System.out.print("Enter the username of the account to delete: ");
        String username = scnr.next();
        account acc = findAccountByUsername(username);

        if (acc == null) {
            System.out.println("Account not found.");
        } else {
            accounts.remove(acc);
            System.out.println("Account deleted successfully.");
        }
    }

    public static void displayTopBalances(int n) {
        // displays the top n accounts by balance
        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
            return;
        }

        // creates an array of accounts to sort
        account[] accountArray = accounts.toArray(new account[0]);

        // sorts the array by balance in descending order
        java.util.Arrays.sort(accountArray, (a, b) -> Double.compare(b.gatBalance(), a.gatBalance()));

        // displays the top n accounts
        System.out.println("Top " + n + " Accounts by Balance:");
        for (int i = 0; i < Math.min(n, accountArray.length); i++) {
            System.out.println((i + 1) + ". " + accountArray[i].getUsername() + " - $" + accountArray[i].gatBalance());
        }
    }
}