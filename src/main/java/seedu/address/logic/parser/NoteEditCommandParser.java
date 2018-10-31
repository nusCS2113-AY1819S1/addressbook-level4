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
        boolean startDateMissingErrorFound = false;

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
                messageErrors.append(ModuleCode.MESSAGE_MODULE_CODE_CONSTRAINT);
                messageErrors.append("\n");
            }
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
