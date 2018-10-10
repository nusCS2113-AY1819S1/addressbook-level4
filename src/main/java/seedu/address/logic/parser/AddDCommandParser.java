package seedu.address.logic.parser;

import seedu.address.logic.commands.AddDCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.distributor.*;
import seedu.address.model.tag.Tag;

import java.util.Set;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Parses input arguments and creates a new AddDCommand object
 */
public class AddDCommandParser implements Parser<AddDCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddDCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDCommand.MESSAGE_USAGE));
        }

        DistributorName name = ParserUtil.parseDistName(argMultimap.getValue(PREFIX_NAME).get());
        DistributorPhone phone = ParserUtil.parseDistPhone(argMultimap.getValue(PREFIX_PHONE).get());

        Distributor distributor = new Distributor(name, phone);

        return new AddDCommand(distributor);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
