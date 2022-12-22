import java.util.Random;
import java.util.Scanner;

public class gameHandling{
    public static Scanner inp = new Scanner(System.in);
    public static void log(String data){Main.log(data);}

    public static int generateNumber(){
        Random gen = new Random();
        return gen.nextInt(7);
    }

    public static String[] doGame(String playerOneUsername, String playerTwoUsername) throws InterruptedException {
        /**
         * As you know the rules of the game are as follows:
         *  - Player One rolls two die, if they are a double they get to roll again.
         *  - This score is added to their total, if it is even add an extra 10, if not, subtract five.
         *  - Repeat this process for player two.
         *  - Repeat This process 5 times.
         *  - The player with the highest total at the end wins.
         *  - If they have the same total they play an extra round until somebody has won.
         */
        String[] winner = new String[2];
        int oneTotal=0, twoTotal=0;

        for (int i = 0; i < 5; i++) {
            Thread.sleep(100);
            log(playerOneUsername + " type \"R\" to roll: ");
            if(inp.nextLine().equalsIgnoreCase("r")){
                oneTotal = doMath(playerOneUsername, oneTotal);
            }else{
                Thread.sleep(100);
                log("You failed to type \"R\"! Skipping " + playerOneUsername + "'s turn!");
            }
            Thread.sleep(100);
            log(playerTwoUsername + " type \"R\" to roll: ");
            if(inp.nextLine().equalsIgnoreCase("r")){
                twoTotal = doMath(playerTwoUsername, twoTotal);
            }else{
                Thread.sleep(100);
                log("You failed to type \"R\"! Skipping " + playerTwoUsername + "'s turn!");
            }
        }

        Thread.sleep(150);
        int rounds = 5;
        if(oneTotal==twoTotal){
            Thread.sleep(100);
            log("It's a  tie!");
            Thread.sleep(100);
            log("Playing until somebody wins!");

            //noinspection ReassignedVariable
            while(oneTotal!=twoTotal){
                log(playerOneUsername + " type \"R\" to roll: ");
                String r = inp.nextLine();
                if(r.equalsIgnoreCase("r")){
                    oneTotal = doMath(playerOneUsername, oneTotal);
                }else{
                    log("You failed to type \"R\"! Skipping " + playerOneUsername + "'s turn!");
                }
                log(playerTwoUsername + " type \"R\" to roll: ");
                r = inp.nextLine();
                if(r.equalsIgnoreCase("r")){
                    twoTotal = doMath(playerTwoUsername, twoTotal);
                }else{
                    log("You failed to type \"R\"! Skipping " + playerTwoUsername + "'s turn!");
                }

                rounds++;
            }
        }


        log("After " + rounds + " rounds, the winner is: ");
        Thread.sleep(100);
        if(oneTotal>twoTotal){
            log(playerOneUsername + " with a total of: " + oneTotal + " points!\n");
            Thread.sleep(100);
            log(playerTwoUsername + " you were close with a total of: " + twoTotal + " points!\n");
        }else {
            log(playerTwoUsername + " with a total of: " + twoTotal + " points!\n");
            Thread.sleep(100);
            log(playerOneUsername + " you were close with a total of: " + oneTotal + " points!\n");
        }
        winner[0] = (oneTotal>twoTotal) ? playerOneUsername : playerTwoUsername;
        winner[1] = String.valueOf(Math.max(oneTotal, twoTotal));

        log("Thank you for playing!");

        return winner;
    }

    public static int doMath(String player, int currentTotal) throws InterruptedException {

        int firstNumb = generateNumber(), secondNumb = generateNumber(), thirdNumb = generateNumber();
        firstNumb = (firstNumb == 0) ? firstNumb+1 : firstNumb;
        secondNumb = (secondNumb == 0) ? secondNumb+1 : secondNumb;
        thirdNumb = (thirdNumb == 0) ? thirdNumb+1 : thirdNumb;

        log(player  + " - you rolled: " + firstNumb + " " + secondNumb);
        if(firstNumb==secondNumb){
            Thread.sleep(100);
            log("Looks like \"" + player + "\" rolled a double!");
            Thread.sleep(100);
            log("Your third dice roll is: " + thirdNumb + "\n");

            currentTotal+=firstNumb+secondNumb+thirdNumb;
            int tempTotal = (currentTotal % 2 == 0) ? currentTotal+10 : currentTotal-5;
            String result = (tempTotal > currentTotal) ? "Your total was even, added 10 to total!" : "Your total was odd, subtracting 5 from your total!";
            Thread.sleep(200);
            log(result);
            currentTotal = Math.max(tempTotal, 0);
        }else{
            currentTotal+=firstNumb+secondNumb;
            int tempTotal = (currentTotal % 2 == 0) ? currentTotal+10 : currentTotal-5;
            String result = (tempTotal > currentTotal) ? "Your total was even, added 10 to total!" : "Your total was odd, subtracting 5 from your total!";
            log(result);
            currentTotal = Math.max(tempTotal, 0);
        }
        Thread.sleep(150);
        log(player + ", your new total is: " + currentTotal + "\n");

        return currentTotal;
    }
}

