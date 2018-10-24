package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LostCommand;
import seedu.address.logic.commands.LostCommand.LostDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses input arguments and creates a new LostCommand object
 */
public class LostCommandParser implements Parser<LostCommand> {

/**
 * Parses the given {@code String} of arguments in the context of the LostCommand
 * and returns an LostCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
    public LostCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_QUANTITY);

        Index targetIndex;
        try {
            targetIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LostCommand.MESSAGE_USAGE), pe);
        }
        LostDescriptor lostDescriptor = new LostDescriptor();
        LostDescriptor.setQuantity(ParserUtil
                .parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get()).toInteger());


        return new LostCommand(targetIndex, lostDescriptor);
    }

}
