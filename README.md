# *DICE GAME - NEA 2022/23*
This is is the final version of the project.
This file is a "better" looking version of the file called "the proper analysis.docx".

------------
## Analysis
I am being asked to create a game involving random numbers, managing and authorising user data. There will be a separate text file which will hold the contents of the authorised users. The program will also be able to create new accounts, delete accounts and resetting passwords for accounts. There will be a maximum of 100 accounts as to avoid a large file size.

The first thing my program will do will run validation checks; these checks will include checking if the text file exists, if it contains the default account and password. It’ll then store the usernames, passwords, codes, and keys into separate array lists, but in the same position in their respective array list as so they can be validated correctly.  This process is estimated to take a few milliseconds as to increase efficiency and user friendliness.

The first thing the user will see is a list asking them what they want to do; the options will be as mentioned: "Create Account", "Log-in", "Delete Account" or "Forgotten Account Details". To select one of the options they will type a number 1 through 4 corresponding to an item on the list, if they mistype the program will state: “invalid option, please choose a number from 1-4”.

If they select "Create Account", they will be prompted to enter a new username, a new password, password confirmation, and to enter a new email. They will then be sent an email with a 4 digit code, and will be prompted to enter the code into the console to confirm the email. Once this is done, it is added to the "usernames", "passwords", and "emails" array list for future use. Before the program ends, the "accounts.txt" file is re-written to include any changes. If their username is already taken, the user will be shown a list of suggested usernames. They will also be shown how to change the settings to how they like, these settings include: how many names to generate, how many numbers to add onto the end, if they want numbers or words. If they choose settings that completely disable the suggest usernames feature, they will be told how to change the settings.

If they select "Delete Account", they will be told the warnings, and how to exit out of the sequence. If they choose to carry on, they will be prompted to enter their username, their password twice, and the code sent to their inbox. As long as the account exists and the info provided is correct, the account will be removed and the program will end as to re-write the file. If they fail to confirm the deletion process, failure being ignited if the code entered is wrong, the password is incorrect, or the account simply doesn't exist, the program will stop.

If they choose "Log-in", the user will be prompted to enter their username and password, the program will then check if the account exists and the password is correct, if they confirmation is a success, then player 2 will be prompted to enter their details, as long as they are not the same as player one, which if they are, they will be told that they can create a new account and the log-in process will wait for input. 

If they choose "Forgotten Account Details", the user will be prompted with a new menu showings two options to select from, those being: "Forgotten Username" and "Forgotten Password". If the user picks "Forgotten Username", they will be prompted to enter the email associated with the account, if the email is linked to an account, the user will be emailed their username. If, for whatever reason, the email is linked to multiple accounts, the user will be told and they will recieve multiple emails. If instead the user chooses "Forgotten Password", the user will be prompted to enter the username of the account, and then the email. If the account exists and the email matches, they will be then be emailed a 4 digit code for confirmation, once confirmed, they will be emailed their password. If any of the steps fail in either of the procedures, the program will exit.

Once both users have logged in, the game will begin. Player one will be assigned to the first user, player two will be assigned to the second user. They will be shown the rules of the game, which are as follows: Players one or two will roll their die, they will then add it to their total, if their total is now even, they will add ten points to their score, if their total is instead odd, they will subtract five from their total score. If they roll a double, another dice will be rolled, this will then be added to their score, and they will either lose or gain points. The score of any player should not go below zero, this will be hard coded in to prevent this. After five rounds the player with the highest score wins, if both players have the same total score, they each get one more dice roll until someone wins.

Once the game is over, the program will check if the winner of the game has beaten any scores on the leaderboard. If they have, they will be placed accordingly into the leaderboard. However, if they already exist on the leaderboard, they will not be placed onto the leaderboard if their score is smaller than their score already on the leaderboard. If they beat their old score, they get the honour of having multiple places on the leaderboard.


------------

### Code Diary
Everything in my code will be included (including comments)

This function replaces the `system.out.println()` and adds a bit of extra data.
```java
public static void log(String data) {
        //This is simply because I can and because I want to because it makes my life ever so slightly easier.
        Date date = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
        String time = "[" + fmt.format(date) + "] ";
        System.out.println(time + data);
    }
```
In practice it'll look like:
```
log("Hello World!)
```

The output to the console will then therefore look like this:
```
[09:41] Hello World!
```
[========]

------------
[========]

Now entering the GUI space of the code, this function will output a (sort of) nice welcome message:
```java
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
```
The output of this code is:
```
[09:41] ┎------------------------┐
[09:41] |       Welcome!         |
[09:41] ┣------------------------┫
[09:41] |     NEA  2022/23       |
[09:41] ┖------------------------┘
```


