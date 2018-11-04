package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddAccountCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.account.Account;
import seedu.address.model.account.Password;
import seedu.address.model.account.Username;

/**
 * Parses input arguments and creates a new AddAccountCommand object
 */
public class AddAccountCommandParser implements Parser<AddAccountCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAccountCommand
     * and returns an AddAccountCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAccountCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAccountCommand.MESSAGE_USAGE));
        }

        Username username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME).get());
        Password password = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get());

        Account account = new Account(username, password);

        return new AddAccountCommand(account);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
