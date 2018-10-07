package seedu.address.logic.parser.user;
//@@author tianhang
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.stream.Stream;

import seedu.address.logic.commands.user.ChangePasswordCommand;
import seedu.address.logic.parser.*;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class ChangePasswordCommandParser implements Parser< ChangePasswordCommand > {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangePasswordCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_OLD_PASSWORD , PREFIX_NEW_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_OLD_PASSWORD , PREFIX_NEW_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePasswordCommand.MESSAGE_USAGE));
        }

        String newPassword = ParserUtil.password (argMultimap.getValue(PREFIX_NEW_PASSWORD).get());
        String oldPassword = ParserUtil.password (argMultimap.getValue (PREFIX_OLD_PASSWORD).get ());
        return new ChangePasswordCommand(oldPassword, newPassword );
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent (ArgumentMultimap argumentMultimap , Prefix prefixes , Prefix prefixNewPassword) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
