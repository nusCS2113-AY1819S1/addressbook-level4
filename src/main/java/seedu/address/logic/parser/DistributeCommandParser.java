package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NATIONALITY;

import java.util.stream.Stream;

import seedu.address.logic.commands.DistributeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.distribute.Distribute;
import seedu.address.model.group.GroupName;

/**
 * Parses input arguments and creates a new DistributeCommand object
 */
public class DistributeCommandParser implements Parser<DistributeCommand> {

    @Override
    public DistributeCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GENDER, PREFIX_NATIONALITY);

        int index;

        try {
            index = ParserUtil.parseInteger(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DistributeCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_GENDER, PREFIX_NATIONALITY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DistributeCommand.MESSAGE_USAGE));
        }

        GroupName groupName = ParserUtil.parseGroupName(argMultimap.getValue(PREFIX_NAME).get());
        Boolean isSortByGender = ParserUtil.parseIsFlagged(argMultimap.getValue(PREFIX_GENDER).get());
        Boolean isSortByNationality = ParserUtil.parseIsFlagged(argMultimap.getValue(PREFIX_NATIONALITY).get());

        Distribute dist = new Distribute(index, groupName, isSortByGender, isSortByNationality);
        return new DistributeCommand(dist);
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
