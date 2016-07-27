/**
 *Defines the properties and the behaviors of the game.
 * @Author Omar Reid
 * @Release 04/12/2015
 */

import java.io.IOException;
import java.util.ArrayList;


public class Logic {

    /**
     * Initialise Variables
     */
    protected int numberOfColours;
    protected int numberOfHiddenPegs;
    protected int numberOfTrials;
    protected int nonChangeNoOfTrials;
    protected int gameMode;
    protected int loadCounter = 0;
    protected int numIn;
    protected int guessNumber = 0;
    int[] codeColorCount;
    int[] guessColorCount;
    boolean loadGame = false;
    boolean loadGuesses = false;
    boolean areColorsSaved = false;
    boolean areHiddenPegsSaved = false;
    boolean areNumberOfTrialsSaved = false;
    boolean isGameModeSaved = false;
    boolean shouldSave = false;
    boolean firstTime = true;

    //Red is r, Green is g, Yellow is y, Purple is p, Cyan is c, Khaki is k, Tan is t
    char[] colors = {'r','g', 'b', 'y', 'p', 'o', 'k', 't'};
    //Stores guesses for when we save the game
    ArrayList<String> guesses = new ArrayList<>();

    String code;
    String guess;

    /**
     * Object instantiation
     */
    //Code maker
    Player p1 = new Player();
    //Code guesser
    Player p2 = new Player();
    //Create a text interface object
    MasterMindTextUI ui = new MasterMindTextUI();
    //Instantiate SaveGame so we can use functions from it
    SaveGame sg2 = new SaveGame(this);
    //Instantiate LoadGAme
    LoadGame lg2 = new LoadGame(this);
    //Instantiate Setup so we can use the setup functions
    Setup set = new Setup(this, sg2, lg2, p1);
    //Create an AI
    AI watson = new AI(this);


    /**
     * Starts a new game. Sets game up and runs it by calling the setGameUp and run methods.
     * @throws IOException
     */
    public void newGame() throws IOException {
        /** Set game up**/
        set.setGameUp(ui);
        /** run game **/
        run();
    }


    /**
     * Runs the game. The type of game depends on the game mode selected during setup.
     * @throws IOException
     */
    public void run() throws IOException {


        if(gameMode == 1){
            //The user has selected to mode as Human Code Maker vs Human Code Breaker
            code = getAndProcessCode();
            //We only save the code if we aren't loading a game as we don't want to overwrite saved data
            if(!loadGuesses){
                saveCode();
            }
            ui.gameSaveInstructions();
            getAndProcessGuess();
        }

        if(gameMode == 2){
            //Player has selected a Computer Code Maker vs Human Code Breaker
            if(loadGuesses){
                //Loads the line in the file that corresponds to the code
                code = lg2.doLoadGame();
                //If this fails then we tell our AI(Artificial Intelligence) to create a code
                if(code.equals("100")){
                    loadGuesses = false;
                    code = watson.createCode();
                }
            }else{
                //Our AI creates a code
                code = watson.createCode();
            }

            if(!loadGuesses){
                //Saves code computer has set
                saveCode();
            }
            //Tells the user CPU has set code and how to save the game
            ui.displayCPUHasSet();
            ui.gameSaveInstructions();
            //Begins retrieval and processing of the guess
            getAndProcessGuess();
        }

        if(gameMode == 3){

            //Player has selected an AI vs AI game.
            code = watson.createCode();
            ui.displayCPUHasSet();
            boolean cpuCodeBreakerHasWon = false;
            while(numberOfTrials > 0){
                guess = watson.generateGuess();
                ui.userMessage("Watson(AI) Guess : " + guess);

                //If the guess is equal to the code you've won
                if(guessResult()){
                    cpuCodeBreakerHasWon = true;
                    ui.codeBreakerHasWonArt();
                    break;
                }
            }

            if (!cpuCodeBreakerHasWon){
                ui.userMessage("Game Over!");
                ui.userMessage("The code was: " + ui.coloredString(code) + "\n");
                ui.codeMakerHasWonArt();
            }
        }

    }


    /**
     * This method handles the input and outputs for the main menu.
     * @throws IOException
     *        If there is a problem when accessing the file used for loading this error must be handled.
     */
    public void mainMenu() throws IOException {
        int numIn;
        int chances = 3;

        do {
            ui.displayMainMenu();
            numIn = p1.getNumberUserInput();

            if(numIn > 0){
                if(numIn == 1){
                    loadGame = false;
                    loadGuesses = false;
                    newGame();
                    break;
                }

                if(numIn == 2){
                    ui.displayLoadingGame();
                    loadGame = true;
                    loadGuesses = true;
                    newGame();
                    break;
                }

            }else{
                ui.userMessage("You haven't entered 1 or 2");
                ui.printChances(chances);
                chances--;
            }
        }while(chances>=0);

        if (numIn<1 || numIn > 2 ){
            System.exit(0);
        }

    }

