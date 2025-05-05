import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankSystem {

    public static class account {
        private String username;
        private String password;
        private double balance;

        public account(String username, String password) {
            this.username = username;
            this.password = password;
            this.balance = 0.0;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public double gatBalance() {
            return balance;
        }

        public void deposit(double amount) {
            if (amount > 0) {
                balance += amount;
            }
        }

        public boolean withdraw(double amount) {
            if (amount > 0 && amount <= balance) {
                balance -= amount;
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return "Username: " + username + ", Balance: $" + balance;
        }
    }

    static ArrayList<account> accounts = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        while (true) {
            displayMainMenu();
            try {
                System.out.print("Choice: ");
                int choice = scnr.nextInt();

                if (choice == 1) {
                    handleLogin(scnr);
                } else if (choice == 2) {
                    handleAccountCreation(scnr);
                } else if (choice == 3) {
                    System.out.println("\nThank you for using our bank system");
                    break;
                } else {
                    System.out.println("\nPLEASE ENTER ONE OF THE CHOICES!!! (1,2,3)\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nPLEASE ENTER ONE OF THE CHOICES!!!\n");
                scnr.next(); 
            }
        }
    }

    
    public static void displayMainMenu() {
        System.out.println("WELCOME TO YOUR BANK SYSTEM");
        System.out.println();
        System.out.println("For login type 1");
        System.out.println("For creating an account type 2");
        System.out.println("For closing the system type 3");
    }

    
    public static void handleLogin(Scanner scnr) {
        System.out.println("Login: ");
        System.out.print("Enter your username: ");
        String usernameLogin = scnr.next();
        System.out.print("Enter your password: ");
        String passwordLogin = scnr.next();

        account loggedIn = findAccountByUsername(usernameLogin);

        if (loggedIn == null || !loggedIn.getPassword().equals(passwordLogin)) {
            System.out.println("Invalid username or password! Returning to main menu.");
            return;
        }

        System.out.println("Login successful! Welcome, " + loggedIn.getUsername());
        handleAccountMenu(scnr, loggedIn);
    }

    
    public static void handleAccountCreation(Scanner scnr) {
        while (true) {
            System.out.println("Enter new Username: ");
            String username = scnr.next();
            System.out.println("Enter new Password: ");
            System.out.println("Password must be at least 10 characters long, have a number, have a letter, and have a special character (.!@#$%^&*?)");
            String password = scnr.next();

            if (isPasswordComplex(password)) {
                System.out.println("Successfully created password!!");
                account acc1 = new account(username, password);
                accounts.add(acc1);
                break;
            } else {
                System.out.println("Password is not complicated enough");
            }
        }
    }

    
    public static void handleAccountMenu(Scanner scnr, account loggedIn) {
        while (true) {
            System.out.println("\nAccount Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Logout");
            System.out.print("Choice: ");

            int action = scnr.nextInt();

            if (action == 1) {
                System.out.printf("Balance: $%.2f\n", loggedIn.gatBalance());
            } else if (action == 2) {
                System.out.print("Enter deposit amount: ");
                double amount = scnr.nextDouble();
                loggedIn.deposit(amount);
                System.out.println("Deposit successful!");
            } else if (action == 3) {
                System.out.print("Enter withdrawal amount: ");
                double amount = scnr.nextDouble();
                if (loggedIn.withdraw(amount)) {
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
                if (loggedIn.withdraw(amount)) {
                    recipient.deposit(amount);
                    System.out.println("Transfer successful!");
                } else {
                    System.out.println("Insufficient balance for transfer.");
                }
            } else if (action == 5) {
                System.out.println("Logging out...");
                break;
            } else {
                System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static boolean isPasswordComplex(String password) {
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
        for (account acc : accounts) {
            if (acc.getUsername().equals(username)) {
                return acc;
            }
        }
        return null;
    }
}