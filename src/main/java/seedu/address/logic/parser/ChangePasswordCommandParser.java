package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangePasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.login.Password;
import seedu.address.model.login.Username;

/**
 * Parses input arguments and creates a new ChangePasswordCommand object
 */
public class ChangePasswordCommandParser implements Parser<ChangePasswordCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ChangePasswordCommand
     * and returns an ChangePasswordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangePasswordCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD, PREFIX_NEW_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD, PREFIX_NEW_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangePasswordCommand.MESSAGE_USAGE));
        }

        try {
            Username username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME).get());
            Password password = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get());
            Password newPassword = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_NEW_PASSWORD).get());

            return new ChangePasswordCommand(username, password, newPassword);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
