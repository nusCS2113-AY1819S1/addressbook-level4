package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;

import seedu.address.logic.commands.AddTransactionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.timeidentifiedclass.transaction.Transaction;
import seedu.address.model.timeidentifiedclass.transaction.exceptions.ClosedTransactionException;

/**
 * Parses input arguments and creates a new AddTransactionCommand object
 */
public class AddTransactionCommandParser implements Parser<AddTransactionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTransactionCommand
     * and returns an AddTransactionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTransactionCommand parse(String args) throws ParseException {
        String[] productList = args.split(PREFIX_PRODUCT.toString());
        Transaction transaction = new Transaction();
        for (int i = 0; i < productList.length; i++) {
            try {
                transaction.addProduct(productList[i].trim());
            } catch (ClosedTransactionException e) {
                // TODO: Exception handling in AddTransaction command parse. Closed transaction not possible as of yet.
            }
        }
        return new AddTransactionCommand(transaction);
    }
}
