import java.io.*;
import java.util.ArrayList;

/**
 * Loads strings from the text file and into the game
 * @Author Omar Reid
 * @Release 16/12/2015
 */
public class LoadGame {

    private Logic game;

    /**
     * Constructor. Sets field values.
     * @param inGame
     *      State of the game.
     */
    public LoadGame (Logic inGame){
        game = inGame;
    }

    /**
     * Reads the contents of the game.txt file
     * @return
     *      ArrayList that contains the strings contained in the text file.
     * @throws IOException
     */
    public ArrayList<String> readFile() throws IOException {
        ArrayList<String> savedInformation = new ArrayList<>();

        File inputFile = new File("game.txt");

        if(!inputFile.exists()){
            inputFile.createNewFile();
        }

        String currentLine;

        //Read the content of the file into the ArrayList
        try{
            FileReader fr = new FileReader(inputFile);
            BufferedReader inputReader = new BufferedReader(fr);

            while((currentLine = inputReader.readLine()) != null){
                savedInformation.add(currentLine);
            }

            inputReader.close();

        }catch(Exception e){

        }

        return savedInformation;
    }

    /**
     * The strings in the ArrayList(file) are passed into the game
     * @return
     *      String to be put into game.
     * @throws IOException
     */
    public String doLoadGame() throws IOException {
        ArrayList<String> valuesToLoad =  readFile();
        String loadValue;

        if(game.loadCounter < valuesToLoad.size()){
            loadValue =  valuesToLoad.get(game.loadCounter);
            game.loadCounter++;
            return loadValue;
        }else{
            //100 notifies the game state that the file is empty
            game.loadGame = false;
            return "100";
        }
    }
}
