package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import seedu.address.logic.commands.AddLeaveCommand;
import seedu.address.model.leave.Leave;

//@@author Hafizuddin-NUS
/**
 * A utility class for Leave.
 */
public class LeaveUtil {
    /**
     * Returns an add leave command string for adding the {@code leave}.
     */
    public static String getAddLeaveCommand(Leave leave) {
        return AddLeaveCommand.COMMAND_WORD + " " + getLeaveDetails(leave);
    }

    /**
     * Returns the part of command string for the given {@code leave}'s details.
     */
    public static String getLeaveDetails(Leave leave) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DATE + leave.getDate().date);
        return sb.toString();
    }
}
