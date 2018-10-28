package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_FILE_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.NoteExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new NoteExportCommand object
 */
public class NoteExportCommandParser implements Parser<NoteExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NoteExportCommand
     * and returns a NoteExportCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public NoteExportCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NOTE_FILE_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NOTE_FILE_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteExportCommand.MESSAGE_USAGE));
        }

        String fileName = argMultimap.getValue(PREFIX_NOTE_FILE_NAME).get();

        if (!fileName.matches("[ a-zA-Z0-9_\\-]+")) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, NoteExportCommand.MESSAGE_INVALID_FILE_NAME));
        }

        return new NoteExportCommand(fileName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
