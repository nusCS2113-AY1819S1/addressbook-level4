package seedu.address.logic.commands;

import seedu.address.model.record.Date;


//TODO: will do later
public class FindDateCommand extends Command {
    public static final String COMMAND_WORD = "find_date";

    public static final String MESSAGE_USAGE = COMMAND_WORD +
            ": Finds all records whose contains date typed and displays them as a list with index numbers.\n"
            + "Parameters: DATE\n"
            + "Example: " + COMMAND_WORD + " 31/03/1999";

}