This function shows the options that the user can pick from:
```java
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
```
The output of this code is:
```Shell
[09:41] |1| Login
[09:41] |2| Create Account
[09:41] |3| Delete Account
[09:41] |4| Forgotten Account Details
```

[========]

------------


[========]
### Moving onto the "dataHandling" Class
As you may have guessed, the code in this class is todo with datahandling (well almost all of it)

[========]

This function generates the 4 digit code:
```java
public static String generateCode(){
    // Create a new random number generator
    Random rand = new Random();

    // Create an array with 4 elements, each initialized to a random integer between 0 and 9 (inclusive)
    int[] codeArr = {rand.nextInt(9),rand.nextInt(9),rand.nextInt(9),rand.nextInt(9)};

    // Create a new StringBuilder to store the generated code
    StringBuilder codeBuilder = new StringBuilder();

    // Iterate over the elements in the code array
    for (int i = 0; i < codeArr.length; i++) {
        // Append each element in the array to the StringBuilder
        codeBuilder.append(codeArr[i]);
    }

    // Return the generated code as a String
    return codeBuilder.toString();
}
```

[========]

[========]

This function is used at the start of the program, the very start and checks for file validity:
```java
    public static void checkFile() {
        try {
            //checks if the folder AND the accounts file can be created/have been.
            if(accountsFolder.mkdir() && accountsFile.createNewFile() && settingsFile.createNewFile() && winnersFile.createNewFile()){
                log("Fixed All Files.");
                writeDefault(2);
            }
            //just checks if the accounts file has been created.
            if(accountsFile.createNewFile()){
                log("Fixed Accounts File");
                writeDefault(0);
            }
            //just checks if the settings file has been created.
            if(settingsFile.createNewFile()){
                log("Fixed Settings File");
                writeDefault(1);
            }
            if(winnersFile.createNewFile()){
                log("Created Winners File - " + winnersFile.getPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

```

[========]

[========]

This function writes to the accounts file:
```java
    public static void writeToFile(String username, String password, String email){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(accountsFile,true));
            writer.newLine(); //creates new line to separate the accounts.
            writer.write(username + ":" + password + ":" + email); //writes username and password.
            writer.close(); // "closes" file
        }catch (Exception e){
            e.printStackTrace(); //catches any error and prints them.
        }
    }
```

[========]

[========]

As a warning, the following function and the code you are about to see is disgusting, it is horrible.
This code writes the default lines into each file:
```java
public static void writeDefault(int whichFile){ //this is practically useless, but until I find a fix to "writeToFile", it'll have to do.
    try{
        // Check which file to write to
        if(whichFile == 0){ //0 is accounts file
            // Create a BufferedWriter to write to the accounts file
            BufferedWriter writeToAccounts = new BufferedWriter(new FileWriter(accountsFile, true));
            // Write the default account information to the file
            writeToAccounts.write("ADMIN:PASSWORD:NOEMAIL");
            // Close the BufferedWriter
            writeToAccounts.close();

        }else if(whichFile == 1){ //1 is the settings file.
            // Create a BufferedWriter to write to the settings file
            BufferedWriter writeToSettingsFile = new BufferedWriter(new FileWriter(settingsFile, true));
            // Write the default settings to the file
            writeToSettingsFile.write("To change the number of accounts created, change this number:4");
            writeToSettingsFile.newLine();
            writeToSettingsFile.write("To change how many numbers appear on the end of each name, change this number:4");
            writeToSettingsFile.newLine();
            writeToSettingsFile.write("Change value to TRUE/FALSE to either ENABLE/DISABLE adding numbers on the end of your suggested username:TRUE");
            writeToSettingsFile.newLine();
            writeToSettingsFile.write("Change value to TRUE/FALSE to either ENABLE/DISABLE adding words on the end of your suggested username:TRUE");
            // Close the BufferedWriter
            writeToSettingsFile.close();

        }else if(whichFile == 2){ //2 is both.
            // Create a BufferedWriter to write to the accounts file
            BufferedWriter writeToAccounts = new BufferedWriter(new FileWriter(accountsFile, true));
            // Write the default account information to the file
            writeToAccounts.write("ADMIN:PASSWORD:NOEMAIL");
            // Close the BufferedWriter
            writeToAccounts.close();

            // Create a BufferedWriter to write to the settings file
            BufferedWriter writeToSettingsFile = new BufferedWriter(new FileWriter(settingsFile, true));
            // Write the default settings to the file
            writeToSettingsFile.write("To change the number of accounts created, change this number:4");
            writeToSettingsFile.newLine();
            writeToSettingsFile.write("To change how many numbers appear on the end of each name, change this number:4");
            writeToSettingsFile.newLine();
            writeToSettingsFile.write("Change value to TRUE/FALSE to either ENABLE/DISABLE adding numbers on the end of your suggested username:TRUE");
            writeToSettingsFile.newLine();
            writeToSettingsFile.write("Change value to TRUE/FALSE to either ENABLE/DISABLE adding words on the end of your suggested username:TRUE");
            // Close the BufferedWriter
            writeToSettingsFile.close();
        }

    }catch (Exception e){
        // Print the stack trace of the exception if it occurs
        e.printStackTrace();
    }
}

```

