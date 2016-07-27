import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Defines the properties and behaviors of the Artificial Intelligence(AI) of the game.
 * @Author Omar Reid
 * @Release 07/12/2015
 */

public class AI{

    Logic game;
    private int counter = -1;
    private int randCounter = 0;
    ArrayList<String> AIGuesses = new ArrayList<>();
    ArrayList<Integer> randomNumbers = new ArrayList<>();
    String initialGuess;


    /**
     * The initial guess is each of the available colors
     * @return
     *      Our first guess.
     */
    public String getInitialGuess(){
        if(game.numberOfHiddenPegs < 5){
            String guess = "";
            for (int i = 0; i < game.numberOfHiddenPegs; i++) {
                guess += "r";
            }
            return guess;
        }else {
            return game.usableColors(game.colors, game.numberOfColours);
        }
    }


    Random rand = new Random();

    /**
     *Constructor. Sets field values for AI.
     * @param inGame
     *        The state of the game.
     */
    public AI( Logic inGame){
        game = inGame;
    }

    /**
     * Creates a random code.
     * @return
     *      The random code.
     */
    String createCode(){
        char[] newColors = new char[game.numberOfColours];
        char[] randomCode = new char[game.numberOfHiddenPegs];

        //Copies the colors we want from colors and puts it in the newcolors array
        System.arraycopy(game.colors, 0, newColors, 0, game.numberOfColours);

        //Create code from that char array newColors
        //Randomly generate a number between 0 and number of colors for the length of colors
        //Store the numbers in an array. Use those numbers to select random positions in the color array and create a string.
        Integer[] gen = new Integer[game.numberOfHiddenPegs];

        for (int i = 0; i <gen.length; i++) {
            Integer next = rand.nextInt(game.numberOfHiddenPegs);
            gen[i] = next;
        }

        for (int i = 0; i < game.numberOfHiddenPegs; i++) {
            randomCode[i] = newColors[gen[i]];
        }

        return new String (randomCode);
    }

    public String generateGuess(){
        counter++;
        if(counter == 0){
            initialGuess = getInitialGuess();
            AIGuesses.add(initialGuess);
            return initialGuess;

        }else{
            AIGuesses.add(generateNextGuess());
            String guess = AIGuesses.get(counter);
            return guess;
        }

    }

    public String generateNextGuess(){
        char[] makeNewGuess = new char[game.numberOfHiddenPegs];
        ArrayList<String> noWorkBefore = new ArrayList<>();
        char[] usableColors = game.getUsableColors();
        String previousGuess = AIGuesses.get(counter -1);
        String feedback = game.checkGuess(previousGuess);

        for(int i = 0 ; i < feedback.length(); i++){
            if(feedback.charAt(i) == 'w'){
                //If the feedback is a 'w' keep the color in the nextGuess but at a different position
                //Swap with a color that also corresponds with a w in code
                //loop through feedback again to find another w - make sure it skips itself
                //As soon as it finds the next w swap the color in that position with the color we have now
                for (int j = 0; j < feedback.length() ; j++) {
                    if(i == j & j+1< feedback.length()){ j++;}
                    if(feedback.charAt(j) == 'w'){
                        //Do the swap
                        makeNewGuess[i] = previousGuess.charAt(j);
                        makeNewGuess[j] = previousGuess.charAt(i);
                        break;
                    }

                    //If you fail to find a w, find a ' ' and swap with that
                    if(feedback.charAt(j) == ' '){
                        //then you put what's in i in j
                        makeNewGuess[j] = previousGuess.charAt(i);

                        //If the feedback is a ' ' then put a new color in that position in nextGuess. Add the color that gives that to list of colors that don't work in that position
                        noWorkBefore.add(previousGuess.charAt(i) + Integer.toString(i));
                        //Get a random color from usable colors array, the color must not be used in a previous guess in that position
                        generateRandomNumbers();
                        char colorToAdd = usableColors[randomNumbers.get(randCounter)];
                        randCounter++;
                        String checkAgainst = colorToAdd + Integer.toString(i);

                        if(noWorkBefore.contains(checkAgainst)){
                            colorToAdd = usableColors[randomNumbers.get(randCounter)];
                        }
                        //place that color in position i of next guess
                        // then in i you do a random char that will work
                        makeNewGuess[i] = colorToAdd;
                        break;
                    }
                }
            }

            if(feedback.charAt(i) == ' '){
                //If the feedback is a ' ' then put a new color in that position in nextGuess. Add the color that gives that to list of colors that don't work in that position
                noWorkBefore.add(previousGuess.charAt(i) + Integer.toString(i));
                //Get a random color from usable colors array, the color must not be used in a previous guess in that position
                generateRandomNumbers();

                char colorToAdd = usableColors[randomNumbers.get(randCounter)];
                randCounter++;
                String checkAgainst = colorToAdd + Integer.toString(i);

                if(noWorkBefore.contains(checkAgainst)){
                    colorToAdd = usableColors[randomNumbers.get(randCounter)];
                }

                //place that color in position i of next guess
                makeNewGuess[i] = colorToAdd;

            }

            if (feedback.charAt(i) == 'c'){
                //If the feedback is a 'c' then put color in nextGuess at same position
                makeNewGuess[i] = previousGuess.charAt(i);

            }
        }
        return new String(makeNewGuess);
    }

    public void generateRandomNumbers(){
        for (int j = 0; j <game.numberOfHiddenPegs; j++) {
            randomNumbers.add(j);
        }
        Collections.shuffle(randomNumbers);
    }

}
