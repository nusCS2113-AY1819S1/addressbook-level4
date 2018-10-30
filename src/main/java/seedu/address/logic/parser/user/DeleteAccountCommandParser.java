package seedu.address.logic.parser.user;
//@@author tianhang
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;
//@@author tianhang

import seedu.address.logic.commands.user.CreateAccountCommand;
import seedu.address.logic.commands.user.DeleteAccountCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;

import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.user.UserName;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class DeleteAccountCommandParser implements Parser<DeleteAccountCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteAccountCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateAccountCommand.MESSAGE_USAGE));
        }

        UserName userName = ParserUtil.parseUserName (argMultimap.getValue(PREFIX_USERNAME).get());

        return new DeleteAccountCommand (userName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent (ArgumentMultimap argumentMultimap , Prefix prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
