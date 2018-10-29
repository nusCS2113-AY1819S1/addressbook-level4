package com.t13g2.forum.logic.parser;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_COMMENT_ID;

import java.util.stream.Stream;

import com.t13g2.forum.commons.core.Messages;
import com.t13g2.forum.logic.commands.DeleteCommentCommand;
import com.t13g2.forum.logic.parser.exceptions.ParseException;

//@@author HansKoh
/**
 * Parses input arguments and creates a new DeleteCommentCommand object
 */
public class DeleteCommentCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommentCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMMENT_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_COMMENT_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteCommentCommand.MESSAGE_USAGE));
        }
        int commentId = Integer.parseInt(ParserUtil.parseCommentId(argMultimap.getValue(PREFIX_COMMENT_ID).get()));

        return new DeleteCommentCommand(commentId);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
