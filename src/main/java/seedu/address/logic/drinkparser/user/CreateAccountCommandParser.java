package seedu.address.logic.drinkparser.user;
//@@author tianhang

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHENTICATION_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.address.logic.drinkcommands.user.CreateAccountCommand;
import seedu.address.logic.drinkparser.DrinkParser;
import seedu.address.logic.drinkparser.DrinkParserUtil;
import seedu.address.logic.drinkparser.exceptions.DrinkParseException;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.user.AuthenticationLevel;
import seedu.address.model.user.Password;
import seedu.address.model.user.UserName;

//@@author tianhang

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class CreateAccountCommandParser implements DrinkParser<CreateAccountCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws DrinkParseException if the user input does not conform the expected format
     */
    public CreateAccountCommand parse(String args) throws DrinkParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD, PREFIX_AUTHENTICATION_LEVEL);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD, PREFIX_AUTHENTICATION_LEVEL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new DrinkParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CreateAccountCommand.MESSAGE_USAGE));
        }

        UserName userName = DrinkParserUtil.parseUserName(argMultimap.getValue(PREFIX_USERNAME).get());
        Password password = DrinkParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get());
        AuthenticationLevel authenticationLevel = DrinkParserUtil.parseAuthenticationLevel(
                argMultimap.getValue(PREFIX_AUTHENTICATION_LEVEL).get());

        return new CreateAccountCommand(userName, password, authenticationLevel);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap,
                                              Prefix prefixUsername, Prefix prefixes, Prefix prefixNewPassword) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
