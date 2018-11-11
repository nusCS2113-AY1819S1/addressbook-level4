package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ExportCalendarCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser for export command
 */
public class ExportCalendarCommandParser implements Parser<ExportCalendarCommand> {
    private static final String SPECIAL_CHARACTERS = "!#$%&'+=~^.@-";
    private static final String FILE_NAME_VALIDATION_REGEX = "^[\\w" + SPECIAL_CHARACTERS + "]+";

    /**
     * Parse the given {@code arguments} of Export Command
     * and return an ExportCalendar object for executions
     * @throws ParseException if arguments is invalid
     */
    public ExportCalendarCommand parse(String args) throws ParseException {
        String filename = args.trim();

        if (filename.isEmpty() || filename.length() > 255 || !filename.matches(FILE_NAME_VALIDATION_REGEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            ExportCalendarCommand.MESSAGE_USAGE));
        }

        return new ExportCalendarCommand(filename);
    }
}
