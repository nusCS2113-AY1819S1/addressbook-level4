package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLUB_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPECTED_TURNOUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_OF_EVENTS;

import java.util.stream.Stream;

import seedu.address.logic.commands.BudgetCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.budgetelements.ClubBudgetElements;
import seedu.address.model.budgetelements.ClubName;
import seedu.address.model.budgetelements.ExpectedTurnout;
import seedu.address.model.budgetelements.NumberOfEvents;

/**
 * Parses input arguments and creates a new BudgetCommand object
 */
public class BudgetCommandParser implements Parser<BudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BudgetCommand
     * and returns an BudgetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BudgetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLUB_NAME, PREFIX_EXPECTED_TURNOUT, PREFIX_NUMBER_OF_EVENTS);

        if (!arePrefixesPresent(argMultimap, PREFIX_CLUB_NAME, PREFIX_EXPECTED_TURNOUT, PREFIX_NUMBER_OF_EVENTS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetCommand.MESSAGE_USAGE));
        }

        ClubName clubname = ParserUtil.parseClubName(argMultimap.getValue(PREFIX_CLUB_NAME).get());
        ExpectedTurnout expectedturnout = ParserUtil.parseExpectedTurnout(argMultimap.getValue(
                PREFIX_EXPECTED_TURNOUT).get());
        NumberOfEvents numberofevents = ParserUtil.parseNumberOfEvents(argMultimap.getValue(
                PREFIX_NUMBER_OF_EVENTS).get());

        ClubBudgetElements club = new ClubBudgetElements(clubname, expectedturnout, numberofevents);

        return new BudgetCommand(club);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
