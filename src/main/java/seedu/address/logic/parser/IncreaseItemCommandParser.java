package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_QUANTITY;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditItemCommand;
import seedu.address.logic.commands.EditItemCommand.EditItemDescriptor;
import seedu.address.logic.commands.IncreaseItemCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.ItemQuantity;

/**
 * Parses input arguments and creates a new EditItemCommand object
 */
public class IncreaseItemCommandParser implements Parser<IncreaseItemCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditItemCommand
     * and returns an EditItemCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public IncreaseItemCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ITEM_QUANTITY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, IncreaseItemCommand.MESSAGE_USAGE), pe);
        }

//        IncreaseItemDescriptor increaseItemDescriptor = new IncreaseItemDescriptor();
//            increaseItemDescriptor.setItemQuantity(ParserUtil
//                    .parseItemQuantity(argMultimap.getValue(PREFIX_ITEM_QUANTITY).get()));
//        }

//        DateLedger dateLedger = ParserUtil.parseDateLedger(argumentMultimap.getValue(PREFIX_DATE).get());
        ItemQuantity itemQuantity = ParserUtil.parseItemQuantity(argumentMultimap.getValue(PREFIX_ITEM_QUANTITY).get());

        return new IncreaseItemCommand(index, itemQuantity);
    }

}
