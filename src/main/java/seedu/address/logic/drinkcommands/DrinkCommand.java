package seedu.address.logic.drinkcommands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.drinkcommands.exceptions.DrinkCommandException;
import seedu.address.model.DrinkModel;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class DrinkCommand {
    /**
     * Executes the command and returns the result message.
     *
     * @param model   {@code DrinkModel} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws DrinkCommandException If an error occurs during command execution.
     */
    public abstract DrinkCommandResult execute(DrinkModel model, CommandHistory history) throws DrinkCommandException;

}
