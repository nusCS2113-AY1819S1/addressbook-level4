package com.t13g2.forum.logic.parser;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_ANNOUNCE_CONTENT;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_ANNOUNCE_TITLE;

import java.util.stream.Stream;

import com.t13g2.forum.commons.core.Messages;
import com.t13g2.forum.logic.commands.AnnounceCommand;
import com.t13g2.forum.logic.parser.exceptions.ParseException;
import com.t13g2.forum.model.forum.Announcement;


/**
 * Parses input arguments and creates a new AnnounceCommand object.
 */
public class AnnounceCommandParser implements Parser<AnnounceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AnnounceCommand.
     * and returns an AnnounceCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AnnounceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_ANNOUNCE_TITLE, PREFIX_ANNOUNCE_CONTENT);
        if (!arePrefixesPresent(argMultimap, PREFIX_ANNOUNCE_TITLE, PREFIX_ANNOUNCE_CONTENT)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                AnnounceCommand.MESSAGE_USAGE));
        }

        Announcement announcement = ParserUtil.parseAnnouncement(argMultimap.getValue(PREFIX_ANNOUNCE_TITLE).get(),
            argMultimap.getValue(PREFIX_ANNOUNCE_CONTENT).get());

        return new AnnounceCommand(announcement);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
