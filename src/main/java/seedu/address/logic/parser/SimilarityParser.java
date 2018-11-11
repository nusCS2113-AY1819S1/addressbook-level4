package seedu.address.logic.parser;

import static seedu.address.logic.DiceCoefficient.diceCoefficient;

import java.util.ArrayList;

/**
 * Similarity parser takes in the UserInput and compares it with the commandList
 * to detect how similar the input is to the command, given that the input is NOT the command.
 */
public class SimilarityParser {
    private static final double DICE_COEFFICIENT_THRESHOLD = 0.5;
    /**
     * performSimilarityCheck takes in two args, the userInput, and the commandList in arrays.
     * If a similarity is found, returns the command most similar to the userInput.
     */
    public String performSimilarityCheck (String userInput, ArrayList<String> commandList) {
        for (String command : commandList) {
            if (diceCoefficient(command, userInput) > DICE_COEFFICIENT_THRESHOLD) {
                return command;
            }
        }
        return "";
    }
}
