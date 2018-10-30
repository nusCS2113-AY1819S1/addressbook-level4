package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FoundCommand;
import seedu.address.logic.commands.FoundCommand.FoundDescriptor;

import seedu.address.logic.parser.exceptions.ParseException;

//@@author HeHaowei
/**
 * Parses input arguments and creates a new FoundCommand object
 */
public class FoundCommandParser implements Parser<FoundCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FoundCommand
     * and returns an FoundCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FoundCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_QUANTITY);

        Index targetIndex;
        try {
            targetIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FoundCommand.MESSAGE_USAGE), pe);
        }
        FoundDescriptor foundDescriptor = new FoundDescriptor();

        foundDescriptor.setFoundQuantity(ParserUtil
                .parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get()).toInteger());

        return new FoundCommand(targetIndex, foundDescriptor);
    }

}

