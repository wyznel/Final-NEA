import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class dataHandling {
    public static void log(String data) {
        Main.log(data);
    }

    public static File accountsFile = Main.getAccountsFile();
    public static File accountsFolder = Main.getAccountsFolder();
    public static File settingsFile = Main.getSettings();
    public static File winnersFile = Main.getWinnersFile();
    public static File deletedAccountsFile = Main.getDeletedAccountsFile();
    public static String generateCode(){
        Random rand = new Random();
        int[] codeArr = {rand.nextInt(9),rand.nextInt(9),rand.nextInt(9),rand.nextInt(9)};
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < codeArr.length; i++) {
            codeBuilder.append(codeArr[i]);
        }
        return codeBuilder.toString();
    }
    public static void checkFile() {
        try {
            //checks if the folder AND the accounts file can be created/have been.
            if(accountsFolder.mkdir() && accountsFile.createNewFile() && settingsFile.createNewFile() && winnersFile.createNewFile() && deletedAccountsFile.createNewFile()){
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
                log("Fixed Settings File - " + settingsFile.getPath());
                writeDefault(1);
            }
            if(winnersFile.createNewFile()){
                log("Created Winners File - " + winnersFile.getPath());
            }
            if(deletedAccountsFile.createNewFile()){
                log("Created store point for deleted accounts - " + deletedAccountsFile.getPath());
                writeDefault(3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(String username, String password, String email, File file){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
            if(file == deletedAccountsFile){
                writer.write(username + ":" + password + ":" + email + ":" + Mailer.deleteAccountCode + "\n");
            }else{
                writer.newLine();
                writer.write(username + ":" + password + ":" + email); //writes username and password.
            }
            writer.close(); // "closes" file
        }catch (Exception e){
            e.printStackTrace(); //catches any error and prints them.
        }
    }


    public static void writeDefault(int whichFile){ //this is practically useless, but until I find a fix to "writeToFile", it'll have to do.
        try{
            //this is very ugly please ignore.

            if(whichFile == 0){ //0 is accounts file
                BufferedWriter writeToAccounts = new BufferedWriter(new FileWriter(accountsFile, true));
                writeToAccounts.write("ADMIN:PASSWORD:NOEMAIL");
                writeToAccounts.close();

            }else if(whichFile == 1){ //1 is the settings file.
                BufferedWriter writeToSettingsFile = new BufferedWriter(new FileWriter(settingsFile, true));
                writeToSettingsFile.write("To change the number of accounts created, change this number:4");
                writeToSettingsFile.newLine();
                writeToSettingsFile.write("To change how many numbers appear on the end of each name, change this number:4");
                writeToSettingsFile.newLine();
                writeToSettingsFile.write("Change value to TRUE/FALSE to either ENABLE/DISABLE adding numbers on the end of your suggested username:TRUE");
                writeToSettingsFile.newLine();
                writeToSettingsFile.write("Change value to TRUE/FALSE to either ENABLE/DISABLE adding words on the end of your suggested username:TRUE");
                writeToSettingsFile.close();

            }else if(whichFile == 2){ //2 is both.
                BufferedWriter writeToSettingsFile = new BufferedWriter(new FileWriter(settingsFile, true));
                BufferedWriter writeToAccounts = new BufferedWriter(new FileWriter(accountsFile, true));
                BufferedWriter writer = new BufferedWriter(new FileWriter(deletedAccountsFile, true));

                writer.write("BLANK:BLANK:BLANK:BLANK");

                writeToAccounts.write("ADMIN:PASSWORD:NOEMAIL");
                writeToAccounts.close();

                writeToSettingsFile.write("To change the number of accounts created, change this number:4");
                writeToSettingsFile.newLine();
                writeToSettingsFile.write("To change how many numbers appear on the end of each name, change this number:4");
                writeToSettingsFile.newLine();
                writeToSettingsFile.write("Change value to TRUE/FALSE to either ENABLE/DISABLE adding numbers on the end of your suggested username:TRUE");
                writeToSettingsFile.newLine();
                writeToSettingsFile.write("Change value to TRUE/FALSE to either ENABLE/DISABLE adding words on the end of your suggested username:TRUE");
                writeToSettingsFile.close();
            }else if(whichFile == 3){
                BufferedWriter writer = new BufferedWriter(new FileWriter(deletedAccountsFile, true));
                writer.write("BLANK:BLANK:BLANK:BLANK");
                writer.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

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

    public static ArrayList<String> sortWinners(ArrayList<String> usernames, ArrayList<String> scores){
        int tempvar;
        String tempName;

        try{
            for (int i = 0; i < scores.size(); i++) {
                for (int j = 1; j < scores.size(); j++) {
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
            e.printStackTrace();
        }
        ArrayList<String> sortedList = new ArrayList<>();

        for (int i = 0; i < usernames.size(); i++) {
            sortedList.add(usernames.get(i) + ":" + scores.get(i));
        }

        return sortedList;
    }
    public static void writeNewWinners(ArrayList<String> data){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(winnersFile));
            for(String line : data){
                writer.write(line + "\n");
            }
            writer.close();
            log("Wrote new winners to file!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void showWinnerScreen(ArrayList<String> leaderboard){
        try{
            System.out.println("USER : SCORE");
            System.out.println("----| LEADERBOARD |----");
            for(String winner : leaderboard){
                System.out.println(winner);
                Thread.sleep(200);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ArrayList<String> readDeletedAccountsFile(){
        ArrayList<String> deletedAccounts = new ArrayList<>();

        try{
            Scanner reader = new Scanner(deletedAccountsFile);
            while(reader.hasNext()){
                deletedAccounts.add(reader.nextLine());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return deletedAccounts;
    }


    public static ArrayList<String> getDeletedAccountsCodes(ArrayList<String> deletedAccountsList){
        ArrayList<String> codes = new ArrayList<>();
        try{
            for(String line : deletedAccountsList){
                String[] tempArr = line.split(":");
                codes.add(tempArr[3]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return codes;
    }

    public static ArrayList<String> getDeletedAccountsUsernames(ArrayList<String> deletedAccountsList){
        ArrayList<String> usernames = new ArrayList<>();
        try{
            for(String line : deletedAccountsList){
                String[] tempArr = line.split(":");
                usernames.add(tempArr[0]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    return usernames;
    }

    public static ArrayList<String> getDeletedAccountsEmail(ArrayList<String> deletedAccountsList){
        ArrayList<String> emails = new ArrayList<>();
        try{
            for(String line : deletedAccountsList){
                String[] tempArr = line.split(":");
                emails.add(tempArr[2]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return emails;
    }
    public static ArrayList<String> getDeletedAccountsPasswords(ArrayList<String> deletedAccountsList){
        ArrayList<String> passwords = new ArrayList<>();
        try{
            for(String line : deletedAccountsList){
                String[] tempArr = line.split(":");
                passwords.add(tempArr[1]);
            }
        }catch (Exception e){
            e.printStackTrace();;
        }
        return passwords;
    }

}
