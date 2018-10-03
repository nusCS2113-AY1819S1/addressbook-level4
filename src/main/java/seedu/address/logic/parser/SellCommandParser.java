package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SellCommand;
import seedu.address.logic.commands.SellCommand.DecreaseQuantity;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.Quantity;

/**
 * Parses input arguments and creates a new SellCommand object
 */
public class SellCommandParser implements Parser<SellCommand> {

    /**
     * Parses the given (@code String} of arguments in the context of the SellCommand
     * and returns a SellCommand object for execution.
     * @throw ParseException if the user input does not conform the expected format
     */
    public SellCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_QUANTITY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE), pe);
        }

        DecreaseQuantity decreaseQuantity = new DecreaseQuantity();
        if (argMultimap.getValue(PREFIX_QUANTITY).isPresent()) {
            Quantity quantity = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_QUANTITY).get());
            if (Integer.valueOf(quantity.getValue()) < 0) {
                throw new ParseException(SellCommand.MESSAGE_NEGATIVE_SOLD_VALUE);
            } else {
                decreaseQuantity.setQuantity(quantity);
            }
        }

        if (!decreaseQuantity.isQuantityFieldSpecified()) {
            throw new ParseException(SellCommand.MESSAGE_NOT_SOLD);
        }


        return new SellCommand(index, decreaseQuantity);
    }
}
