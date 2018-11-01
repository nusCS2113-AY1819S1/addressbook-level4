package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ExportAllCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Filetype;

//@@author jitwei98
/**
 * Parses input arguments and creates a new {@code ExportAllCommand} object
 */
public class ExportAllCommandParser implements Parser<ExportAllCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code ExportAllCommand}
     * and returns a {@code ExportAllCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ExportAllCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput);

        Filetype filetype;
        try {
            filetype = ParserUtil.parseFiletype(argMultimap.getPreamble());
        } catch (Exception pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ExportAllCommand.MESSAGE_USAGE), pe);
        }

        return new ExportAllCommand(filetype);
    }

}
