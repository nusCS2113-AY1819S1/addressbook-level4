package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ChangeTimeSlotCommand.MESSAGE_USAGE;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangeTimeSlotCommand;
import seedu.address.logic.commands.MergeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.enrolledClass.EnrolledClass;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class ChangeTimeSlotCommandParser implements Parser<ChangeTimeSlotCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeTimeSlotCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        String[] actions = trimmedArgs.split("\\s+");
        if (actions.length < 4) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangeTimeSlotCommand.MESSAGE_USAGE));
        }
        if (actions.length % 3 != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangeTimeSlotCommand.MESSAGE_USAGE));
        }
        Index index = Index.fromOneBased(Integer.parseInt(actions[0]));
        for (int i = 1; i < actions.length; i++) {
            if (i % 3 == 1) {
                if (isInvalidDay(actions[i])) {
                    throw new ParseException("Invalid Day. " + MESSAGE_USAGE);
                }
            }
            if (i % 3 == 2) {
                if (isInvalidTime(actions[i])) {
                    throw new ParseException("Invalid Time. " + MESSAGE_USAGE);
                }
            }
        }

        return new ChangeTimeSlotCommand(index, actions);
    }

    Boolean isInvalidDay(String day) {
        if (!day.equalsIgnoreCase("mon") && !day.equalsIgnoreCase("tue") &&
                !day.equalsIgnoreCase("wed") && !day.equalsIgnoreCase("thu") &&
                !day.equalsIgnoreCase("fri")) {
            return true;
        } else {
            return false;
        }
    }

    Boolean isInvalidTime(String time) {
        if (!time.equalsIgnoreCase("8am") && !time.equalsIgnoreCase("9am")
                && !time.equalsIgnoreCase("10am")
                && !time.equalsIgnoreCase("11am")
                && !time.equalsIgnoreCase("12am")
                && !time.equalsIgnoreCase("1pm")
                && !time.equalsIgnoreCase("2pm")
                && !time.equalsIgnoreCase("3pm")
                && !time.equalsIgnoreCase("4pm")
                && !time.equalsIgnoreCase("5pm")
                && !time.equalsIgnoreCase("6pm")
                && !time.equalsIgnoreCase("7pm")) {
            return true;
        } else {
            return false;
        }
    }
}


