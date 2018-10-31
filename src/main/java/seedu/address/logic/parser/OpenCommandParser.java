package seedu.address.logic.parser;

import java.io.File;

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
        String fileName = ParserUtil.parseFileName(args);
        fileName = fileName + ".xml";
        if ((".xml").equals(fileName)) {
            throw new ParseException(
                    String.format(OpenCommand.MESSAGE_EMPTY_FILE_NAME, OpenCommand.MESSAGE_USAGE)
            );
        } else if (!isValidFile(fileName)) {
            throw new ParseException(
                    String.format(OpenCommand.MESSAGE_FILE_NOT_EXIST, OpenCommand.MESSAGE_USAGE)
            );
        }
        return new OpenCommand(fileName);
    }

    /**
     * Checks if file with given {@code fileName} exists in versions folder
     * @param fileName
     * @return
     */
    public boolean isValidFile(String fileName) {
        File tempFile = new File("src/main/resources/docs/versions/" + fileName);
        return tempFile.isFile();
    }
}
