package seedu.recruit.logic.commands.EmailCommand;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.Command;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;

/**
 * Finally, send the email.
 */
public class EmailSendCommand extends Command {
    public static final String MESSAGE_USAGE = "Sending...";
    public static final String EMAIL_SUCCESS = "Successfully sent the email!";
    public static final String EMAIL_FAILURE = "Failed to send the email!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        String result = "Send";
        return new CommandResult(result);
    }
}
