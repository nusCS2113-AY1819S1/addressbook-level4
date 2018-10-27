package com.t13g2.forum.logic.parser;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_COMMENT_CONTENT;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD_ID;

import java.util.stream.Stream;

import com.t13g2.forum.commons.core.Messages;
import com.t13g2.forum.logic.commands.CreateCommentCommand;
import com.t13g2.forum.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CreateCommentCommand object
 */
public class CreateCommentCommandParser implements Parser<CreateCommentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateCommentCommand
     * and returns an CreateCommentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateCommentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_THREAD_ID, PREFIX_COMMENT_CONTENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_THREAD_ID, PREFIX_COMMENT_CONTENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    CreateCommentCommand.MESSAGE_USAGE));
        }

        int threadId = Integer.parseInt(ParserUtil.parseThreadId(argMultimap.getValue(PREFIX_THREAD_ID).get()));
        String commentToAdd = ParserUtil.parseComment(argMultimap.getValue(PREFIX_COMMENT_CONTENT).get());

        return new CreateCommentCommand(threadId, commentToAdd);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
