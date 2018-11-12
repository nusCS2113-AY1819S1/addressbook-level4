package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.session.SessionManager;

//@@author jylee-git
/**
 * Returns the log in status to the user.
 * Also returns the current logged in NRIC if the app's logged in.
 */
public class CheckLoginStatusCommand extends Command {
    public static final String COMMAND_WORD = "checkloginstatus";
    public static final String STATUS_LOGGED_IN = "You are logged in as: %s";
    public static final String STATUS_NOT_LOGGED_IN = "You are not logged in.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        SessionManager sessionManager = SessionManager.getInstance(model);
        if (!sessionManager.isLoggedIn()) {
            return new CommandResult(STATUS_NOT_LOGGED_IN);
        } else {
            return new CommandResult(String.format(STATUS_LOGGED_IN,
                    sessionManager.getLoggedInSessionNric()));
        }
    }
}
