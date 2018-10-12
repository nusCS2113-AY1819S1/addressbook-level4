package com.t13g2.forum.logic.parser;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_NAME;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_PASSWORD;

import java.util.stream.Stream;

import com.t13g2.forum.commons.core.Messages;
import com.t13g2.forum.logic.commands.LoginCommand;
import com.t13g2.forum.logic.parser.exceptions.ParseException;

/**
 *
 */
public class LoginCommandParser implements Parser<LoginCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand
     * and returns an LoginCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USER_NAME, PREFIX_USER_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USER_NAME, PREFIX_USER_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                LoginCommand.MESSAGE_USAGE));
        }

        String userName = argMultimap.getValue(PREFIX_USER_NAME).get().trim();
        String userPassword = argMultimap.getValue(PREFIX_USER_PASSWORD).get().trim();

        return new LoginCommand(userName, userPassword);
    }
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
