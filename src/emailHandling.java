import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class emailHandling{
    public static void log(Object data){
        Main.log((String) data);
    }

    public static void forgotUsername(){
        try{
            // Create a Scanner object to read input from the user
            Scanner inp = new Scanner(System.in);

            // Prompt the user to enter their email
            log("Please enter your email associated with the account: ");
            String email = inp.nextLine();

            // Print a message indicating that the account is being checked for
            log("Checking for account...");
            Thread.sleep(200);

            // Create a list to store the usernames found for the email
            ArrayList<String> foundUsernames = new ArrayList<>();

            // Initialize a variable to keep track of the number of accounts found for the email
            int multipleAccountsCheck = 0;

            // Initialize a boolean flag to indicate if an account was found for the email
            boolean found = false;

            // Loop through the list of emails in the accountHandling class
            for (int i = 0; i < accountHandling.getEmails().size(); i++) {
                // Check if the email entered by the user matches the ones in the list
                if(email.equals(accountHandling.getEmails().get(i))){
                    // If a match is found, add the corresponding username to the list of found usernames
                    foundUsernames.add(accountHandling.getUsernamesOfFile().get(i));
                    // Increment the multipleAccountsCheck variable
                    multipleAccountsCheck++;
                    // Set the found flag to true
                    found = true;
                }
            }

            // If no accounts were found for the email
            if (!found) {
                // Print a message indicating that no accounts were found
                log("There are no accounts linked to this email!");
                Thread.sleep(200);
                // Print a message asking the user if they spelled their email correctly
                log("Did you spell it correctly?\n");
                forgotUsername();
            }else{
                // If at least one account was found
                // Print a message indicating that an account was found for the email
                log("Found account linked to: " + email);
                // If multiple accounts were found, print a message indicating this
                if(multipleAccountsCheck > 1){log("We found multiple accounts under the same email! Hmm weird, sending both... (multiple emails)");}
                // Loop through the list of found usernames
                for (String foundUsername : foundUsernames) {
                    // Send an email for each username using the sendEmail method from the emailHandling class
                    // The email subject is "username"
                    Mailer.send(email, foundUsername, "FORGOT_USERNAME");
                }
                // Print a message indicating that the usernames were sent to the email
                Thread.sleep(150);
                log("Sent usernames to email: " + email + "\n");
                Thread.sleep(150);
                // Call the main method of the Main class
                Main.showMenu();
                Main.main(new String[] {""});
            }

        }catch (Exception e){
            // If an exception is thrown, print the stack trace
            e.printStackTrace();
        }
    }


    public static void forgotPassword(){
        try{
            // Create a Scanner object to read input from the user
            Scanner inp = new Scanner(System.in);

            // Declare variables
            String username, userInputCode, generatedCode, userEmail;

            // Generate a code using the generateCode method from the dataHandling class
            generatedCode = dataHandling.generateCode();

            // Prompt the user to enter their username
            log("Enter username of account: ");
            username = inp.nextLine();

            // Prompt the user to enter their email
            log("Enter email of account: ");
            userEmail = inp.nextLine();

            // Print a message indicating that the account is being located
            log("Looking for account");
            Thread.sleep(150);

            // Loop through the list of usernames and emails in the accountHandling class
            for(int i = 0; i<accountHandling.getUsernamesOfFile().size(); i++){
                // Check if the username and email entered by the user match the ones in the list
                if(accountHandling.getUsernamesOfFile().get(i).equals(username)){
                    if(accountHandling.getEmails().get(i).equals(userEmail)){
                        // If a match is found, print a message indicating that the account has been located
                        log("Located Account");
                        // Send an email using the sendEmail method from the Mailer class
                        // The email contains the generated code and the subject is "password"
                        Mailer.send(userEmail, generatedCode, "FORGOT_PASSWORD_CODE");
                        // Prompt the user to enter the code sent to their inbox
                        log("Please enter code sent to your inbox: ");
                        userInputCode = inp.nextLine();

                        // Check if the code entered by the user matches the generated code
                        if(userInputCode.equals(generatedCode)){
                            // If the code is correct, print a message indicating this
                            log("Correct code.");
                            // Send an email using the sendEmail method from the Mailer class
                            // The email contains the user's password and the subject is "password"
                            Mailer.send(userEmail, accountHandling.getPasswordsFromList().get(i),  "FORGOT_PASSWORD_NORMAL");
                            // Print a message indicating that the password has been sent to the user's inbox
                            log("We have sent your password to your inbox.");
                            //
                            Main.showMenu();
                            Main.main(new String[] {"0"});

                            // Exit the program
                            System.exit(0);
                        }
                    }
                }
            }
            // If the loop completes without finding a match, print a message indicating that the account does not exist
            log("Your account doesn't seem to exist!");
        }catch (Exception e){
            // If an exception is thrown, print the stack trace
            e.printStackTrace();
        }

    }
}
