package com.t13g2.forum.logic.parser;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD_ID;

import java.util.stream.Stream;

import com.t13g2.forum.commons.core.Messages;
import com.t13g2.forum.logic.commands.DeleteThreadCommand;
import com.t13g2.forum.logic.parser.exceptions.ParseException;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.storage.forum.UnitOfWork;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteThreadCommandParser implements Parser<DeleteThreadCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteThreadCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_THREAD_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_THREAD_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                 DeleteThreadCommand.MESSAGE_USAGE));
        }
        int threadId = Integer.parseInt(ParserUtil.parseThreadId(argMultimap.getValue(PREFIX_THREAD_ID).get()));
        UnitOfWork unitOfWork = new UnitOfWork();
        //get forum thread from repo by thread id
        ForumThread forumThread = unitOfWork.getForumThreadRepository().getThread(threadId);

        return new DeleteThreadCommand(forumThread);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
