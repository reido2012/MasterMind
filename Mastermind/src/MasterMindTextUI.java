/**
 * This class handles all the output to the user.
 * @Author oreid
 * @Release 04/12/2015
 */
public class MasterMindTextUI implements MasterMindUI{

    //Array of strings that uses ansi escape sequences to create colored text in terminal
    String[] colors2 = {(char)27 + "[31mr" + (char)27 + "[37m", (char)27 + "[32mg" + (char)27 + "[37m",  (char)27 + "[34mb"+ (char)27 + "[37m",  (char)27 + "[33my"+ (char)27 + "[37m",  (char)27 + "[35mp"+(char)27 + "[37m", (char)27 + "[36mc"+(char)27 + "[37m", "k", "t"};

    /**
     * Turns a normal string into a colored one.
     * @param toColor
     *       The string that we want to color.
     * @return
     *      The colored string.
     */
    public String coloredString(String toColor){
        int length = toColor.length();
        StringBuilder colorGuess = new StringBuilder();
        for (int i = 0; i < length; i++) {

            switch (toColor.charAt(i)){
                case 'r':
                    colorGuess.append(colors2[0]);
                    break;

                case 'g':
                    colorGuess.append(colors2[1]);
                    break;

                case 'b':
                    colorGuess.append(colors2[2]);
                    break;
                case 'y':
                    colorGuess.append(colors2[3]);
                    break;

                case 'p':
                    colorGuess.append(colors2[4]);
                    break;

                case 'c':
                    colorGuess.append(colors2[5]);
                    break;

                case 'k':
                    colorGuess.append(colors2[6]);
                    break;

                case 't':
                    colorGuess.append(colors2[7]);
                    break;

                case 'w':
                    colorGuess.append("w");
                    break;

                default:
                    colorGuess.append(toColor.charAt(i));
                    break;
            }
        }
        return colorGuess.toString();
    }

    /**
     * Prints out string to the terminal
     * @param s
     *      The string that we want to be outputted.
     */
    @Override
    public void userMessage(String s) {
        //Prompt user for input or tell user something
        System.out.println(s);
    }

    /**
     * Tells the user what game options they have.
     */
    public void displayGameModes(){
        userMessage("Enter \"1\" for 1 Human Code Maker and 1 Human Code Breaker. ");
        userMessage("Enter \"2\" for 1 Computer Code Maker and 1 Human Code Breaker. ");
        userMessage("Enter \"3\" for 1 Computer Code Maker and 1 Computer Code Breaker. ");
    }

    /**
     * Displays main menu to the user.
     */
    public void displayMainMenu(){
        userMessage("Enter \"1\" to start a new game. ");
        userMessage("Enter \"2\" to  Load an existing game ");
    }

    /**
     * Displays the End Menu to the user.
     */
    public void displayEndMenu(){
        userMessage("Enter \"1\" to start a new game. ");
        userMessage("Enter \"2\" to exit game ");

    }


    /**
     *Tells the user that the CPU has
     */
    public void displayCPUHasSet(){
        userMessage("Computer has set code to be broken.");
    }

    public void gameSaveInstructions(){
        userMessage("Type \"save\" to save during gameplay");
        userMessage("");
    }

    /**
     * Tells the user that we are loading the game
     */
    public void displayLoadingGame(){
        userMessage("Loading game...");
    }

    /**
     * Tells the user how many chances they have.
     * @param chances
     *      The number of chances the user has left.
     */
    public void printChances( int chances){
        if(chances > 1 || chances == 0 ){
            //Plural
            userMessage("You have " + chances + " chances left");
        }else{
            //Singular
            userMessage("You have " + chances + " chance left");
        }
    }

