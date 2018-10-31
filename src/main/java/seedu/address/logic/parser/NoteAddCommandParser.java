package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_BLANK_FIELD;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TIME_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_TITLE;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.logic.commands.NoteAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.note.Note;
import seedu.address.model.note.NoteDate;
import seedu.address.model.note.NoteLocation;
import seedu.address.model.note.NoteText;
import seedu.address.model.note.NoteTime;
import seedu.address.model.note.NoteTitle;


/**
 * Parses input arguments and creates a new NoteAddCommand object
 */
public class NoteAddCommandParser implements Parser<NoteAddCommand> {

    public static final String MESSAGE_ERROR_IN_PARSING_FOUND =
            "Invalid input! Trajectory found the following error(s).";
    public static final String MESSAGE_INVALID_DATE_TIME_DIFFERENCE =
            "Invalid input! Please make sure the start date/time is earlier than the end date/time.";

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

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteAddCommand.MESSAGE_USAGE));
        }

        ModuleCode moduleCode = null;

        NoteTitle title = new NoteTitle("");
        NoteDate startDate = null;
        NoteTime startTime = new NoteTime(NoteTime.DEFAULT_START_TIME.format(NoteTime.TIME_FORMAT));
        NoteDate endDate = null;
        NoteTime endTime = new NoteTime(NoteTime.DEFAULT_END_TIME.format(NoteTime.TIME_FORMAT));
        NoteLocation location = new NoteLocation("");
        NoteText noteText = new NoteText("");

        StringBuilder messageErrors = new StringBuilder();
        boolean dateErrorFound = false;
        boolean timeErrorFound = false;
        boolean startDateMissingErrorFound = false;

        // ModuleManager moduleManager = ModuleManager.getInstance();

        try {
            moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE).get());

            /* Disabled for standalone testing
            if (!moduleManager.doesModuleExist(moduleCode.toString())) {
                messageErrors.append(
                        String.format(NoteAddCommand.MESSAGE_MODULE_CODE_DOES_NOT_EXIST, moduleCode.toString()));
                messageErrors.append("\n");
            }
            */
        } catch (ParseException e) {
            messageErrors.append(ModuleCode.MESSAGE_MODULE_CODE_CONSTRAINT);
            messageErrors.append("\n");
        }

        if (argMultimap.getValue(PREFIX_NOTE_TITLE).isPresent()) {
            String trimmedTitle = argMultimap.getValue(PREFIX_NOTE_TITLE).get().trim();
            if (trimmedTitle.isEmpty()) {
                throw new ParseException(MESSAGE_BLANK_FIELD);
            }

            if (!NoteTitle.isValidTitle(trimmedTitle)) {
                messageErrors.append(NoteTitle.MESSAGE_TITLE_EXCEED_MAX_CHAR_COUNT);
                messageErrors.append("\n");
            } else {
                title = new NoteTitle(trimmedTitle);
            }
        }

        if (argMultimap.getValue(PREFIX_NOTE_START_DATE).isPresent()) {
            if (argMultimap.getValue(PREFIX_NOTE_START_DATE).get().trim().isEmpty()) {
                throw new ParseException(MESSAGE_BLANK_FIELD);
            }

            try {
                startDate = ParserUtil.parseNoteDate(argMultimap.getValue(PREFIX_NOTE_START_DATE).get());
            } catch (ParseException e) {
                messageErrors.append(MESSAGE_INVALID_DATE_FORMAT);
                messageErrors.append("\n");
                dateErrorFound = true;
            }
        }

        if (argMultimap.getValue(PREFIX_NOTE_START_TIME).isPresent()) {
            if (argMultimap.getValue(PREFIX_NOTE_START_TIME).get().trim().isEmpty()) {
                throw new ParseException(MESSAGE_BLANK_FIELD);
            }

            try {
                startTime = ParserUtil.parseNoteTime(argMultimap.getValue(PREFIX_NOTE_START_TIME).get());
            } catch (ParseException e) {
                messageErrors.append(MESSAGE_INVALID_TIME_FORMAT);
                messageErrors.append("\n");
                timeErrorFound = true;
            }

            if (startDate == null) {
                messageErrors.append(NoteDate.MESSAGE_START_DATE_MISSING_FIELD);
                messageErrors.append("\n");
                startDateMissingErrorFound = true;
            }
        }

        if (argMultimap.getValue(PREFIX_NOTE_END_DATE).isPresent()) {
            if (argMultimap.getValue(PREFIX_NOTE_END_DATE).get().trim().isEmpty()) {
                throw new ParseException(MESSAGE_BLANK_FIELD);
            }

            try {
                endDate = ParserUtil.parseNoteDate(argMultimap.getValue(PREFIX_NOTE_END_DATE).get());
            } catch (ParseException e) {
                if (!dateErrorFound) {
                    messageErrors.append(MESSAGE_INVALID_DATE_FORMAT);
                    messageErrors.append("\n");
                }
            }

            if (startDate == null) {
                if (!startDateMissingErrorFound) {
                    messageErrors.append(NoteDate.MESSAGE_START_DATE_MISSING_FIELD);
                    messageErrors.append("\n");
                    startDateMissingErrorFound = true;
                }
            }
        }

        if (argMultimap.getValue(PREFIX_NOTE_END_TIME).isPresent()) {
            if (argMultimap.getValue(PREFIX_NOTE_END_TIME).get().trim().isEmpty()) {
                throw new ParseException(MESSAGE_BLANK_FIELD);
            }

            try {
                endTime = ParserUtil.parseNoteTime(argMultimap.getValue(PREFIX_NOTE_END_TIME).get());
            } catch (ParseException e) {
                if (!timeErrorFound) {
                    messageErrors.append(MESSAGE_INVALID_TIME_FORMAT);
                    messageErrors.append("\n");
                }
            }

            if (startDate == null) {
                if (!startDateMissingErrorFound) {
                    messageErrors.append(NoteDate.MESSAGE_START_DATE_MISSING_FIELD);
                    messageErrors.append("\n");
                }
            }
        }

        if (argMultimap.getValue(PREFIX_NOTE_LOCATION).isPresent()) {
            String trimmedLocation = argMultimap.getValue(PREFIX_NOTE_LOCATION).get().trim();
            if (trimmedLocation.isEmpty()) {
                throw new ParseException(MESSAGE_BLANK_FIELD);
            }

            if (!NoteLocation.isValidLocation(trimmedLocation)) {
                messageErrors.append(NoteLocation.MESSAGE_LOCATION_EXCEED_MAX_CHAR_COUNT);
                messageErrors.append("\n");
            } else {
                location = new NoteLocation(trimmedLocation);
            }
        }

        if (messageErrors.length() > 0) {
            throw new ParseException(messageErrors.toString());
        }

        if (startDate != null && endDate == null) {
            endDate = new NoteDate(startDate.getDate().format(NoteDate.DATE_FORMAT));
        }

        if (startDate != null) {
            LocalDateTime start = LocalDateTime.of(startDate.getDate(), startTime.getTime());
            LocalDateTime end = LocalDateTime.of(endDate.getDate(), endTime.getTime());

            // result = 0, equal, valid
            // result > 0, end > start, valid
            // result < 0, end < start, invalid
            int result = end.compareTo(start);
            if (result < 0) {
                throw new ParseException(MESSAGE_INVALID_DATE_TIME_DIFFERENCE);
            }
        }

        Note note = new Note(
                moduleCode,
                title,
                startDate,
                startTime,
                endDate,
                endTime,
                location,
                noteText);

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
