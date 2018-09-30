package seedu.address.logic.commands.exceptions;

import
import seedu.address.logic.commands.Command;

public class LimitCommand extends Command {
    public static final String COMMAND_WORD= "limit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Set the expense limit for a period of time\n"
            + "Parameters: Start_date(dd-mm-yyyy) End_date_(dd-mm-yyyy)\n"
            + "Example: " + COMMAND_WORD + "01-09-2018 ";

    public static final String Limit_Set = "Limit has been set: %1$s";

}
