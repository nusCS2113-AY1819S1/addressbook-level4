package seedu.address.logic.commands.EmailCommand;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Finally, send the email.
 */
public class EmailSendCommand extends EmailInitialiseCommand {
    public static final String MESSAGE_USAGE = "Sending...";
    public static final String EMAIL_SUCCESS = "Successfully sent the email!";
    public static final String EMAIL_FAILURE = "Failed to send the email!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        String result = "Send";
        return new CommandResult(result);
    }
}
