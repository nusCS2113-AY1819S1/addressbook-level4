package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_TITLE;

import java.util.stream.Stream;

import seedu.address.logic.commands.NoteAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.note.Note;


/**
 * Parses input arguments and creates a new NoteAddCommand object
 */
public class NoteAddCommandParser implements Parser<NoteAddCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the NoteAddCommand
     * and returns a NoteAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public NoteAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_MODULE_CODE,
                        PREFIX_NOTE_TITLE,
                        PREFIX_NOTE_START_DATE,
                        PREFIX_NOTE_START_TIME,
                        PREFIX_NOTE_END_DATE,
                        PREFIX_NOTE_END_TIME,
                        PREFIX_NOTE_LOCATION);

        String moduleCode = "";
        String title = "";
        String startDate = "";
        String startTime = "";
        String endDate = "";
        String endTime = "";
        String location = "";

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    NoteAddCommand.MESSAGE_USAGE));
        }

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

        // TODO: Validate date & time difference for 'start' and 'end' here

        Note note = new Note(
                moduleCode,
                title,
                startDate,
                startTime,
                endDate,
                endTime,
                location,
                "");

        return new NoteAddCommand(note);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
