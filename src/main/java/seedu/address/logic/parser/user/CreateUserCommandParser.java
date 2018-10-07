package seedu.address.logic.parser.user;
//@@author tianhang
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHENTICATION_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;
//@@author tianhang

import seedu.address.logic.commands.user.CreateUserCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class CreateUserCommandParser implements Parser< CreateUserCommand > {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateUserCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME , PREFIX_PASSWORD, PREFIX_AUTHENTICATION_LEVEL);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME , PREFIX_PASSWORD, PREFIX_AUTHENTICATION_LEVEL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateUserCommand.MESSAGE_USAGE));
        }

        String userName = argMultimap.getValue(PREFIX_USERNAME).get();
        String password = argMultimap.getValue (PREFIX_PASSWORD).get ();
        String authenticationLevel = argMultimap.getValue (PREFIX_AUTHENTICATION_LEVEL).get ();

        return new CreateUserCommand(userName, password, authenticationLevel);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent (ArgumentMultimap argumentMultimap ,
                                               Prefix prefixUsername , Prefix prefixes , Prefix prefixNewPassword) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