[========]

[========]

The following function is normal, it reads in the data from the "accounts.txt" file:
```java
public static ArrayList<String> readAccountsFile(){
        ArrayList<String> accounts = new ArrayList<>(); //create arraylist to store accounts.

        try{
            /*
            * Read from file
            * Store in array list in the format: "username:password"
             */

            Scanner reader = new Scanner(accountsFile);
            while(reader.hasNextLine()){
                accounts.add(reader.nextLine());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return accounts;
    }
```

[========]

[========]

Same premise here, reads in the values for the "settings.txt" file and returns the entire line:
```java
public static ArrayList<String> readSettingsFile(){
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> returnValues = new ArrayList<>();

        try{
            Scanner reader = new Scanner(settingsFile);
            while(reader.hasNextLine()){
                data.add(reader.nextLine());
            }
            for (String valueFrom : data) {
                String[] newValues = valueFrom.split(":");
                /** BUG THROWN HERE */
                if(newValues[1].contains(" ")){
                    /** BUG THROWN HERE */
                    String[] letters = newValues[1].split("");
                    StringBuilder finalString = new StringBuilder();

                    for (String letter : letters) {
                        if (!letter.equals(" ")) {
                            finalString.append(letter);
                        }
                    }
                    newValues[1] = finalString.toString();
                }


                returnValues.add(newValues[1]);
            }

        }catch (Exception e){
//            Although an error is thrown, it doesn't interfere with the actual read, therefore becoming obsolete.
//            log("Something went wrong in: readSettingsFile");
//            e.printStackTrace();

        }
        return returnValues;
    }
```

[========]

[========]

Again, same idea, reads the data from the "winners.txt" file and returns the data:

```java
public static ArrayList<String> readWinnerFile(){
        ArrayList<String> data = new ArrayList<>();

        try{
            Scanner reader = new Scanner(winnersFile);

            for (int i = 0; i <=4; i++) {
                if(reader.hasNext()){
                    data.add(reader.nextLine());
                }
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return data;
    }

```

[========]

[========]

This function takes the data read in from the "winners.txt" file and gets the winners section:
```java
public static ArrayList<String> extractPreviousWinnersUsernames(ArrayList<String> data){
        ArrayList<String> usernames = new ArrayList<>();

        try{
            for (String datum : data) {
                String[] current = datum.split(":");
                usernames.add(current[0]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return usernames;
    }
```

[========]

[========]

This function also takes the data read in from the "winners.txt" file, but instead extracts the scores section:
```java
    public static ArrayList<String> extractPreviousWinnersScores(ArrayList<String> data){
        ArrayList<String> scores = new ArrayList<>();

        try{
            for(String datum : data){
                String[] current = datum.split(":");
                scores.add(current[1]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return scores;
    }
```

[========]

[========]

This function sorts the winners of the given arraylist, this is called at the end of the game if needed:
```java
public static ArrayList<String> sortWinners(ArrayList<String> usernames, ArrayList<String> scores){
        // Declare temporary variables to hold values during sorting
        int tempvar;
        String tempName;

        try{
            // Loop through the scores list
            for (int i = 0; i < scores.size(); i++) {
                // Compare the current score to all previous scores
                for (int j = 1; j < scores.size(); j++) {
                    // If the current score is greater than the previous score, swap them
                    if(Integer.parseInt(scores.get(i)) > Integer.parseInt(scores.get(j-1))){
                        tempvar = Integer.parseInt(scores.get(j-1));
                        tempName = usernames.get(j-1);

                        scores.set(j-1, scores.get(i));
                        scores.set(i, String.valueOf(tempvar));

                        usernames.set(j-1, usernames.get(i));
                        usernames.set(i, tempName);
                    }
                }
            }
        }catch (Exception e){
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
        // Create a new list to store the sorted results
        ArrayList<String> sortedList = new ArrayList<>();

        // Loop through the usernames list and add the username and score to the sorted list
        for (int i = 0; i < usernames.size(); i++) {
            sortedList.add(usernames.get(i) + ":" + scores.get(i));
        }

        // Return the sorted list
        return sortedList;
    }
```







#Final-NEA
