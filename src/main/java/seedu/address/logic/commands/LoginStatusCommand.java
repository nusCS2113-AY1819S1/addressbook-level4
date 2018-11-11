package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Logs out account from Stock List.
 */

public class LoginStatusCommand extends Command {

    public static final String COMMAND_WORD = "loginStatus";
    public static final String MESSAGE_SUCCESS = "Logged in as: %1$s";
    public static final String MESSAGE_NOT_LOGGED_IN = "Not logged in";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_NOT_LOGGED_IN);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, model.getLoggedInUser()));
    }
}
