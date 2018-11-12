package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EQUIPMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTRUCTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.stream.Stream;

import seedu.address.logic.commands.TrackCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.workout.Calories;
import seedu.address.model.workout.Difficulty;
import seedu.address.model.workout.Duration;
import seedu.address.model.workout.Equipment;
import seedu.address.model.workout.Instruction;
import seedu.address.model.workout.Muscle;
import seedu.address.model.workout.Name;
import seedu.address.model.workout.Parameter;
import seedu.address.model.workout.Remark;
import seedu.address.model.workout.Type;

/**
 * Parses input arguments and creates a new TrackCommand object
 */
public class TrackCommandParser implements Parser<TrackCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code TrackCommand}
     * and returns a {@code TrackCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    @Override
    public TrackCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TYPE, PREFIX_DURATION, PREFIX_DIFFICULTY,
                        PREFIX_EQUIPMENT, PREFIX_MUSCLE, PREFIX_CALORIES, PREFIX_INSTRUCTION, PREFIX_TAG,
                        PREFIX_REMARK);

        // check 1: no subcommand
        // check 2: no prefixes present
        // check 3: more than one prefix present
        if (argMultimap.getPreamble().isEmpty()
                || !areAnyPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_TYPE, PREFIX_DURATION, PREFIX_DIFFICULTY,
                PREFIX_EQUIPMENT, PREFIX_MUSCLE, PREFIX_CALORIES, PREFIX_INSTRUCTION, PREFIX_TAG, PREFIX_REMARK)
                || areMultipleParametersPresent(argMultimap)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrackCommand.MESSAGE_USAGE));
        }

        String subcommand = ParserUtil.parseSubcommand(argMultimap.getPreamble());
        Prefix prefix = argMultimap.getTheOnlyPrefix();
        String value = null;
        if (prefix.equals(PREFIX_NAME)) {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            value = name.fullName;
        } else if (prefix.equals(PREFIX_TYPE)) {
            Type type = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
            value = type.fullType;
        } else if (prefix.equals(PREFIX_DURATION)) {
            Duration duration = ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get());
            value = duration.fullDuration;
        } else if (prefix.equals(PREFIX_DIFFICULTY)) {
            Difficulty difficulty = ParserUtil.parseDifficulty(argMultimap.getValue(PREFIX_DIFFICULTY).get());
            value = difficulty.fullDifficulty;
        } else if (prefix.equals(PREFIX_EQUIPMENT)) {
            Equipment equipment = ParserUtil.parseEquipment(argMultimap.getValue(PREFIX_EQUIPMENT).get());
            value = equipment.fullEquipment;
        } else if (prefix.equals(PREFIX_MUSCLE)) {
            Muscle muscle = ParserUtil.parseMuscle(argMultimap.getValue(PREFIX_MUSCLE).get());
            value = muscle.fullMuscle;
        } else if (prefix.equals(PREFIX_CALORIES)) {
            Calories calories = ParserUtil.parseCalories(argMultimap.getValue(PREFIX_CALORIES).get());
            value = calories.fullCalories;
        } else if (prefix.equals(PREFIX_INSTRUCTION)) {
            Instruction instruction = ParserUtil.parseInstruction(argMultimap.getValue(PREFIX_INSTRUCTION).get());
            value = instruction.fullInstruction;
        } else if (prefix.equals(PREFIX_TAG)) {
            Tag tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
            value = tag.tagName;
        } else if (prefix.equals(PREFIX_REMARK)) {
            Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get());
            value = remark.fullRemark;
        }

        // check that the value is one string/value with no spaces
        if (value.split("\\s+").length > 1) {
            throw new ParseException(TrackCommand.MESSAGE_VALUE_CONSTRAINTS);
        }

        Parameter parameter = new Parameter(prefix, value);

        return new TrackCommand(subcommand, parameter);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if there is only one parameter in the given {@code ArgumentMultimap}.
     */
    private static boolean areMultipleParametersPresent(ArgumentMultimap argumentMultimap) {
        return (argumentMultimap.getNumberOfArgs() > 2);
    }
}
