package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.DateTimeUtil;
import seedu.address.model.Model;
import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

/**
 * Use to export list of current user
 * registered events as a iCalendar file
 */
public class ExportCalendarCommand extends Command {

    private final String fileName;

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "filename"
            + "export current user registered event as an iCalender file\n"
            + "filename should be alpha numerical\n"
            + "Example: export myCalendar";

    public static final String MESSAGE_EXPORT_SUCCESS =
            "Your %1$s iCalendar file has been successfully exported";

    public static final String MESSAGE_FILE_ERROR = "File with %1$s.ics has existed in other folder\n"
            + "or file has errors and cannot be opened";

    public ExportCalendarCommand(String filename) {
        fileName = filename;
    }

    //Todo: Update when have a method to get the current logging in user
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        User currentUser = new User(new Username("John"), new Password("12345678"));
        try {
            DateTimeUtil.exportICalenderFile(model, currentUser, fileName);
        } catch (IOException e) {
            return new CommandResult(String.format(MESSAGE_FILE_ERROR,fileName));
        }

        return new CommandResult(String.format(MESSAGE_EXPORT_SUCCESS,fileName));
    }
}

