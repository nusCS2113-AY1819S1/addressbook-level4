package com.t13g2.forum.logic.parser;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_NAME;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_PASSWORD;

import java.util.stream.Stream;

import com.t13g2.forum.commons.core.Messages;
import com.t13g2.forum.logic.commands.AddUserCommand;
import com.t13g2.forum.logic.parser.exceptions.ParseException;
import com.t13g2.forum.model.forum.User;

/**
 *
 */
public class AddUserCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand
     * and returns an LoginCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddUserCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USER_NAME, PREFIX_USER_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USER_NAME, PREFIX_USER_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                AddUserCommand.MESSAGE_USAGE));
        }

        String userName = ParserUtil.parseUserName(argMultimap.getValue(PREFIX_USER_NAME).get());
        String userPassword = ParserUtil.parseUserPassword(argMultimap.getValue(PREFIX_USER_PASSWORD).get());
        User user = new User (userName, userPassword, false, false, " ", " ");
        return new AddUserCommand(user);
    }
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
