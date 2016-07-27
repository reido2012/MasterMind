import java.io.IOException;

/**
 * This class creates starts the mastermind game.
 * @Author Omar Reid
 * @Release 19/12/2015
 */
public class Mastermind {
    public static void main(String[] args) throws IOException {
        Logic game = new Logic();
        /** Prints Main Logic ASCII Art**/
        game.getAsciiArt();
        /** Main Menu **/
        game.mainMenu();
        /** End Game Menu **/
        game.endGameMenu();
    }

}
