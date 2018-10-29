package com.t13g2.forum.logic.parser;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_NAME;

import java.util.stream.Stream;

import com.t13g2.forum.commons.core.Messages;
import com.t13g2.forum.logic.commands.DeleteUserCommand;
import com.t13g2.forum.logic.parser.exceptions.ParseException;

//@@author xllx1

/**
 * Parses input arguments and deletes a user from forum book.
 */
public class DeleteUserCommandParser implements Parser<DeleteUserCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteUserCommand
     * and returns an DeleteUserCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteUserCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_USER_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_USER_NAME)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteUserCommand.MESSAGE_USAGE));
        }

        String userName = argMultimap.getValue(PREFIX_USER_NAME).get();

        return new DeleteUserCommand(userName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
