import java.util.ArrayList;

public class afterGameHandling{
    public static void log(Object data){Main.log((String) data);}
    public static void sortFile(String username, String score){
        try{
            //get old winners and previousScores/usernames
            ArrayList<String> oldLeaderboard = dataHandling.readWinnerFile();
            ArrayList<String> previousScores = dataHandling.extractPreviousWinnersScores(oldLeaderboard);
            ArrayList<String> previousWinners = dataHandling.extractPreviousWinnersUsernames(oldLeaderboard);

            for (int i = 0; i < previousWinners.size(); i++) {
                if(previousWinners.get(i).equals(username)){
                    if(Integer.parseInt(previousScores.get(i)) > Integer.parseInt(score)){
                        log("As you are already on the leaderboard AND you didn't beat your previous score, you didn't get a new place on the leaderboard!");
                        dataHandling.showWinnerScreen(oldLeaderboard);
                        System.exit(0);
                    }else{
                        break;
                    }
                }
            }

            if(oldLeaderboard.size() == 5){
                //if the 5 places on the scoreboard are filled, check if the last place score is bigger or smaller.
                if(Integer.parseInt(previousScores.get(previousScores.size()-1)) > Integer.parseInt(score)){
                    log("Looks like you didn't make the leaderboard! Better luck next time!");
                    dataHandling.showWinnerScreen(oldLeaderboard);
                }else if(Integer.parseInt(previousScores.get(previousScores.size()-1)) == Integer.parseInt(score)){
                    //if the user got the same as last show this:
                    log("You were so close! You matched with the 5th best score!");
                    dataHandling.showWinnerScreen(oldLeaderboard);
                }else if(Integer.parseInt(previousScores.get(previousScores.size()-1)) < Integer.parseInt(score)){
                    //if the user beat the 5th player, set the user to the 5th place
                    log("You placed on the leaderboard!");
                    previousWinners.set(previousWinners.size()-1, username);
                    previousScores.set(previousScores.size()-1, score);

                    //get updated leaderboard by sorting the previous one.
                    ArrayList<String> updatedLeaderboard = dataHandling.sortWinners(previousWinners, previousScores);
                    dataHandling.showWinnerScreen(updatedLeaderboard);

                    //write the sorted leaderboard to screen.
                    dataHandling.writeNewWinners(updatedLeaderboard);
                }
            }else{
                //if the previous score is smaller than the winning users score and the scoreboard doesn't have five people, add the winner to the leaderboard and re-sort it.
                previousWinners.add(username);
                previousScores.add(score);
                ArrayList<String> updatedLeaderboard = dataHandling.sortWinners(previousWinners, previousScores);
                dataHandling.writeNewWinners(updatedLeaderboard);
                dataHandling.showWinnerScreen(updatedLeaderboard);
            }
        }catch (Exception e){
            //if this is thrown that means the Winner file is empty.
            ArrayList<String> firstPlayer = new ArrayList<>();
            firstPlayer.add(username + ":" + score);
            dataHandling.writeNewWinners(firstPlayer);

        }
    }
}
