import java.io.IOException;

/**
 * This class handles set up required to play the mastermind game.
 * @Author Omar Reid
 * @Release 22/12/2015
 */
public class Setup {

    Logic game;
    SaveGame sg;
    LoadGame lg;
    Player p1;


    public Setup(Logic inGame, SaveGame inSG, LoadGame inLG, Player inP1){
        game = inGame;
        sg = inSG;
        lg = inLG;
        p1 = inP1;
    }



    public void setGameUp(MasterMindTextUI ui) throws IOException{
        /** Sets the number of colors**/
        setNumberOfColours(ui);
        /** Set number of hidden pegs**/
        setNumberOfPegsHidden(ui);
        /** Set number of trials**/
        setNumberOfTrials(ui);
        /** set game mode i.e. human v computer, human v human **/
        setGameMode(ui);
        /** Generate all codes **/

    }

    /** User Specifies number of colours in the game, player has 3 chances to select correct input **/
    private void setNumberOfColours(MasterMindTextUI ui) throws IOException {
        int chances = 3;

        do {

            if(game.loadGame){
                game.numIn = Integer.parseInt(lg.doLoadGame());
            }else{
                /** Tell user to enter a number between 3 and 8  **/
                ui.userMessage("During setup enter the number 50 to save the game.");
                ui.userMessage("Enter the number of colours you want to use (3 - 8): ");
                game.numIn = p1.getNumberUserInput();
            }

            if(game.numIn > 0 ){

                if(game.numIn<3 || game.numIn > 8){
                    if(game.numIn == 50){
                        //Save the game so far
                        game.shouldSave = true;
                        ui.userMessage("Will save game on next valid input.");
                    }else{
                        //User input is incorrect. Try and give them 3 chances to give number input
                        ui.userMessage("You can only chose between 3 and 8 colors.");
                        ui.printChances(chances);
                        ui.userMessage("");
                        chances--;
                    }

                }else{
                    game.numberOfColours = game.numIn;
                    String printColors = game.usableColors(game.colors, game.numberOfColours);
                    printColors = ui.coloredString(printColors);
                    ui.userMessage("The colors you will use are: " + printColors );
                    System.out.println((char)27 + "[37m");

                    if(game.shouldSave){
                        sg.doSaveGame(Integer.toString(game.numberOfColours));
                        game.areColorsSaved = true;
                    }

                    break;
                }

            }else{
                ui.userMessage("The input wasn't a number.");
                ui.printChances( chances);
                ui.userMessage("");
                chances--;
            }

        }while (chances>=0);



        if (game.numIn<3 || game.numIn > 8 ){
            System.exit(0);
        }

    }

    private void setNumberOfTrials(MasterMindTextUI ui) throws IOException {
        int chances = 3;
        game.shouldSave = false;

        do{
            if(game.loadGame){
                game.numIn = Integer.parseInt(lg.doLoadGame());
            }else{
                /** Tell user to enter a number between 0 and 30  **/
                ui.userMessage("Enter the number of trials you want (4 - 30): ");
                game.numIn = p1.getNumberUserInput();
            }

            if(game.numIn > 0 ){
                if(game.numIn<4 || game.numIn > 30){
                    if (game.numIn == 50){
                        game.shouldSave = true;
                        ui.userMessage("Will save game on next valid input.");
                    }else{
                        //User input is incorrect.
                        ui.userMessage("You can only chose between 4 and 30 trials.");
                        ui.printChances(chances);
                        chances--;
                    }

                }else{
                    game.numberOfTrials = game.numIn;
                    game.nonChangeNoOfTrials = game.numIn;
                    ui.userMessage("You have chosen to have " + game.nonChangeNoOfTrials + " trials");
                    ui.userMessage("");

                    if(!game.areHiddenPegsSaved && game.shouldSave){
                        sg.doSaveGame(Integer.toString(game.numberOfColours));
                        sg.doSaveGame(Integer.toString(game.numberOfHiddenPegs));
                        sg.doSaveGame(Integer.toString(game.nonChangeNoOfTrials));
                        game.areColorsSaved = true;
                        game.areHiddenPegsSaved = true;
                        game.areNumberOfTrialsSaved = true;
                    }
                    break;
                }

            }else{
                ui.userMessage("The input wasn't a number.");
                ui.printChances(chances);
                chances--;
            }

        }while(chances >=0);

        if (game.numIn<4 || game.numIn > 30){
            System.exit(0);
        }
    }

