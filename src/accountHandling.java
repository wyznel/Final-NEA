import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class accountHandling {
    private static final ArrayList<String> dataReadFromFile = dataHandling.readAccountsFile();
    private static ArrayList<String> usernames = getUsernames(dataReadFromFile);
    private static final ArrayList<String> emails = getEmails(dataReadFromFile);
    private static final ArrayList<String> passwords = getPasswords(dataReadFromFile);

    public static ArrayList<String> getPasswordsFromList(){
        return passwords;
    }
    public static ArrayList<String> getUsernamesOfFile() {
        return usernames;
    }

    public static ArrayList<String> getEmails() {
        return emails;
    }

    private static boolean taken = false;

    public static boolean isTaken() {
        return taken;
    }

    public static void setTaken(boolean taken) {
        accountHandling.taken = taken;
    }
    public static boolean getTaken(){return accountHandling.taken;}
    public static void log(String data) {
        Main.log(data);
    }


    public static void createAccount(int fromWho) {
        Scanner inp = new Scanner(System.in);
        //All we need to get is the usernames.
        usernames = getUsernames(dataHandling.readAccountsFile());

        //We need to check if the account already exists within the file, as to make sure each account is unique.
        try{

            String newUsername, newPassword, newEmail;

            log("Account Creation: ");
            Thread.sleep(200);
            log("Enter new Username: ");
            newUsername = inp.nextLine();
            Thread.sleep(150);
            log("Enter new Password: ");
            newPassword = inp.nextLine();
            log("Enter email: ");
            newEmail = inp.nextLine();



            for (String name : getUsernamesOfFile()) {
                if (name.equals(newUsername)) {
                    setTaken(true);
                    Thread.sleep(150);
                    log("This username has already been taken! Please try again.");
                    accountHandling.suggestUsername(newUsername);
                    break;
                }
            }
            if(!getTaken()){
                for(String em : getEmails()){
                    if(em.equals(newEmail)){
                        log("This email is already in use!");
                        Thread.sleep(150);
                        log("If you want to proceed, please type \"PROCEED\", else please type: \"CANCEL\": ");
                        if(inp.nextLine().equalsIgnoreCase("PROCEED")){
                            break;
                        }else{
                            log("Cancelling...\n");
                            Thread.sleep(150);
                            Main.showMenu();
                            Main.main(new String[] {"0"});
                            System.exit(0);
                        }
                    }
                }
                //if not taken, send confirmation email containing a unique code.
                // then  write username and password to file and send user back to the 2nd account log in.

                //Email Confirmation
                String code = dataHandling.generateCode();
                Mailer.send(newEmail, code, "CREATE");
                Thread.sleep(200);

                for (int i = 0; i < 3; i++) {
                    log("Enter the code sent to your email (" + newEmail + "): ");
                    String userInput = inp.nextLine();
                    if (userInput.equals(code)) {
                        log("Success");
                        dataHandling.writeToFile(newUsername, newPassword, newEmail, Main.getAccountsFile());
                        if (fromWho == 1) {
                            log("Created new account: " + newUsername);
                            Main.main(new String[]{"2"});
                        } else {
                            log("Created new account: " + newUsername + "\n");
                            Mailer.send(newEmail, newUsername, "CREATE_SUCCESS");
                        }
                        break;
                    } else {
                        log("Incorrect code, try again");
                    }
                }
            } else{
                setTaken(false);
                createAccount(fromWho);
                Thread.sleep(150);
                Main.showMenu();
                Main.main(new String[] {"0"});
                System.exit(0);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //settings from the settings file.
    public static ArrayList<String> values = dataHandling.readSettingsFile();
    public static int numberOfAccounts() throws InterruptedException {
        int numOfAccounts = 4;

        try{
            numOfAccounts = Integer.parseInt(values.get(0));
        }catch (Exception e){
            Thread.sleep(150);
            log("You entered: \"" + values.get(0) + "\" which isn't a valid number! 4 accounts will still be generated however, as this is default.");
        }
        return numOfAccounts;
    }
    public static int numbersOnTheEnd = Integer.parseInt(values.get(1));
    public static boolean addNumbers = Boolean.parseBoolean(values.get(2));
    public static boolean addWords = Boolean.parseBoolean(values.get(3));


    public static void suggestUsername(String username){
        int amountOfAccounts = 4;
        try{
            amountOfAccounts = numberOfAccounts();
        }catch (Exception e){
            e.printStackTrace();
        }
        log("Thinking of usernames...\n");
        try{
            //this will add numbers and possibly letters onto the username given so the user can have as close as possible to their desired username.
            Random generate = new Random();
            //creates a 2D array based on how many accounts the user wants generated and how many numbers they want on the end.
            int[][] numbers = new int[amountOfAccounts][numbersOnTheEnd];
            ArrayList<String> generatedNames = new ArrayList<>();
            ArrayList<String> tempNames = new ArrayList<>();

            StringBuilder suggestedName = new StringBuilder(username);
            if(!addNumbers && !addWords){ //if both of the settings that decide if the user wants numbers or words added to their username is false, this will tell them that this is preventing the name generation.
                log("To generate new usernames, we need to be allowed to add words and/or numbers to your username! Your current settings tell us we can't do this!");
                Thread.sleep(150);
                log("Head to the \"settings\" file located: " + Main.getSettings().getPath());
                Thread.sleep(150);
                log("To change them!");
            }else{

                if(addNumbers){
                    /*
                       Fills the 2D array with randomly generated numbers 0 through 9.
                     */
                    for (int i = 0; i < amountOfAccounts; i++) {
                        for (int j = 0; j < numbersOnTheEnd; j++) {
                            numbers[i][j] = generate.nextInt(9);
                        }
                    }

                    /*
                    Loops through each column of the 2D array, getting each number.
                    Then it adds those numbers to the name and once finished with the column it'll move through the next for the next username.
                     */
                    for (int i = 0; i < amountOfAccounts; i++) {
                        for (int j = 0; j < numbersOnTheEnd; j++) {
                            suggestedName.append(numbers[i][j]);
                            if(j == numbersOnTheEnd-1){
                                if(addWords){
                                    //this is because the numbers are created then added first, so we don't push them to the final array list until the words have been added.
                                    tempNames.add(suggestedName.toString());
                                }else{
                                    generatedNames.add(suggestedName.toString());
                                }
                                suggestedName = new StringBuilder(username);
                            }
                        }
                    }
                }

                if(addWords){
                    ArrayList<String> wordList = words.wordList(); //gets the word list from "words.java"
                    //for each account wanted, get the desired username, add a word before it and add it to the generated usernames array list.
                    for (int i = 0; i < amountOfAccounts; i++) {
                        /*
                        In "words.java", the "wordList" function, the list is programmed to have the amount of words as the last position in the list.
                        This is so we can use every word possible that has been provided.
                         */
                        int chooseWord = generate.nextInt(wordList.size());

                        String wordToAdd = wordList.get(chooseWord);

                        String newName;
                        if(addNumbers){
                            newName = wordToAdd + tempNames.get(i);
                        }else{
                            newName = wordToAdd + username;
                        }
                        generatedNames.add(newName);
                    }
                }
                //for each name in the generatedNames array list, show it to the console with a bit of delay.
                for(String name : generatedNames){
                    Thread.sleep(150);
                    log("Generated: " + name);
                }
            }

            System.out.println();
            if(addWords && addNumbers) log("To change the settings for the account generation, please head to: " + Main.getSettings().getPath());

        }catch (Exception e ){
            e.printStackTrace();
        }
    }
    public static ArrayList<String> getUsernames(ArrayList<String> data) {
        ArrayList<String> usernames = new ArrayList<>();

        try{

            for (String i : data) {
                String[] moreData = i.split(":");
                usernames.add(moreData[0]);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return usernames;
    }

    public static ArrayList<String> getPasswords(ArrayList<String> data) {
        ArrayList<String> passwords = new ArrayList<>();

        try{
            for (String i : data) {
                String[] moreData = i.split(":");
                passwords.add(moreData[1]);

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return passwords;
    }

    public static ArrayList<String> getEmails(ArrayList<String> data){
        ArrayList<String> emails = new ArrayList<>();
        try{
            for(String i : data){
                String[] moreData = i.split(":");
                emails.add(moreData[2]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return emails;

    }

    public static boolean checkDetails(String username, String password){
        boolean success = false;

        //Whilst I could make the "usernames" and "passwords" a public variable, it would introduce the issue of the checkFiles not being first, throwing an error if the file doesn't exist as it is trying to read from nothing.
        try {
            ArrayList<String> fileData = dataHandling.readAccountsFile();

            ArrayList<String> usernames = accountHandling.getUsernames(fileData);
            ArrayList<String> passwords = accountHandling.getPasswords(fileData);

            for (String i : usernames) {
                if (i.equals(username)) {
                    for (String a : passwords) {
                        if (a.equals(password)) {
                            Thread.sleep(200);
                            log("Successful log-in.");
                            success = true;
                            break;
                        }
                    }
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return success;
    }

    public static void forgottenAccountDetails() {
        //if the user has forgotten their account details, they will be redirected here.
        try{
            Scanner inp = new Scanner(System.in);
            String choice;
            log("|1| Forgotten Username");
            Thread.sleep(200);
            log("|2| Forgotten Password");
            Thread.sleep(200);
            log("Please type either '1' or '2' to select a choice: ");
            choice = inp.nextLine();
            switch(choice){
                case "1" ->{
                    emailHandling.forgotUsername();
                    break;
                }
                case "2" -> {
                    emailHandling.forgotPassword();
                    break;
                }
                default -> {
                    log("Invalid input");
                    forgottenAccountDetails();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteAccount(){
        Scanner inp = new Scanner(System.in);

        try{
            ArrayList<String> accounts = dataHandling.readAccountsFile(), updatedUsernameList = getUsernames(accounts), updatedPasswordsList = getPasswords(accounts), updatedEmailsList = getEmails(accounts);

            ArrayList<String> deletedAccountsCodes = dataHandling.getDeletedAccountsCodes(dataHandling.readDeletedAccountsFile());
            String usernameToDelete, passwordToDelete, confirmationPassword, emailToDelete;

            log("Account deletion -\n ");
            Thread.sleep(100);
            log("To exit this process please type \"CANCEL DELETION\" into the 'Username' field.");

            Thread.sleep(100);
            log("Enter username of account to delete: ");
            usernameToDelete = inp.nextLine();
            for(String code : deletedAccountsCodes){
                if(code.equalsIgnoreCase(usernameToDelete)){
                    accountHandling.recoverAccountProcess(code);
                    System.exit(0);
                }
            }
            if(usernameToDelete.equals("ADMIN")){
                log("Unable to delete this account. Stopping...");
                System.exit(0);
            }
            else if(usernameToDelete.equalsIgnoreCase("cancel deletion")){
                Main.main(new String[] {"0"});
            }else{
                Thread.sleep(100);

                //Ask for password
                log("Enter password: ");
                passwordToDelete = inp.nextLine();
                Thread.sleep(100);

                //Password confirmation
                log("Re-enter password to confirm account deletion: ");
                confirmationPassword = inp.nextLine();

                //Ask for email.
                log("Enter email of the account you want to delete: ");
                emailToDelete = inp.nextLine();
                //send a 4-digit code to inbox.
                String code = dataHandling.generateCode();
                Mailer.send(emailToDelete, code, "DELETE_APPROVAL");
                //Email confirmation
                log("Enter code sent to your inbox: ");

                String removedUsername = null, removedPassword = null, removedEmail = null;


                if(inp.nextLine().equals(code)){ //if correct code proceed.
                    if(passwordToDelete.equals(confirmationPassword)){
                        boolean success = checkDetails(usernameToDelete, passwordToDelete);
                        boolean deletedAccount = false;
                        if(success){
                            for (int i = 0; i <updatedUsernameList.size(); i++) {
                                if(updatedUsernameList.get(i).equals(usernameToDelete)){
                                    if(updatedPasswordsList.get(i).equals(passwordToDelete)){
                                        if(updatedEmailsList.get(i).equals(emailToDelete)){
                                            removedUsername = updatedUsernameList.get(i);
                                            removedPassword = updatedPasswordsList.get(i);
                                            removedEmail = updatedEmailsList.get(i);

                                            updatedUsernameList.remove(removedUsername);
                                            updatedPasswordsList.remove(removedPassword);
                                            updatedEmailsList.remove(removedEmail);
                                            deletedAccount = true;
                                        }

                                    }
                                }
                            }
                            if(deletedAccount){
                                //remove from file.
                                log("Clearing file...");
                                BufferedWriter writer = new BufferedWriter(new FileWriter(Main.getAccountsFile(), false));
                                writer.write("");
                                writer.close();
                                log("Cleared.");
                                Thread.sleep(100);
                                //Rewrite file.
                                log("Re-writing file.");
                                dataHandling.writeDefault(0);
                                for (int i = 0; i < updatedUsernameList.size(); i++) {
                                    if(i!=0){
                                        dataHandling.writeToFile(updatedUsernameList.get(i), updatedPasswordsList.get(i), updatedEmailsList.get(i), Main.getAccountsFile());
                                    }
                                }
                                log("Successfully deleted account: \"" + usernameToDelete + "\"");

                                //Add account to deleted accounts file.
                                Mailer.deleteAccountCode = dataHandling.generateCode();
                                dataHandling.writeToFile(removedUsername, removedPassword, removedEmail, Main.getDeletedAccountsFile());
                                Mailer.send(emailToDelete, usernameToDelete, "DELETE_SUCCESS");
                            }
                        }else{
                            log("Invalid Username and or Password");
                            Main.showMenu();
                            Main.main(new String[] {"0"});
                        }
                    }else{
                        log("Failed. Wrong password.");
                    }
                }else{
                    log("Invalid code. Stopping...");
                    System.exit(0);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void recoverAccountProcess(String userEnteredCode){
        try{
            Scanner inp = new Scanner(System.in);

            ArrayList<String> deletedAccountsList = dataHandling.readDeletedAccountsFile();
            ArrayList<String> deletedAccountCodes = dataHandling.getDeletedAccountsCodes(deletedAccountsList);
            ArrayList<String> deletedAccountUsernames = dataHandling.getDeletedAccountsUsernames(deletedAccountsList);
            ArrayList<String> deletedAccountEmails = dataHandling.getDeletedAccountsEmail(deletedAccountsList);
            ArrayList<String> deletedAccountPasswords = dataHandling.getDeletedAccountsPasswords(deletedAccountsList);

            int placeInLists = 0;
            for (int i = 0; i < deletedAccountCodes.size(); i++) {
                if(deletedAccountCodes.get(i).equalsIgnoreCase(userEnteredCode)){
                    log("To confirm your account is (Y/N): " + deletedAccountUsernames.get(i));
                    String conf = inp.nextLine();
                    if(conf.equalsIgnoreCase("y") || conf.equalsIgnoreCase("yes")){
                        log("Recovering...");
                        placeInLists = i;
                        break;
                    }else{
                        log("Exiting...");
                        System.exit(0);
                    }
                }
            }
            log("Please enter password of account you want to recover: ");
            String recoveryPassword = inp.nextLine();
            if(recoveryPassword.equalsIgnoreCase(deletedAccountPasswords.get(placeInLists))){
                String generatedCode = dataHandling.generateCode();
                Mailer.send(deletedAccountEmails.get(placeInLists), generatedCode, "ACCOUNT_RECOVERY_CODE");
                log("Check the inbox of your email associate with this account and enter the 4 digit code: ");
                if(inp.nextLine().equalsIgnoreCase(generatedCode)){
                    dataHandling.writeToFile(deletedAccountUsernames.get(placeInLists), recoveryPassword, deletedAccountEmails.get(placeInLists), dataHandling.accountsFile);
                    Mailer.send(deletedAccountEmails.get(placeInLists), deletedAccountUsernames.get(placeInLists), "ACCOUNT_RECOVERY_SUCCESS");
                    log("Sent email regarding: ACCOUNT RECOVERY SUCCESS");
                    log("Welcome back " + deletedAccountUsernames.get(placeInLists) + "!");


                    //rewrite deleted accounts file.
                    deletedAccountPasswords.remove(placeInLists);
                    deletedAccountEmails.remove(placeInLists);
                    deletedAccountUsernames.remove(placeInLists);
                    deletedAccountCodes.remove(placeInLists);
                    BufferedWriter writer = new BufferedWriter(new FileWriter(dataHandling.deletedAccountsFile));
                    writer.write("");


                    for (int i = 0; i < deletedAccountCodes.size(); i++) {
                        writer.write(deletedAccountUsernames.get(i) + ":" + deletedAccountPasswords.get(i) + ":" + deletedAccountEmails.get(i) + ":" + deletedAccountCodes.get(i) + "\n");
                    }
                    writer.close();
                    log("Done.");
                    System.exit(0);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}