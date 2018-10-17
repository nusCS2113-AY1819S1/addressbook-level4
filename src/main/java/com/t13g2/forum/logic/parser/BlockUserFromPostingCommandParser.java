package com.t13g2.forum.logic.parser;

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
        try {
            String userName = ParserUtil.parseUserName(args);
            return new BlockUserFromCreatingCommand(userName);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, BlockUserFromCreatingCommand.MESSAGE_USAGE), pe);
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
