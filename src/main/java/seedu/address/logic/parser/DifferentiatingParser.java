//@@author guiyong96
package seedu.address.logic.parser;

import static seedu.address.logic.DiceCoefficient.diceCoefficient;

import seedu.address.request.requestcommands.DeleteRequestCommand;
import seedu.address.request.requestcommands.RequestCommand;
import seedu.address.request.requestcommands.ToggleRequestCommand;
import seedu.address.request.requestcommands.UndoRequestCommand;

/**
 * Differentiating Parser separates commands going to the BookInventoryParser and RequestListParser.
 */
public class DifferentiatingParser {

    //Different threshold tested for higher accuracy.
    private static final double DICE_COEFFICIENT_THRESHOLD = 0.5;
    private static final double DICE_COEFFICIENT_ADJUSTED_THRESHOLD = 0.7;
    private static final String requestCommand = "request";

    /**
     *  parseInput takes in three args, the trimmed commands, the prev command (To check if undo/redo belongs in
     *  requestlist or in bookinventory), and the command history.
     *  It returns true when the input is meant for requestlistparser.
     */
    public boolean parseInput (String[] string, String prev) {
        if (diceCoefficient(string[0], RequestCommand.COMMAND_WORD) > DICE_COEFFICIENT_ADJUSTED_THRESHOLD
                || diceCoefficient(string[0], DeleteRequestCommand.COMMAND_WORD) > DICE_COEFFICIENT_ADJUSTED_THRESHOLD
                || diceCoefficient(string[0], ToggleRequestCommand.COMMAND_WORD) > DICE_COEFFICIENT_ADJUSTED_THRESHOLD
                || ((diceCoefficient(string[0], UndoRequestCommand.COMMAND_WORD) > DICE_COEFFICIENT_THRESHOLD)
                && prev.equals(requestCommand))) {
            return true;
        } else {
            return false;
        }
    }
}
