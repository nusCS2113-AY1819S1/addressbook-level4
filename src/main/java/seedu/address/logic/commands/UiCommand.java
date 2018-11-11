package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.events.ui.ShowLoginEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.security.SecurityAuthenticationException;

/**
 * Lists all persons in the address book to the user.
 */
public class UiCommand extends Command {

    public static final String COMMAND_WORD = "ui";

    public static final String MESSAGE_SUCCESS = "Successfully Displayed UI";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws SecurityAuthenticationException {
        requireNonNull(model);

        raise(new ShowLoginEvent());

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