    public void endGameMenu() throws IOException {
        int numIn;
        int chances = 3;

        do {

            ui.displayEndMenu();
            numIn = p1.getNumberUserInput();

            if(numIn > 0){

                if(numIn == 1){
                    loadGame = false;
                    loadGuesses = false;
                    newGame();
                    break;
                }

                if(numIn == 2){
                    ui.thankYou();
                    System.exit(0);
                    break;
                }

            }else{
                ui.userMessage("You haven't entered 1 or 2");
                ui.printChances(chances);
                chances--;
            }
        }while(chances>=0);

        if (numIn<1 || numIn > 2 ){
            System.exit(0);
        }


    }

    /**
     * This checks the guess that was given and provides feedback.
     * @return
     *      The string feedback on the guess that was given
     */
    public String checkGuess(String guess){
        //Gets information necessary for a checking of guess to be carried out correctly
        evaluate(guess);

        StringBuilder feedback = new StringBuilder("");
        String availableColors = usableColors(colors, numberOfColours);

        //Created array list to store characters that have been processed as this will change the feedback
        ArrayList<Character> processedBefore = new ArrayList<>();
        processedBefore.clear();
        for (int i = 0; i < code.length(); i++) {
            //Checks that the each character is in the available colors
            if(availableColors.indexOf(guess.charAt(i)) >= 0){
                if(code.indexOf(guess.charAt(i)) >= 0){
                    //The string contains the character in the ith psn
                    //Check if color is in correct psn
                    if(code.charAt(i) == guess.charAt(i)){
                        feedback.append("c");
                        processedBefore.add(guess.charAt(i));
                        //So we know how many of each color there is left
                        --guessColorCount[getIndexOf(guess.charAt(i))];
                        --codeColorCount[getIndexOf(guess.charAt(i))];

                    }else{
                        //If there isn't another peg of color (in position i) present and it can't be given a peg so it's given a blank
                        if(guessColorCount[getIndexOf(guess.charAt(i))] != codeColorCount[getIndexOf(guess.charAt(i))]){
                            feedback.append(" ");

                        }else{
                            feedback.append("w");
                            --guessColorCount[getIndexOf(guess.charAt(i))];
                            --codeColorCount[getIndexOf(guess.charAt(i))];
                        }
                    }
                }else{
                    //If color isn't present in code do nothing
                    feedback.append(" ");
                }
            }else{
                //Player has entered a color that you can't select
                ui.userMessage("You've entered a color that you can't use");
            }
        }

        //Turns the feedback from StringBuilder to String
        return feedback.toString();
    }

    /**
     * Turns the array of all colors into an array of usable colors. If user selects 4 colors we don't want an array of all 8 colors.
     * @param colors
     *          The character array that contains the first letter of all colors
     * @param numberOfColors
     *          The number of colors the user has selected to use during the game
     * @return
     *          The usable colors.
     */
    public String usableColors(char[] colors, int numberOfColors){
        char[] newColors = new char[numberOfColors];
        /** Copies the colors we want form colors and puts it in newcolors array**/
        System.arraycopy(colors, 0, newColors, 0, numberOfColors);
        return new String (newColors);
    }


    /**
     * Gets the guesses and makes sure it's in the correct format.
     * @throws IOException
     */
    public void getAndProcessGuess() throws IOException {
        boolean codeBreakerHasWon = false;
        boolean isGuessCorrectLength = false;
        shouldSave = false;

        //Makes sure you can only have the number of trials set
        while(numberOfTrials > 0){
            int chances2 = 3;
            do{
                //If we are loading guesses get guess from the file by using doLoadGame()
                if(loadGuesses){
                    guess = lg2.doLoadGame();
                    if(guess.equals("100")){
                        //Load has failed so we do normal guess input
                        loadGuesses = false;
                        guess = p2.getNextGuess();
                    }
                }else{
                    guess = p2.getNextGuess();
                }

                //Removes all whitespace from guess
                guess = guess.replaceAll("\\s+","");

                //Makes sure guess is of the correct format and the guess isn't the word save.
                if(guess.length() == numberOfHiddenPegs && !guess.equals("save")){
                    isGuessCorrectLength = true;
                    guesses.add(guess);
                    guessNumber++;
                    saveGuesses(shouldSave);
                    break;
                }else{
                    if(guess.equals("save")){
                        shouldSave = true;
                        ui.userMessage("Will save game on next valid input");
                    }else{
                        isGuessCorrectLength = false;
                        ui.userMessage("The guess you entered was not the correct length.");
                        ui.printChances(chances2);
                        ui.userMessage("");
                        chances2--;
                    }
                }

            }while(chances2>=0);

            if(!isGuessCorrectLength){
                System.exit(0);
            }

            //Checks if code breaker has won
            if(guessResult()){
                codeBreakerHasWon = true;
                ui.codeBreakerHasWonArt();
                break;
            }

        }

        if(!codeBreakerHasWon){
            ui.userMessage("Game Over!");
            ui.userMessage("The code was: " + ui.coloredString(code) + (char)27 + "[37m\n");
            ui.codeMakerHasWonArt();
        }

    }

