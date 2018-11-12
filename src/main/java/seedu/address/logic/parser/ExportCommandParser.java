package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.nio.file.Path;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author jitwei98
/**
 * Parses input arguments and creates a new {@code ExportCommand} object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Parses the given {@code args} of arguments in the context of the {@code ImportCommand}
     * and returns a {@code ImportCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ExportCommand parse(String args) throws ParseException {
        requireNonNull(args);

        try {
            Path filePath = ParserUtil.parseFilename(args);
            return new ExportCommand(filePath);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE), pe);
        }
    }
}
