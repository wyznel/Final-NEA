import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class words {
    public static void log(String data){
        Main.log(data);
    }

    public static ArrayList<String> wordList(){
        String[] list = {"Frivolous","Bamboozled","Syzygy","Dream","Fantasy","Blubber","Moonlight","bumfuzzle","Delicacy","Autumn","Quicksilver","Embellish","CyanicW","Canorous","Purple","Nefarious","Draconic","Beanpole","Bucolic","Quirky","Cognizant","Insidious","Phoenix","Silky","Epitome","Beatify","Dazzle","Glow","Beats","Magenta","Combat","Dazzling","Hyperbolic","Crystal","Charm","Sunshine","Galaxy","Mellow","Adorable","Bumblebee","Moon","Endeavor","Apocalyptic","Elixir","Fascinate","Phosphorous","Ortanique","Cuddle","Chilling","Ethereal","Synergistic","Peerless","Violet","Fipple","Sparkles","Frantic","Miracle","Tequila","Croze","Cuckoo","Dimple","Curious","Languish","Mystic","Dazzle","Velvet","Stellar","Frosty","Alluring","Bingo","Bizarre","Conflate","Hypnosis","Shine","Swaggy","Effervescent","Elixir","Superior","Insidious","Flippant","Boffola","Bracing","Nougat","Pleasant","Breeze","Statuesque","Rigorous","Bliss","Coxcomb","Huzzah","Typhoon","Classic","Effervescent","Bloom","Paradise","Dazzle","Endearing","Comely","Noxious","Twilight","Superlative","Flame","Aesthetic","Stellar","Flora","Bingle","Angelic","Spurrier","Candor","Luminous","Cocktail","Butterfly","Classy","Totalitarian","Refresh","Bugbear","Zigzag","Supercilious","Pearl","Supreme","Scrupulous","Blasphemy","Dazzle","Utopian","Winsome","Fancy","Stellar","Ace","Lavish","Bracing","Pleasing","Swanky","Outstanding","Delight","Ethereal","Superb","Ideal","Daunting","Pretty","Fabulous","Immense","Frosty","Supreme","Noxious","Classy","Chilly","Wild","Lovely","Dynamic","Cold","Beguiling","Glory","Mega","Pro","First-rate","Fab","Wondrous","Ravish","Posh","Astounding","Prime","Exemplary","Sublime","Tender","Misty","Splendid","Magnificient","Static","Peerless","Amuse","Charm","Tip-top","Tough","Superlative","Aggressive","Lively","Luminous","Jocular","Brill","Elegant","Rejoice","Evil","Crisp","Awesome","Superior","Real","Iconic","Strange","Crazy","Offbeat","Swaggy","Adorable","Fresh","Frantic","Raw","Adroit","Terrific","Aesthetic","Debonair","Elite","Vivacious","Fantasy"};

        return new ArrayList<>(Arrays.asList(list));
    }


    public static void fixFile(){

        File accountsFolder = new File( Main.getAccountsFolder() + "\\Words");

        try{
            //Create old words file.
            File unformattedWordFile = new File(accountsFolder + "\\unformattedWordList.txt");

            //create formatted words file.
            File formattedWordFile = new File(accountsFolder + "\\formattedList.txt");

            //deletes old files and folder in order for new ones to be created.


            if(accountsFolder.mkdir()) {
                if (unformattedWordFile.createNewFile()) {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(unformattedWordFile, true));
                    //put the words into the file.
                    ArrayList<String> wordslol = wordList();
                    for (String words : wordslol) {
                        writer.write(words);
                        log("Wrote: \"" + words + "\" to file: " + unformattedWordFile.getPath());
                        writer.newLine();
                    }
                    writer.close();

//                  now time to format them for an array.
                    // we are going to read them in from the file as to demonstrate what had to be done.


                    if (formattedWordFile.createNewFile()) {
                        BufferedWriter writeToFormattedFile = new BufferedWriter(new FileWriter(formattedWordFile, true));
                        Scanner reader = new Scanner(unformattedWordFile);
                        ArrayList<String> listofwords = new ArrayList<>();
                        while(reader.hasNextLine()){
                            listofwords.add(reader.nextLine());
                        }

                        for (int i = 0; i < listofwords.size(); i++) {
                            if(i == listofwords.size()-1){
                                writeToFormattedFile.write("\"" + listofwords.get(i) + "\"]");
                            }else if(i == 0){
                                writeToFormattedFile.write("[\"" + listofwords.get(i) + "\",");
                            }else{
                                writeToFormattedFile.write("\"" + listofwords.get(i) + "\",");
                            }
                            log("Wrote: \"" + listofwords.get(i) + "\" to file: " + formattedWordFile.getPath());
                        }

                        System.out.println("\nWrote all unformatted words to the file: " + unformattedWordFile.getPath());
                        System.out.println("Wrote formatted list to the file: " + formattedWordFile.getPath() + "\n");
                        writeToFormattedFile.close();

                    }
                }
            }else{


                log(">> Attempting to delete all old files... >>\n");
                Thread.sleep(200);
                log(">> Deleted >> " + formattedWordFile.getPath());
                log(">> Deleted >> " + unformattedWordFile.getPath());
                log(">> Deleted >> "+ accountsFolder.getPath() + "\n");
                //deletes old files.
                formattedWordFile.delete();
                unformattedWordFile.delete();
                accountsFolder.delete();
                //begins process to make them again.
                fixFile();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