    private void setNumberOfPegsHidden(MasterMindTextUI ui) throws IOException {
        int chances = 3;
        game.shouldSave = false;

        do{
            if(game.loadGame){
                game.numIn = Integer.parseInt(lg.doLoadGame());
            }else{
                /** Tell user to enter a number between 3 and 8  **/
                ui.userMessage("Enter the number of hidden pegs you want (3 - 8): ");
                game.numIn = p1.getNumberUserInput();
            }

            if(game.numIn > 0 ){
                if(game.numIn<3 ||game.numIn > 8){
                    if(game.numIn == 50){
                        //Save the game so far
                        game.shouldSave = true;
                        //Function that clears file
                        ui.userMessage("Will save game on next valid input.");
                    }else{
                        //User input is incorrect.
                        ui.userMessage("You can only chose between 3 and 8 hidden pins.");
                        ui.printChances(chances);
                        chances--;
                    }

                }else{
                    game.numberOfHiddenPegs = game.numIn;
                    ui.userMessage("You have chosen to use " + game.numberOfHiddenPegs + " hidden pegs");
                    ui.userMessage("");

                    if(!game.areColorsSaved && game.shouldSave){
                        sg.doSaveGame(Integer.toString(game.numberOfColours));
                        sg.doSaveGame(Integer.toString(game.numberOfHiddenPegs));
                        game.areColorsSaved = true;
                        game.areHiddenPegsSaved = true;
                    }

                    break;
                }

            }else{
                ui.userMessage("The input wasn't a number.");
                ui.printChances(chances);
                chances--;
            }

        }while(chances >=0);



        if (game.numIn<3 || game.numIn > 8 ){
            System.exit(0);
        }

    }

    private void setGameMode(MasterMindTextUI ui) throws IOException {
        int chances = 3;
        game.shouldSave = false;

        do {
            if(game.loadGame){
                game.numIn = Integer.parseInt(lg.doLoadGame());
            }else{
                ui.displayGameModes();
                game.numIn = p1.getNumberUserInput();
            }

            if(game.numIn > 0){
                if (game.numIn >=1 && game.numIn<=3){
                    //Correct input
                    if (game.numIn == 1){
                        ui.userMessage("You will play as 1 Human Code Maker and 1 Human Code Breaker. ");
                        game.gameMode = 1;
                        if(!game.loadGuesses){
                            sg.finalSetupSave();
                        }

                        break;
                    }

                    if (game.numIn == 2){
                        ui.userMessage("You will play as 1 Computer Code Maker and 1 Human Code Breaker. ");
                        game.gameMode = 2;
                        if(!game.loadGuesses){
                            sg.finalSetupSave();
                        }

                        break;
                    }

                    if (game.numIn == 3){
                        ui.userMessage("You will play as 1 Computer Code Maker and 1 Human Code Breaker. ");
                        game.gameMode = 3;
                        break;
                    }

                }else{
                    if(game.numIn == 50){
                        game.shouldSave = true;
                        ui.userMessage("Will save game on next valid input.");
                    }else{
                        // User has entered incorrect input
                        ui.userMessage("You haven't entered 1, 2, or 3. ");
                        ui.printChances(chances);
                        chances--;
                    }
                }
            }
        }while(chances >=0);

        if (game.numIn<1 || game.numIn > 3 ){
            System.exit(0);
        }



    }

}
