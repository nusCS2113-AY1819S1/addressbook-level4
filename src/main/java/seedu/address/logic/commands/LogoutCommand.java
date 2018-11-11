package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.security.SecurityAuthenticationException;

/**
 * Lists all persons in the address book to the user.
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";
    public static final String COMMAND_WORD_ALIAS = "lo";

    public static final String MESSAGE_SUCCESS = "Successfully Logged Out";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws SecurityAuthenticationException {
        requireNonNull(model);

        if (model.getUser() == null) {
            throw new SecurityAuthenticationException();
        }

        model.commandLogout();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
