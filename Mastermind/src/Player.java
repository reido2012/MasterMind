import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @Author Omar Reid
 * @Release 04/12/2015
 */
public class Player {


    int getNumberUserInput(){
        int in = 0;
        Scanner input = new Scanner(System.in);
        try{
            in = input.nextInt();
        }catch (NoSuchElementException nse1){}
        return in;
    }

    public String getNextGuess() throws IOException {
        if (System.console() != null) {
            return System.console().readLine("Code Breaker - Enter guess: ");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                System.in));
        System.out.print("Code Breaker - Enter Guess: ");
        return reader.readLine();
    }

    public String getCodeToBreak() throws IOException {
        char[] code;
        if (System.console() != null) {
            code = System.console().readPassword("Code Maker - Enter Code To Break: ");
            return new String(code);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                System.in));
        System.out.print("Code Maker - Enter Code: ");
        return reader.readLine();
    }
}
