package seedu.address.request.requestcommands;

import seedu.address.commons.core.ComponentManager;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.request.requestmodel.RequestModel;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 * ComponentManager is extended as events may be raised.
 */
public abstract class CommandSecondary extends ComponentManager {

    /**
     * Executes the command and returns the result message.
     *
     * @param requestModel {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(RequestModel requestModel, CommandHistory history) throws CommandException;
}
