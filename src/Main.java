import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

class Main {

    /**
     * TODO:
     *  - Auto delete account from deletedAccountsFile after an extended period of time?
     *  - (possible) Prevent new accounts from being made that have the same name as one of the recently deleted accounts (?)
     *  - I think everything is done.
     *  - Possibly upload code to CHATGPT to comment on for me (?)
    /**
     *
     * NOT URGENT:
     *  - FIX BUG IN "readSettingsFile" FUNCTION (doesn't cause any harm and the program still runs normally)
     */


    /**
    - ¦<JUST CHANGE THIS VARIABLE TO DIRECT WHERE THE FILE WILL BE STORED>¦
     **/
    private static final File accountsFolder = new File("E:\\Java\\NYTimes Spelling-Bee Hack\\test email sebd\\Files");
    /**- ¦<JUST CHANGE THIS VARIABLE TO DIRECT WHERE THE FILE WILL BE STORED>¦ - */


    /** DO NOT CHANGE THESE |----| DO NOT CHANGE THESE */
    private static final File settings = new File(accountsFolder +  "\\settings.txt");
    private static final File accountsFile = new File(accountsFolder + "\\accounts.dmb");
    private static final File winnersFile = new File(accountsFolder + "\\winners.dmb");
    private static final File deletedAccountsFile = new File(accountsFolder + "\\deletedAccounts.dmb");

    public static File getDeletedAccountsFile() {return deletedAccountsFile;}
    public static File getAccountsFolder() {
        return accountsFolder;
    }

    public static File getSettings() {
        return settings;
    }

    public static File getAccountsFile() {
        return accountsFile;
    }

    public static File getWinnersFile() {
        return winnersFile;
    }

    /** DO NOT CHANGE THESE |----| DO NOT CHANGE THESE */



    public static void log(String data) {
        //This is simply because I can and because I want to because it makes my life ever so slightly easier.
        Date date = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
        String time = "[" + fmt.format(date) + "] ";
        System.out.println(time + data);

    }

    private static String playerOneUsername = null;
    private static String playerTwoUsername = null;
    private static String playerOnePassword = null;
    private static String playerTwoPassword = null;

    public static String getPlayerOneUsername() {
        return playerOneUsername;
    }

    public static void setPlayerOneUsername(String playerOneUsername) {
        Main.playerOneUsername = playerOneUsername;
    }

    public static String getPlayerTwoUsername() {
        return playerTwoUsername;
    }

    public static void setPlayerTwoUsername(String playerTwoUsername) {
        Main.playerTwoUsername = playerTwoUsername;
    }

    public static String getPlayerOnePassword() {
        return playerOnePassword;
    }

    public static void setPlayerOnePassword(String playerOnePassword) {
        Main.playerOnePassword = playerOnePassword;
    }

    public static String getPlayerTwoPassword() {
        return playerTwoPassword;
    }

    public static void setPlayerTwoPassword(String playerTwoPassword) {
        Main.playerTwoPassword = playerTwoPassword;
    }

