package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_BLANK_FIELD;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DAY_OF_MONTH;
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
import seedu.address.model.module.ModuleCode;
import seedu.address.model.note.NoteDate;
import seedu.address.model.note.NoteLocation;
import seedu.address.model.note.NoteTime;
import seedu.address.model.note.NoteTitle;

/**
 * Parses input arguments and creates a new NoteEditCommand object
 */
public class NoteEditCommandParser implements Parser<NoteEditCommand> {
    /**
     * Used to separate the index and optional parameters in the arguments.
     */
    private static final Pattern ARGS_FORMAT = Pattern.compile("^\\s*(?<index>\\S+)(?<optionalParams>.*)");

    private static final String MESSAGE_ERROR_IN_PARSING_FOUND =
            "Invalid input! Trajectory found the following error(s).";

    private static final String DOUBLE_NEW_LINE_SEPARATOR = "\n\n";

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

        ModuleCode moduleCode = null;
        NoteTitle title = null;
        NoteDate startDate = null;
        NoteTime startTime = null;
        NoteDate endDate = null;
        NoteTime endTime = null;
        NoteLocation location = null;

        StringBuilder messageErrors = new StringBuilder();
        boolean dateErrorFound = false;
        boolean timeErrorFound = false;

        // ModuleManager moduleManager = ModuleManager.getInstance();

        if (argMultimap.getValue(PREFIX_MODULE_CODE).isPresent()) {
            if (argMultimap.getValue(PREFIX_MODULE_CODE).get().trim().isEmpty()) {
                throw new ParseException(MESSAGE_BLANK_FIELD);
            }

            try {
                moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE).get());

                /* Disabled for standalone testing
                if (!moduleManager.doesModuleExist(moduleCode.toString())) {
                    messageErrors.append(
                            String.format(NoteAddCommand.MESSAGE_MODULE_CODE_DOES_NOT_EXIST, moduleCode.toString()));
                    messageErrors.append("\n");
                } */
            } catch (ParseException e) {
                messageErrors.append(e.getMessage());
                messageErrors.append(DOUBLE_NEW_LINE_SEPARATOR);
            }
        }

        if (argMultimap.getValue(PREFIX_NOTE_TITLE).isPresent()) {
            if (argMultimap.getValue(PREFIX_NOTE_TITLE).get().trim().isEmpty()) {
                throw new ParseException(MESSAGE_BLANK_FIELD);
            }

            try {
                title = ParserUtil.parseNoteTitle(argMultimap.getValue(PREFIX_NOTE_TITLE).get());
            } catch (ParseException e) {
                messageErrors.append(e.getMessage());
                messageErrors.append(DOUBLE_NEW_LINE_SEPARATOR);
            }
        }

        int startMonth = -1;
        int startYear = -1;
        if (argMultimap.getValue(PREFIX_NOTE_START_DATE).isPresent()) {
            if (argMultimap.getValue(PREFIX_NOTE_START_DATE).get().trim().isEmpty()) {
                throw new ParseException(MESSAGE_BLANK_FIELD);
            }

            try {
                startDate = ParserUtil.parseNoteDate(argMultimap.getValue(PREFIX_NOTE_START_DATE).get());

                if (!NoteDate.isValidDayOfMonth(argMultimap.getValue(PREFIX_NOTE_START_DATE).get(),
                        startDate.getDate().lengthOfMonth())) {

                    messageErrors.append(String.format(
                            MESSAGE_INVALID_DAY_OF_MONTH,
                            startDate.getDate().getMonth(),
                            startDate.getDate().getYear(),
                            startDate.getDate().lengthOfMonth()
                    ));
                    messageErrors.append(DOUBLE_NEW_LINE_SEPARATOR);

                    startMonth = startDate.getDate().getMonthValue();
                    startYear = startDate.getDate().getYear();

                    dateErrorFound = true;
                    startDate = null;
                }
            } catch (ParseException e) {
                messageErrors.append(e.getMessage());
                messageErrors.append(DOUBLE_NEW_LINE_SEPARATOR);
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
                messageErrors.append(e.getMessage());
                messageErrors.append(DOUBLE_NEW_LINE_SEPARATOR);
                timeErrorFound = true;
            }
        }

        if (argMultimap.getValue(PREFIX_NOTE_END_DATE).isPresent()) {
            if (argMultimap.getValue(PREFIX_NOTE_END_DATE).get().trim().isEmpty()) {
                throw new ParseException(MESSAGE_BLANK_FIELD);
            }

            try {
                endDate = ParserUtil.parseNoteDate(argMultimap.getValue(PREFIX_NOTE_END_DATE).get());

                if (!NoteDate.isValidDayOfMonth(argMultimap.getValue(PREFIX_NOTE_END_DATE).get(),
                        endDate.getDate().lengthOfMonth())) {

                    if (startMonth == -1
                            || (endDate.getDate().getMonthValue() != startMonth
                            || endDate.getDate().getYear() != startYear)) {

                        messageErrors.append(String.format(
                                MESSAGE_INVALID_DAY_OF_MONTH,
                                endDate.getDate().getMonth(),
                                endDate.getDate().getYear(),
                                endDate.getDate().lengthOfMonth()
                        ));
                        messageErrors.append(DOUBLE_NEW_LINE_SEPARATOR);
                    }
                    endDate = null;
                }
            } catch (ParseException e) {
                if (!dateErrorFound) {
                    messageErrors.append(e.getMessage());
                    messageErrors.append(DOUBLE_NEW_LINE_SEPARATOR);
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
                    messageErrors.append(e.getMessage());
                    messageErrors.append(DOUBLE_NEW_LINE_SEPARATOR);
                }
            }
        }

        if (argMultimap.getValue(PREFIX_NOTE_LOCATION).isPresent()) {
            if (argMultimap.getValue(PREFIX_NOTE_LOCATION).get().trim().isEmpty()) {
                throw new ParseException(MESSAGE_BLANK_FIELD);
            }

            try {
                location = ParserUtil.parseNoteLocation(argMultimap.getValue(PREFIX_NOTE_LOCATION).get());
            } catch (ParseException e) {
                messageErrors.append(e.getMessage());
                messageErrors.append(DOUBLE_NEW_LINE_SEPARATOR);
            }
        }

        if (messageErrors.length() > 0) {
            throw new ParseException(MESSAGE_ERROR_IN_PARSING_FOUND + DOUBLE_NEW_LINE_SEPARATOR
                    + messageErrors.toString().substring(0, messageErrors.length() - 1));
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
