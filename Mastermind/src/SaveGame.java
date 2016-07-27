import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Handles the saving of the game
 * @Author Omar Reid
 * @Release 16/12/2015
 */
public class SaveGame {
    private Logic game;

    /**
     *Constructor. Sets field values.
     * @param inGame
     *         The state of the game.
     */
    public SaveGame (Logic inGame){
        game = inGame;
    }

    /**
     * This function saves a string to the game.txt file
     * @param saveThis
     *      The string that will be saved to file
     * @throws IOException
     */
    public void saveVariable(String saveThis) throws IOException {
        File outputFile = new File("game.txt");

        //Creates the file if it doesn't exist
        if(!outputFile.exists()){
            outputFile.createNewFile();
        }

        FileWriter fw = new FileWriter(outputFile, true);
        BufferedWriter outputWriter = new BufferedWriter(fw);

        try{
            outputWriter.write(saveThis + "\n");
        }catch (Exception e){
            e.printStackTrace();
        }

        outputWriter.close();
    }

    /**
     * Overwrites data in file
     * @throws IOException
     */
    public void overwrite() throws IOException {
        File fileToOverwrite = new File("game.txt");

        if(!fileToOverwrite.exists()){
           fileToOverwrite.createNewFile();
        }

        FileWriter fw = new FileWriter(fileToOverwrite, false);
        //Empty current content
        fw.write("");
        fw.close();

    }

    /**
     * Saves to text all variables in the setup.
     * @throws IOException
     */
    public void finalSetupSave() throws IOException {
            doSaveGame(Integer.toString(game.numberOfColours));
            doSaveGame(Integer.toString(game.numberOfHiddenPegs));
            doSaveGame(Integer.toString(game.nonChangeNoOfTrials));
            doSaveGame(Integer.toString(game.gameMode));
            game.areColorsSaved = true;
            game.areHiddenPegsSaved = true;
            game.areNumberOfTrialsSaved = true;
            game.isGameModeSaved = true;
    }


    public void doSaveGame(String toFile) throws IOException {

        //If this is the first save of the current game overwrite what's in game.txt
        if(game.firstTime){
            overwrite();
            game.firstTime = false;
        }

        saveVariable(toFile);
    }
}
