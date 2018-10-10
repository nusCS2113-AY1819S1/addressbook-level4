package com.t13g2.forum.logic.parser;

import java.util.stream.Stream;

import com.t13g2.forum.commons.core.Messages;
import com.t13g2.forum.logic.commands.BlockUserFromPostingCommand;
import com.t13g2.forum.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and block a certain user from posting.
 */
public class BlockUserFromPostingCommandParser implements Parser<BlockUserFromPostingCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the BlockUserFromPostingCommand.
     * and returns an BlockUserFromPostingCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public BlockUserFromPostingCommand parse(String args) throws ParseException {
        try {
            String userName = ParserUtil.parseUserName(args);
            return new BlockUserFromPostingCommand(userName);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, BlockUserFromPostingCommand.MESSAGE_USAGE), pe);
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
