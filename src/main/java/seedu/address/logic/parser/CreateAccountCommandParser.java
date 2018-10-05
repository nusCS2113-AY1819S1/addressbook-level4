package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERPASSWORD;

import java.util.stream.Stream;

import seedu.address.logic.commands.CreateAccountCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.login.LoginDetails;
import seedu.address.model.login.UserId;
import seedu.address.model.login.UserPassword;

/**
 * Parses input arguments and creates a new CreateAccountCommand object
 */
public class CreateAccountCommandParser implements Parser<CreateAccountCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateAccountCommand
     * and returns an CreateAccountCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateAccountCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERID, PREFIX_USERPASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERID, PREFIX_USERPASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateAccountCommand.MESSAGE_USAGE));
        }

        UserId userId = ParserUtil.parseUserId(argMultimap.getValue(PREFIX_USERID).get());
        UserPassword userPassword = ParserUtil.parseUserPassword(argMultimap.getValue(PREFIX_USERPASSWORD).get());
        LoginDetails details = new LoginDetails(userId, userPassword);

        return new CreateAccountCommand(details);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
