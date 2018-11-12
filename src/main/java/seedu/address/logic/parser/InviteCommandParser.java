package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.InviteCommand;
import seedu.address.logic.parser.exceptions.ParseException;


//@@author jieliangang
/**
 * Parses input arguments and creates a new InviteCommand object
 */
public class InviteCommandParser implements Parser<InviteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the InviteCommandParser
     * and returns a InviteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public InviteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TO);

        Index indexPerson;
        Index indexEvent;

        try {
            indexPerson = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, InviteCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(PREFIX_TO).isPresent()) {
            try {
                indexEvent = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TO).get());
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        InviteCommand.MESSAGE_USAGE), pe);
            }
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, InviteCommand.MESSAGE_USAGE));
        }

        return new InviteCommand(indexPerson, indexEvent);

    }

}
