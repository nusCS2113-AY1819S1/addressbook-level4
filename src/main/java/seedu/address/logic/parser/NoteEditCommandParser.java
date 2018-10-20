package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_DATE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.NoteEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new NoteEditCommand object
 */
public class NoteEditCommandParser implements Parser<NoteEditCommand> {

    /**
     * Used to separate the index and optional parameters in the arguments.
     */
    private static final Pattern ARGS_FORMAT = Pattern.compile("^\\s*(?<index>\\S+)(?<optionalParams>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the NoteEditCommand
     * and returns a NoteEditCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public NoteEditCommand parse(String args) throws ParseException {
        Matcher matcher = ARGS_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    NoteEditCommand.MESSAGE_USAGE));
        }

        final String index = matcher.group("index");
        final String optionalParams = matcher.group("optionalParams");

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(optionalParams, PREFIX_MODULECODE, PREFIX_NOTE_DATE);

        if (!index.matches("\\d+") || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    NoteEditCommand.MESSAGE_USAGE));
        }

        String moduleCode = "";
        String date = "";
        if (argMultimap.getValue(PREFIX_MODULECODE).isPresent()) {
            moduleCode = argMultimap.getValue(PREFIX_MODULECODE).get();
        }

        if (argMultimap.getValue(PREFIX_NOTE_DATE).isPresent()) {
            date = argMultimap.getValue(PREFIX_NOTE_DATE).get();
        }

        return new NoteEditCommand(Integer.parseInt(index), moduleCode, date);
    }
}
