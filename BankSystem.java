import java.util.InputMismatchException;
import java.util.Scanner;

public class BankSystem {

    public class account{
        public String username;
        public String password;
        
        public account(String username, String password){
            this.username = username;
            this.password = password;
        }

    }
    
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
                        System.out.print("Enter username: ");
                        String usernameLogin = scnr.next();
                        System.out.print("Enter your password:");
                        String passwordLogin = scnr.next();
                    }
                }
                if(choice == 2){
                    //In here we only need to create an account that needs username and password(maybe we can add other things like email or something like that)
                    //We should create a class for this like we been doing
                    //I was thinking of a type of validation of passwords(the user needs a strong password that have at least 10 characters, a number and a special character)
                    // I remember we have already done this so we can check previous assignments for it
                    // When the user is created it should break and return to the first page so it can login
                    while(true){
                        System.out.print("Enter new Username: ");
                        String username = scnr.next();
                        System.out.print("Enter new Password: ");
                        String password = scnr.next();
                        break;
                    }
                }
                if(choice ==3){
                    System.out.println("Thank you for using our bank system");
                    break;
                }
                else{
                    System.out.println("PLEASE ENTER ONE OF THE CHOICES!!!\n");
                }
            }
            catch(InputMismatchException e){
                System.out.println("PLEASE ENTER ONE OF THE CHOICES!!!\n");
            }
        }

    }
}
