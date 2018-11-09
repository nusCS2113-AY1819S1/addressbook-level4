package seedu.address.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * This class provides for the expediting of user input,
 * by way of pre-typed commands to speed up the flow of a presentation.
 */
public class ExpeditedInputs {
    // DEBUG CLASS FOR PRESENTATION
    private static ArrayList<String> commands = new ArrayList<String>();
    private static int counter = 0;

    /**
     * Loads input data (pre-typed commands).
     */
    public static void loadInputData() {
        try (BufferedReader br = new BufferedReader(new FileReader("inputs.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                commands.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getNextCommand() {
        counter++;
        return commands.get(counter - 1);
    }

    public static String getPreviousCommand() {
        counter--;
        return commands.get(counter + 1);
    }
}