    /**
     * Displays acii art Logic
     */
    public void showAsciiArt(){
        userMessage("" +
                "" +
                " /$$      /$$                       /$$                         /$$      /$$ /$$                 /$$\n" +
                "| $$$    /$$$                      | $$                        | $$$    /$$$|__/                | $$\n" +
                "| $$$$  /$$$$  /$$$$$$   /$$$$$$$ /$$$$$$    /$$$$$$   /$$$$$$ | $$$$  /$$$$ /$$ /$$$$$$$   /$$$$$$$\n" +
                "| $$ $$/$$ $$ |____  $$ /$$_____/|_  $$_/   /$$__  $$ /$$__  $$| $$ $$/$$ $$| $$| $$__  $$ /$$__  $$\n" +
                "| $$  $$$| $$  /$$$$$$$|  $$$$$$   | $$    | $$$$$$$$| $$  \\__/| $$  $$$| $$| $$| $$  \\ $$| $$  | $$\n" +
                "| $$\\  $ | $$ /$$__  $$ \\____  $$  | $$ /$$| $$_____/| $$      | $$\\  $ | $$| $$| $$  | $$| $$  | $$\n" +
                "| $$ \\/  | $$|  $$$$$$$ /$$$$$$$/  |  $$$$/|  $$$$$$$| $$      | $$ \\/  | $$| $$| $$  | $$|  $$$$$$$\n" +
                "|__/     |__/ \\_______/|_______/    \\___/   \\_______/|__/      |__/     |__/|__/|__/  |__/ \\_______/\n" +
                "                                                                                                    \n" +
                "                                                                                                    \n" +
                "                                                                                                                                                                                            ");
    }

    public void codeBreakerHasWonArt(){
        userMessage(" " +
                "+-+-+-+-+ +-+-+-+-+-+-+-+ +-+-+-+ +-+-+-+\n" +
                " |C|o|d|e| |B|r|e|a|k|e|r| |H|a|s| |W|o|n|\n" +
                " +-+-+-+-+ +-+-+-+-+-+-+-+ +-+-+-+ +-+-+-+" + "\n");
    }

    public void codeMakerHasWonArt(){
        userMessage("" +
                " +-+-+-+-+ +-+-+-+-+-+ +-+-+-+ +-+-+-+\n" +
                " |C|o|d|e| |M|a|k|e|r| |H|a|s| |W|o|n|\n" +
                " +-+-+-+-+ +-+-+-+-+-+ +-+-+-+ +-+-+-+" + "\n");
    }

    public void thankYou(){
        userMessage("" +
                " /$$$$$$$$ /$$                           /$$                       /$$$$$$$$                        /$$$$$$$  /$$                     /$$                           /$$\n" +
                "|__  $$__/| $$                          | $$                      | $$_____/                       | $$__  $$| $$                    |__/                          | $$\n" +
                "   | $$   | $$$$$$$   /$$$$$$  /$$$$$$$ | $$   /$$  /$$$$$$$      | $$     /$$$$$$   /$$$$$$       | $$  \\ $$| $$  /$$$$$$  /$$   /$$ /$$ /$$$$$$$   /$$$$$$       | $$\n" +
                "   | $$   | $$__  $$ |____  $$| $$__  $$| $$  /$$/ /$$_____/      | $$$$$ /$$__  $$ /$$__  $$      | $$$$$$$/| $$ |____  $$| $$  | $$| $$| $$__  $$ /$$__  $$      | $$\n" +
                "   | $$   | $$  \\ $$  /$$$$$$$| $$  \\ $$| $$$$$$/ |  $$$$$$       | $$__/| $$  \\ $$| $$  \\__/      | $$____/ | $$  /$$$$$$$| $$  | $$| $$| $$  \\ $$| $$  \\ $$      |__/\n" +
                "   | $$   | $$  | $$ /$$__  $$| $$  | $$| $$_  $$  \\____  $$      | $$   | $$  | $$| $$            | $$      | $$ /$$__  $$| $$  | $$| $$| $$  | $$| $$  | $$          \n" +
                "   | $$   | $$  | $$|  $$$$$$$| $$  | $$| $$ \\  $$ /$$$$$$$/      | $$   |  $$$$$$/| $$            | $$      | $$|  $$$$$$$|  $$$$$$$| $$| $$  | $$|  $$$$$$$       /$$\n" +
                "   |__/   |__/  |__/ \\_______/|__/  |__/|__/  \\__/|_______/       |__/    \\______/ |__/            |__/      |__/ \\_______/ \\____  $$|__/|__/  |__/ \\____  $$      |__/\n" +
                "                                                                                                                            /$$  | $$               /$$  \\ $$          \n" +
                "                                                                                                                           |  $$$$$$/              |  $$$$$$/          \n" +
                "                                                                                                                            \\______/                \\______/           ");
    }
}
