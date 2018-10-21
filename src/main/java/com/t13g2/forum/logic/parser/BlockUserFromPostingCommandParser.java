package com.t13g2.forum.logic.parser;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_NAME;

import java.util.stream.Stream;

import com.t13g2.forum.commons.core.Messages;
import com.t13g2.forum.logic.commands.BlockUserFromCreatingCommand;
import com.t13g2.forum.logic.parser.exceptions.ParseException;

//@@xllx1
/**
 * Parses input arguments and block a certain user from posting.
 */
public class BlockUserFromPostingCommandParser implements Parser<BlockUserFromCreatingCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the BlockUserFromCreatingCommand.
     * and returns an BlockUserFromCreatingCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public BlockUserFromCreatingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_USER_NAME);
        if (!arePrefixesPresent(argMultimap, PREFIX_USER_NAME)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                BlockUserFromCreatingCommand.MESSAGE_USAGE));
        }
        String userName = ParserUtil.parseUserName(argMultimap.getValue(PREFIX_USER_NAME).get());

        return new BlockUserFromCreatingCommand(userName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
