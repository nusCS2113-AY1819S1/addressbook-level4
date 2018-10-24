package seedu.address.logic.parser;

import seedu.address.logic.commands.SaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SaveCommandParser implements Parser<SaveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SaveCommand parse(String args) throws ParseException {
        try {
            String fileName = ParserUtil.parseFileName(args);
            fileName = fileName + ".xml";
            return new SaveCommand(fileName);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(SaveCommand.MESSAGE_INVALID_FILE_NAME, SaveCommand.MESSAGE_USAGE), pe);
        }
    }
}
