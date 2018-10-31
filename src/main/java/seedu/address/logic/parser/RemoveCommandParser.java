package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;


//@@author jieliangang
/**
 * Parses input arguments and creates a new RemoveCommand object
 */
public class RemoveCommandParser implements Parser<RemoveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveCommandParser
     * and returns a RemoveCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public RemoveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FROM);

        Index indexPerson;
        Index indexEvent;

        try {
            indexPerson = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(PREFIX_FROM).isPresent()) {
            try {
                indexEvent = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_FROM).get());
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        RemoveCommand.MESSAGE_USAGE), pe);
            }
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE));
        }

        return new RemoveCommand(indexPerson, indexEvent);

    }
}