    /**
     * Makes sure that the inputted code is of the correct format.
     * @return
     *      A correctly formatted code.
     * @throws IOException
     */
    public String getAndProcessCode() throws IOException {
        int chances1 = 3;
        boolean codeCorrectlySet = false;
        do{
            //If we are loading guesses that means we are loading codes too so get code from the file by using doLoadGame()
            if(loadGuesses){
                code = lg2.doLoadGame();
                if (code.equals("100")){
                    //If load fails we let user input code.
                    loadGuesses = false;
                    code = p1.getCodeToBreak();
                }
            }else{
                code = p1.getCodeToBreak();
            }

            code = code.replaceAll("\\s+","");

            //Makes sure code is of the correct format
            if(code.length() == numberOfHiddenPegs ){
                codeCorrectlySet = true;
                break;
            }else {
                ui.userMessage("The code you entered was not the correct length.");
                ui.printChances(chances1);
                ui.userMessage("");
                chances1--;
            }

        }while(chances1>=0);

        if(!codeCorrectlySet){
            System.exit(0);
        }else{
            ui.userMessage("Code is set.");
            ui.userMessage("");
        }

        return code;
    }

    /**
     * Gets the feedback and checks if player has won.
     * Displays the guess they entered in color.
     * @return
     */
    public boolean guessResult(){
        boolean codeBreakerHasWon;
        //remove whitespaces from string code
        code = code.replaceAll("\\s+","");
        //Get the feedback by passing the guess to the checkGuess function
        String feedback = checkGuess(guess);
        guess = guess.replaceAll("\\s+","");
        String feedbackFormatted = feedback.replaceAll("\\s+","");

        //Builds the string of c's the length of the code
        //A correct guess will have this exact feedback
        StringBuilder correctFeedback = new StringBuilder();
        for (int i = 0; i < code.length() ; i++) {
            correctFeedback.append("c");
        }

        if(feedbackFormatted.equals(correctFeedback.toString())){
            //Player 2 wins
            ui.userMessage("Code Breaker has won!");
            code = ui.coloredString(code);
            ui.userMessage("The code was: " + code);
            codeBreakerHasWon = true;
            return  codeBreakerHasWon;
        }else{
            guess = ui.coloredString(guess);
            ui.userMessage("Your guess was: " + guess);
            ui.userMessage("Feedback:" + feedback);
            numberOfTrials--;
            ui.userMessage(numberOfTrials + " trials left.");
            ui.userMessage("");
            codeBreakerHasWon = false;
            return codeBreakerHasWon;
        }
    }


    /**
     * Saves guesses to a file called game.txt
     * @param shouldSave
     *          If this boolean is true it will save the game.
     * @throws IOException
     */
    public void saveGuesses(boolean shouldSave) throws IOException {

        if(shouldSave){
            sg2.overwrite();
            sg2.finalSetupSave();
            saveCode();

            for (int i = 0; i < guessNumber; i++) {
                sg2.doSaveGame(guesses.get(i));
            }
        }
    }

    /**
     * Saves code to a file called game.txt
     * @throws IOException
     */
    public void saveCode() throws IOException{
        sg2.doSaveGame(code);
    }

    /**
     * Allows ascii art to be displayed in first screen.
     */
    public void getAsciiArt(){
        ui.showAsciiArt();
    }

    /**
     * Returns an array of the colors that are being used as pegs.
     * @return
     *      Character array of available colors.
     */
    public char[] getUsableColors(){
        char[] newColors = new char[numberOfColours];
        char[] colors = {'r','g', 'b', 'y', 'p', 'o', 'k', 't'};
            /** Copies the colors we want form colors and puts it in newcolors array**/
            System.arraycopy(colors, 0, newColors, 0, numberOfColours);

        return  newColors;
    }

    /**
     * Counts the number of each color there is in the guess and code.
     * @param guess
     *      The current guess
     */
    public void evaluate(String guess) {
        codeColorCount = new int[numberOfColours];
        guessColorCount = new int[numberOfColours];

        //Counts the number of times the color occurs in code and guess.
        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);
            char g = guess.charAt(i);
            ++ codeColorCount[getIndexOf(c)];
            ++ guessColorCount[getIndexOf(g)];
        }
    }

    /**
     * According to the color we get we give back the corresponding number so that I can use a int array to reference the characters.
     * @param letter
     *      The letter(color) that we will give the position for.
     * @return
     *      The position of the character in the guess and code color count arrays.
     */
    public int getIndexOf(char letter){
        switch (letter){
            case 'r':
                return 0;
            case 'g':
                return 1;
            case 'b':
                return 2;
            case 'y':
                return 3;
            case 'p':
                return 4;
            case 'o':
                return 5;
            case 'k':
                return 6;
            case 't':
                return 7;
            default:
                return 1;
        }
    }
}