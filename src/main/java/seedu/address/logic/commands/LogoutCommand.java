package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.user.UserManager;
import seedu.address.ui.HtmlTableProcessor;

/**
 * Lists all courses within Trajectory.
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "lock";
    public static final String MESSAGE_SUCCESS = "You have locked the system.";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        UserManager.getInstance().logout();
        return new CommandResult(MESSAGE_SUCCESS + "\n"
                + "", HtmlTableProcessor.renderCard(MESSAGE_SUCCESS));
    }
}
