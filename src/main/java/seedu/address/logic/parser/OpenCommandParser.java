package seedu.address.logic.parser;

import seedu.address.logic.commands.OpenCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new OpenCommand object
 */
public class OpenCommandParser implements Parser<OpenCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public OpenCommand parse(String args) throws ParseException {
        try {
            String fileName = ParserUtil.parseFileName(args);
            fileName = fileName + ".xml";
            return new OpenCommand(fileName);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(OpenCommand.MESSAGE_INVALID_FILE_NAME, OpenCommand.MESSAGE_USAGE), pe);
        }
    }
}
