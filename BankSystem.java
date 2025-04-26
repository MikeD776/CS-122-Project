import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankSystem {

    public static class account{
        private String username;
        private String password;
        private double balance;
        
        public account(String username, String password){
            this.username = username;
            this.password = password;
            this.balance = 0.0;
        }
        
        public String getUsername(){
            return username;
        }
        public String getPassword(){
            return password;
        }
        public double gatBalance(){
            return balance;
        }

        public void deposit(double amount){
            if(amount> 0){
                balance +=amount;
            }
        }
        public boolean withdraw(double amount){
            if(amount > 0 && amount<= balance){
                balance-= amount;
                return true;
            }
            return false;
        }
        public boolean transfer(double amount){
           return false; 
        }

        @Override
        public String toString(){
            return "Username: " + username + ", Balance: $ " + balance + password;
        }
    }
   
    static ArrayList<account> accounts = new ArrayList<>();

    public static void main(String[] args) {
        while(true){
            System.out.println("WELCOME TO YOUR BANK SYSTEM");
            System.out.println();
            System.out.println("For login type 1");
            System.out.println("For creating an account type 2");
            System.out.println("For closing the system type 3");
            try{
                Scanner scnr = new Scanner(System.in);
                System.out.print("Choice: ");
                int choice = scnr.nextInt();
            
                if(choice == 1){
                    //Login logic
                    //Check if their is an existing user
                    //In here we are going to have the bank system
                    //We nee in the while loop logic for money withdraws, deposits, and transactions between accounts
                    //For those we need to check if they have enough money and if there are other accounts(for the last one)
                    //Add other things if you feel like is a good idea
                    while(true){
                        System.out.println("Login: ");
                        System.out.print("Enter your username: ");
                        String usernameLogin = scnr.next();
                        System.out.print("Enter your password: ");
                        String passwordLogin = scnr.next();
                    }
                }
                if(choice == 2){
                    while(true){
                        System.out.println("Enter new Username: ");
                        String username = scnr.next();
                        System.out.println("Enter new Password: ");
                        System.out.println("Password must be at least 10 characters long, have a number, have a letter, and have a special character (.!@#$%^&*?)");
                        String password = scnr.next();

                        Boolean digit = false;
                        Boolean letter = false;
                        Boolean length = false;
                        Boolean spChar = false;
                        String specialChars = ".!@#$%^&*?";
                        
                        for(int i = 0; i < password.length(); i++){
                            char character = password.charAt(i);
                            if (Character.isDigit(character)){
                                digit = true;
                            }
                            if(Character.isLetter(character)){
                                letter = true;
                            }
                            if(specialChars.contains(String.valueOf(character))){
                                spChar = true;
                            }
                            if(password.length() >= 10){
                                length = true;
                            }
                        }
                        if(digit && letter && spChar && length){
                            System.out.println("Succesfully created password!!");
                            account acc1 = new account(username, password);
                            accounts.add(acc1);
                            break;
                        }
                        else{
                            System.out.println("Password is not complicated enough");
                        }
                    }
                    continue;
                }
                if(choice ==3){
                    System.out.println("\nThank you for using our bank system");
                    break;
                }
                else{
                    System.out.println("\nPLEASE ENTER ONE OF THE CHOICES!!! (1,2,3)\n");
                }
            }
            catch(InputMismatchException e){
                System.out.println("\nPLEASE ENTER ONE OF THE CHOICES!!!\n");
            }
        }

    }
}
