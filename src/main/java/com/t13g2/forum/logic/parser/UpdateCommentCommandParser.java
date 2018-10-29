package com.t13g2.forum.logic.parser;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_COMMENT_CONTENT;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_COMMENT_ID;

import java.util.stream.Stream;

import com.t13g2.forum.commons.core.Messages;
import com.t13g2.forum.logic.commands.UpdateCommentCommand;
import com.t13g2.forum.logic.parser.exceptions.ParseException;

//@@author HansKoh
/**
 * Parses input arguments and creates a new UpdateCommentCommand object
 */
public class UpdateCommentCommandParser implements Parser<UpdateCommentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommentCommand
     * and returns an UpdateCommentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateCommentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMMENT_ID, PREFIX_COMMENT_CONTENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_COMMENT_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    UpdateCommentCommand.MESSAGE_USAGE));
        }
        int commentId = Integer.parseInt(ParserUtil.parseCommentId(argMultimap.getValue(PREFIX_COMMENT_ID).get()));
        String commenContentToUpdate = ParserUtil.parseComment(argMultimap.getValue(PREFIX_COMMENT_CONTENT).get());
        return new UpdateCommentCommand(commentId, commenContentToUpdate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