    public static void main(String[] args) {

        /**
         *
        * "words.fixFile();" use this function to see outcome, the old word list will be created at the start of the function and the new list will be created at the end. Two separate files to see.
         * OR  enter the words fix file into the first user log in section.
         */
        try{
            //check file.
            dataHandling.checkFile();
            /**
             * In theory the usernames and passwords should be in the "same" space within both ArrayLists,
             * For example:
             *  When the user enters a username, if it exists, the place of the username will be located so when the user enters their password, we can check the same place
             *  in the passwords ArrayList to see if it matches.
             */

            /*
            The following code checks for any args, this is used to see if the welcome message needs to be shown again.
            So if there is no args, we can safely assume the "main" class is being run for the first time.
            Therefore, if it contains args, it is being re-initiated from within the program, and we can use the args to decipher what we are doing.

            If the arg[0] is:
                - 0: Means the user failed to type 1, 2 or 3.
                - 1: Means player one failed to log-in correctly - this initiate a skip of the selection menu.
                - 2: Means player one logged in successfully, but player two didn't - This will initiate a skip of the menu and player one log-in.
             */
            boolean skipPlayerOneLoginMenu = false;
            boolean skipMenu = false;
            if(args.length!=0){
                switch (args[0]){
                    case "1" -> skipMenu = true;
                    case "2" -> {
                        skipMenu = true;
                        skipPlayerOneLoginMenu = true;
                    }
                }
            }else{
                showTitleMessage();
                showMenu();
            }


            //User select option. GOTO "showTitleMessage" for info.
            Scanner inp = new Scanner(System.in);
            String userChoice;
            //check if skipmenu is false, if it is then we need to show this part, if not the "userChoice" is forced to "1" to go straight to the log-in again.
            if(!skipMenu){
                Thread.sleep(200);
                log("Type 1, 2, 3, or 4 to select your option: ");
                userChoice = inp.nextLine();
            }else{
                userChoice = "1";
            }
            try{

                switch (userChoice) {
                    case "1" -> {
                        /*This is the login section for player one and two.*/
                        boolean successPlayerOne = skipPlayerOneLoginMenu;

                        if(!skipMenu) log("Player One Log-in");
                        if(!skipPlayerOneLoginMenu){
                            Thread.sleep(250);

                            log("Enter username: ");
                            setPlayerOneUsername(inp.nextLine());
                            if(getPlayerOneUsername().equalsIgnoreCase("create account")){
                                accountHandling.createAccount(0);
                                break;
                            }else if(getPlayerOneUsername().equalsIgnoreCase("fix file") || getPlayerOneUsername().equalsIgnoreCase("fix files")){
                                words.fixFile();
                                break;
                            }
                            Thread.sleep(250);
                            log("Enter password: ");
                            setPlayerOnePassword(inp.nextLine());

                            successPlayerOne = accountHandling.checkDetails(getPlayerOneUsername(), getPlayerOnePassword());
                        }
                        //Check for username and then password.
                        /*
                        Idea is that if the username exists, then get the "location" of it and use that integer in the passwords arraylist, hence we can then check if the passwords correspond.
                         */

                        if(!successPlayerOne){
                            Thread.sleep(250);
                            log("Incorrect Username and or Password!");
                            main(new String[] {"1"});
                        }else{
                            //Player two log-in section.

                            if(!skipPlayerOneLoginMenu) {Thread.sleep(250); log("Player Two Log-in");}
                            Thread.sleep(250);
                            log("Enter username: ");
                            setPlayerTwoUsername(inp.nextLine());
                            if(getPlayerTwoUsername().equalsIgnoreCase("create account")){
                                Thread.sleep(250);
                                accountHandling.createAccount(1);
                            }else{
                                Thread.sleep(250);
                                log("Enter Password: ");
                                setPlayerTwoPassword(inp.nextLine());


                            //handles player account creation.
                                if(getPlayerTwoUsername().equals(getPlayerOneUsername())){
                                    Thread.sleep(250);
                                    log("Player One has already logged in using this user! Please choose a different account.");
                                    Thread.sleep(500);
                                    log("If you need to create an account, please type: \"Create Account\" into the \"Username\" field.");
                                    main(new String[] {"2"});
                                }else{
                                    boolean successPlayerTwo = accountHandling.checkDetails(getPlayerTwoUsername(), getPlayerTwoPassword());
                                    if(!successPlayerTwo){
                                        Thread.sleep(250);
                                        log("Incorrect Username and or Password.");
                                        Thread.sleep(150);
                                        log("If you need to create an account, please type: \"Create Account\" into the \"Username\" field.");
                                        main(new String[] {"2"});
                                    }else{
                                        //"doGame" return winner and score.
                                        String[] winner = gameHandling.doGame(getPlayerOneUsername(), getPlayerTwoUsername());
                                        afterGameHandling.sortFile(winner[0], winner[1]);
                                    }
                                }
                            }

                        }
                        break;
                    }
                    case "2" -> {
                        accountHandling.createAccount(0);
                        break;
                    }
                    case "3" -> {
                        accountHandling.deleteAccount();
                        break;
                    }
                    case "4" ->{
                        accountHandling.forgottenAccountDetails();
                    }


                    default -> {
                        Thread.sleep(250);
                        log("Invalid, please pick from one of the options!");
                        main(new String[] {"0"});
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }
    //this is disgusting code don't look at it.
    public static void showTitleMessage(){ //this is disgusting don't look at this.
        try{
            String bars = "------------------------";
            log("┎" + bars + "┐");
            log("|       Welcome!         |");
            log("┣------------------------┫");
            log("|     NEA  2022/23       |");
            log("┖" + bars + "┘");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void showMenu(){
        /*
         * Menu
         * Will include the following:
         * User log-in system, p1 p2.
         * Create Account
         * Delete Account
         * Get account details
         */
        try{
            Thread.sleep(200);
            log("|1| Login");
            Thread.sleep(200);
            log("|2| Create Account");
            Thread.sleep(200);
            log("|3| Delete Account");
            Thread.sleep(200);
            log("|4| Forgotten Account Details");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

