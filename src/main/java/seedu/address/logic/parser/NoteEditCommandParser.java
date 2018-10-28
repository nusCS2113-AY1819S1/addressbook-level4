package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_TITLE;

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
                ArgumentTokenizer.tokenize(optionalParams,
                        PREFIX_MODULE_CODE,
                        PREFIX_NOTE_TITLE,
                        PREFIX_NOTE_START_DATE,
                        PREFIX_NOTE_START_TIME,
                        PREFIX_NOTE_END_DATE,
                        PREFIX_NOTE_END_TIME,
                        PREFIX_NOTE_LOCATION);

        if (!index.matches("\\d+") || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    NoteEditCommand.MESSAGE_USAGE));
        }

        String moduleCode = "";
        String title = "";
        String startDate = "";
        String startTime = "";
        String endDate = "";
        String endTime = "";
        String location = "";

        if (argMultimap.getValue(PREFIX_MODULE_CODE).isPresent()) {
            // TODO: Check validity of input moduleCode value
            moduleCode = argMultimap.getValue(PREFIX_MODULE_CODE).get();
        }

        if (argMultimap.getValue(PREFIX_NOTE_TITLE).isPresent()) {
            title = argMultimap.getValue(PREFIX_NOTE_TITLE).get();
        }

        if (argMultimap.getValue(PREFIX_NOTE_START_DATE).isPresent()) {
            // TODO: Check validity of input startDate value
            startDate = argMultimap.getValue(PREFIX_NOTE_START_DATE).get();
        }

        if (argMultimap.getValue(PREFIX_NOTE_START_TIME).isPresent()) {
            // TODO: Check validity of input startTime value
            startTime = argMultimap.getValue(PREFIX_NOTE_START_TIME).get();
        }

        if (argMultimap.getValue(PREFIX_NOTE_END_DATE).isPresent()) {
            // TODO: Check validity of input endDate value
            endDate = argMultimap.getValue(PREFIX_NOTE_END_DATE).get();
        }

        if (argMultimap.getValue(PREFIX_NOTE_END_TIME).isPresent()) {
            // TODO: Check validity of input endTime value
            endTime = argMultimap.getValue(PREFIX_NOTE_END_TIME).get();
        }

        if (argMultimap.getValue(PREFIX_NOTE_LOCATION).isPresent()) {
            location = argMultimap.getValue(PREFIX_NOTE_LOCATION).get();
        }

        return new NoteEditCommand(
                Integer.parseInt(index),
                moduleCode,
                title,
                startDate,
                startTime,
                endDate,
                endTime,
                location);
    }
}
