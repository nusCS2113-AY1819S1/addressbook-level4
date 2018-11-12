package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.session.SessionManager;

//@@author jylee-git
/**
 * Logs the user out of the application.
 */
public class LogoutCommand extends Command {
    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_SUCCESS = "You have successfully logged out.";
    public static final String NOT_LOGGED_IN = "Unable to perform action; you are not logged in!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        SessionManager sessionManager = SessionManager.getInstance(model);
        if (!sessionManager.isLoggedIn()) {
            throw new CommandException(NOT_LOGGED_IN);
        } else {
            sessionManager.logOutSession();
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
}
