package com.t13g2.forum.logic.parser;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_NAME;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_PASSWORD;

import java.util.stream.Stream;

import com.t13g2.forum.commons.core.Messages;
import com.t13g2.forum.logic.commands.AdminUpdatePasswordCommand;
import com.t13g2.forum.logic.parser.exceptions.ParseException;

//@@xllx1
/**
 * Parses input arguments and creates a new AdminUpdatePasswordCommand object.
 */
public class AdminUpdatePasswordCommandParser implements Parser<AdminUpdatePasswordCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AdminUpdatePasswordCommand.
     * and returns an AdminUpdatePasswordCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AdminUpdatePasswordCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_USER_NAME, PREFIX_USER_PASSWORD);
        if (!arePrefixesPresent(argMultimap, PREFIX_USER_NAME, PREFIX_USER_PASSWORD)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                AdminUpdatePasswordCommand.MESSAGE_USAGE));
        }

        String userName = ParserUtil.parseUserName(argMultimap.getValue(PREFIX_USER_NAME).get());
        String userPassword = ParserUtil.parseUserPassword(argMultimap.getValue(PREFIX_USER_PASSWORD).get());

        return new AdminUpdatePasswordCommand(userName, userPassword);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
