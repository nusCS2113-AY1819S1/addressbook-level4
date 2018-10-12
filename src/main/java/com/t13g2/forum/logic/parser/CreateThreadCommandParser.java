package com.t13g2.forum.logic.parser;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_COMMENT_CONTENT;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD_TITLE;

import java.util.stream.Stream;

import com.t13g2.forum.commons.core.Messages;
import com.t13g2.forum.logic.commands.CreateThreadCommand;
import com.t13g2.forum.logic.parser.exceptions.ParseException;
import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.model.forum.ForumThread;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class CreateThreadCommandParser implements Parser<CreateThreadCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateThreadCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE, PREFIX_THREAD_TITLE, PREFIX_COMMENT_CONTENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE, PREFIX_COMMENT_CONTENT, PREFIX_THREAD_TITLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                CreateThreadCommand.MESSAGE_USAGE));
        }

        String moduleCode = ParserUtil.parseModule(argMultimap.getValue(PREFIX_MODULE_CODE).get());
        String threadTitle = ParserUtil.parseThread(argMultimap.getValue(PREFIX_THREAD_TITLE).get());
        String commentContent = ParserUtil.parseComment(argMultimap.getValue(PREFIX_COMMENT_CONTENT).get());

        ForumThread forumThread = new ForumThread(); //instantiate a new forum thread
        forumThread.setTitle(threadTitle); //pass the thread title to this new forum thread

        Comment comment = new Comment();
        comment.setContent(commentContent);

        return new CreateThreadCommand(moduleCode, forumThread, comment);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
