package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIST_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIST_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIST_PROD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddDistributorCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.distributor.DistributorPhone;
import seedu.address.model.distributor.DistributorProduct;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddDistributorCommand object
 */
public class AddDistributorCommandParser implements Parser<AddDistributorCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddDistributorCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DIST_NAME, PREFIX_DIST_PHONE, PREFIX_DIST_PROD, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_DIST_NAME, PREFIX_DIST_PHONE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddDistributorCommand.MESSAGE_USAGE));
        }

        DistributorName name = ParserUtil.parseDistName(argMultimap.getValue(PREFIX_DIST_NAME).get());
        DistributorPhone phone = ParserUtil.parseDistPhone(argMultimap.getValue(PREFIX_DIST_PHONE).get());
        Set<DistributorProduct> prodList = ParserUtil.parseDistProds(argMultimap.getAllValues(PREFIX_DIST_PROD));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Distributor distributor = new Distributor(name, phone, prodList, tagList);

        return new AddDistributorCommand(distributor);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
