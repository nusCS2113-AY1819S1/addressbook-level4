package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Filetype;

//@@author jitwei98
/**
 * Parses input arguments and creates a new {@code ExportCommand} object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    private static final int MINIMUM_ARGS_LENGTH = 5;
    private static final int LENGTH_OF_FILETYPE = 3;

    /**
     * Parses the given {@code String} of arguments in the context of the {@code ExportCommand}
     * and returns a {@code ExportCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ExportCommand parse(String args) throws ParseException {
        requireNonNull(args);

        if (args.length() < MINIMUM_ARGS_LENGTH) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(this.getIndexFrom(args));
            Filetype filetype = ParserUtil.parseFiletype(this.getFiletypeFrom(args));
            return new ExportCommand(index, filetype);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE), pe);
        }
    }

    private String getIndexFrom(String args) {
        return args.substring(0, args.length() - LENGTH_OF_FILETYPE);
    }

    private String getFiletypeFrom(String args) {
        return args.substring(args.length() - LENGTH_OF_FILETYPE);
    }

}
