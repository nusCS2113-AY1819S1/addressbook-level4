package seedu.address.logic.commands.user;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.LoginInfoManager;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class UserCommand extends Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param loginInfoManager {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(LoginInfoManager loginInfoManager,
                                          CommandHistory history) throws CommandException;

}
