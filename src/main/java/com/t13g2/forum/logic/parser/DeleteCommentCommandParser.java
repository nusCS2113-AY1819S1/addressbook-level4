package com.t13g2.forum.logic.parser;

import com.t13g2.forum.commons.core.Messages;
import com.t13g2.forum.commons.core.index.Index;
import com.t13g2.forum.logic.commands.DeleteCommand;
import com.t13g2.forum.logic.commands.DeleteThreadCommand;
import com.t13g2.forum.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteThreadCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
