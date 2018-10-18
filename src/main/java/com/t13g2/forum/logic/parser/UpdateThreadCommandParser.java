package com.t13g2.forum.logic.parser;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD_ID;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD_TITLE;

import java.util.stream.Stream;

import com.t13g2.forum.commons.core.Messages;
import com.t13g2.forum.logic.commands.UpdateThreadCommand;
import com.t13g2.forum.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UpdateThreadCommand object
 */
public class UpdateThreadCommandParser implements Parser<UpdateThreadCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UpdateThreadCommand
     * and returns an UpdateThreadCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateThreadCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_THREAD_ID, PREFIX_THREAD_TITLE);

        if (!arePrefixesPresent(argMultimap, PREFIX_THREAD_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    UpdateThreadCommand.MESSAGE_USAGE));
        }
        int threadId = Integer.parseInt(ParserUtil.parseThreadId(argMultimap.getValue(PREFIX_THREAD_ID).get()));
        String threadTitle = ParserUtil.parseThread(argMultimap.getValue(PREFIX_THREAD_TITLE).get());
        return new UpdateThreadCommand(threadId, threadTitle);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
