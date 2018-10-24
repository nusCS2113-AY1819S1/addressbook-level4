package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLUB_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.ViewClubBudgetsCommand;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.budgetelements.ClubName;

/**
 * Parses input arguments and creates a new ViewClubBudgetsCommand object
 */
public class ViewClubBudgetsCommandParser implements Parser<ViewClubBudgetsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewClubBudgetsCommand
     * and returns a ViewClubBudgetsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewClubBudgetsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLUB_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_CLUB_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewClubBudgetsCommand.MESSAGE_USAGE));
        }

        ClubName clubName = ParserUtil.parseClubName(argMultimap.getValue(PREFIX_CLUB_NAME).get());

        return new ViewClubBudgetsCommand(clubName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
