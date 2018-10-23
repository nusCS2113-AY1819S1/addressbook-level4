package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ExportCalendarCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ExportCalendarCommandParser {

    public static final String FILENAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    /**
     * Parse the given {@code arguments} of Export Command
     */
    public ExportCalendarCommand parse(String args) throws ParseException {
        String filename = args.trim();
        if (filename.isEmpty() && !filename.matches(FILENAME_VALIDATION_REGEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            ExportCalendarCommand.MESSAGE_USAGE));
        }

        return new ExportCalendarCommand(filename);
    }
}
