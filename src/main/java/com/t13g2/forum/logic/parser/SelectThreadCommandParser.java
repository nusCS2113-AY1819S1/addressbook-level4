package com.t13g2.forum.logic.parser;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD_ID;

import java.util.stream.Stream;

import com.t13g2.forum.commons.core.Messages;
import com.t13g2.forum.logic.commands.SelectThreadCommand;
import com.t13g2.forum.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectThreadCommand object
 */
public class SelectThreadCommandParser implements Parser<SelectThreadCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SelectThreadCommand
     * and returns an SelectThreadCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectThreadCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_THREAD_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_THREAD_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                SelectThreadCommand.MESSAGE_USAGE));
        }
        String thread_Id = ParserUtil.parseThreadId(argMultimap.getValue(PREFIX_THREAD_ID).get());
        int threadId = Integer.parseInt(thread_Id);
        return new SelectThreadCommand(threadId);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
