package seedu.address.logic.drinkcommands.user;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.drinkcommands.DrinkCommand;
import seedu.address.logic.drinkcommands.DrinkCommandResult;
import seedu.address.logic.drinkcommands.exceptions.DrinkCommandException;
import seedu.address.model.LoginInfoManager;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class UserCommand extends DrinkCommand {

    /**
     * Executes the command and returns the result message.
     *
     * @param loginInfoManager {@code Model} which the command should operate on.
     * @param history          {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract DrinkCommandResult execute(LoginInfoManager loginInfoManager,
                                               CommandHistory history) throws DrinkCommandException;

}
