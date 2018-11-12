package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EQUIPMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.Stream;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.workout.DurationPredicate;
import seedu.address.model.workout.EquipmentPredicate;
import seedu.address.model.workout.TypePredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_DURATION,
                        PREFIX_EQUIPMENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_TYPE, PREFIX_DURATION,
                PREFIX_EQUIPMENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));

        }

        String trimmedArgs = args.trim();

        String[] parameters = trimmedArgs.split("\\s+");

        String typeKeywords = argMultimap.getValue(PREFIX_TYPE).get();
        String durationKeywords = argMultimap.getValue(PREFIX_DURATION).get();
        String equipmentKeywords = argMultimap.getValue(PREFIX_EQUIPMENT).get();

        StringTokenizer tk = new StringTokenizer(typeKeywords);
        StringTokenizer dk = new StringTokenizer(durationKeywords);
        StringTokenizer ek = new StringTokenizer(equipmentKeywords);

        return new FilterCommand(
                new DurationPredicate(Arrays.asList(dk.nextToken(" "))),
                new TypePredicate(Arrays.asList(tk.nextToken(" "))),
                new EquipmentPredicate(Arrays.asList(ek.nextToken(" "))));

    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
