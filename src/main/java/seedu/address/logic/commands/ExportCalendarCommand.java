package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.DateTimeManager;
import seedu.address.model.Model;
import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

/**
 * Use to export list of current user
 * registered events as a iCalendar file
 */
public class ExportCalendarCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "export current user registered event as an iCalender file\n"
            + "Example: export";

    public static final String MESSAGE_EXPORT_SUCCESS =
            "Your iCalendar file has been successfully exported";

    //Todo: Update when have a method to get the current logging in user
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        User currentUser = new User(new Username("John"), new Password("12345678"));
        DateTimeManager.exportICalenderFile(model, currentUser);
        return new CommandResult(MESSAGE_EXPORT_SUCCESS);
    }
}

