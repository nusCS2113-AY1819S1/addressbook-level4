package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Logs out account from Stock List.
 */

public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";
    public static final String MESSAGE_SUCCESS = "Logged out: %1$s";
    public static final String MESSAGE_ALREADY_LOGGED_OUT = "Already logged out";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_ALREADY_LOGGED_OUT);
        }

        String loggedOutUser = model.getLoggedInUser();
        model.setLoggedOutStatus();
        return new CommandResult(String.format(MESSAGE_SUCCESS, loggedOutUser));
    }

}
