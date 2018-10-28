package seedu.address.logic.drinkparser.user;
//@@author tianhang
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLD_PASSWORD;

import java.util.stream.Stream;

import seedu.address.logic.drinkcommands.user.ChangePasswordCommand;
import seedu.address.logic.drinkparser.DrinkParser;
import seedu.address.logic.drinkparser.DrinkParserUtil;
import seedu.address.logic.drinkparser.exceptions.DrinkParseException;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.user.Password;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class ChangePasswordCommandParser implements DrinkParser< ChangePasswordCommand > {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws DrinkParseException if the user input does not conform the expected format
     */
    public ChangePasswordCommand parse(String args) throws DrinkParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_OLD_PASSWORD , PREFIX_NEW_PASSWORD);
        if (!arePrefixesPresent(argMultimap, PREFIX_OLD_PASSWORD , PREFIX_OLD_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new DrinkParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                        ChangePasswordCommand.MESSAGE_USAGE));
        }

        Password newPassword = DrinkParserUtil.parsePassword (argMultimap.getValue(PREFIX_NEW_PASSWORD).get());
        Password oldPassword = DrinkParserUtil.parsePassword (argMultimap.getValue (PREFIX_OLD_PASSWORD).get ());

        return new ChangePasswordCommand(oldPassword, newPassword);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent (ArgumentMultimap argumentMultimap , Prefix prefixes ,
                                               Prefix prefixNewPassword) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
