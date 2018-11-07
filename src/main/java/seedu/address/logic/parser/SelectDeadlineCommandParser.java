package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.stream.Stream;

import seedu.address.logic.commands.SelectDeadlineCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Deadline;

//@@author emobeany
/**
 * Parses input arguments and creates a new SelectDeadlineCommand object
 */
public class SelectDeadlineCommandParser implements Parser<SelectDeadlineCommand> {
    @Override
    public SelectDeadlineCommand parse(String userInput) throws ParseException {

        Deadline deadlineWithoutPrefixes = parseWithoutPrefixes(userInput);
        if (deadlineWithoutPrefixes != null) {
            return new SelectDeadlineCommand(deadlineWithoutPrefixes);
        }
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR);
        // shows correct error message when illegal character is used in dd/mm/yyyy format
        if (noPrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR)) {
            ParserUtil.parseDeadline(userInput);
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_MONTH)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SelectDeadlineCommand.MESSAGE_USAGE));
        }

        String day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).orElse(""));
        String month = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_MONTH).orElse(""));
        String year;
        if (argMultimap.getValue(PREFIX_YEAR).isPresent()) {
            year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());
        } else {
            Deadline deadline = new Deadline(day, month);
            return new SelectDeadlineCommand(deadline);
        }
        Deadline deadline = new Deadline(day, month, year);
        return new SelectDeadlineCommand(deadline);
    }

    /**
     * Returns true if all of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    protected static boolean noPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).noneMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    protected static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Alternative parsing method:
     * @param userInput without date, month and year prefixes
     * @return the parsed Deadline
     */
    protected static Deadline parseWithoutPrefixes(String userInput) {
        try {
            return ParserUtil.parseDeadline(userInput);
        } catch (ParseException e) {
            return null;
        }
    }
}
