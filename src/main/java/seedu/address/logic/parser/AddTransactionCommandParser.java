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

    public AddTransactionCommand parse(String args) throws ParseException {
        String[] productList = args.split(PREFIX_PRODUCT.toString());
        Transaction transaction = new Transaction();
        for (int i = 0; i < productList.length; i++) {
            try {
                transaction.addProduct(productList[i]);
            } catch (ClosedTransactionException e) {
                // TODO: Exception handling in AddTransaction command parse. Closed transaction not possible as of yet.
            }
        }
        return new AddTransactionCommand(transaction);
    }
}
