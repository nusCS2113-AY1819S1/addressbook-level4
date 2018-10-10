package seedu.address.logic.parser.exceptions;

import seedu.address.logic.commands.AddTransactionCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.model.timeidentifiedclass.transaction.Transaction;
import seedu.address.model.timeidentifiedclass.transaction.exceptions.ClosedTransactionException;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;

public class AddTransactionCommandParser implements Parser<AddTransactionCommand> {

    public AddTransactionCommand parse(String args) throws ParseException {
        String[] productList = args.split(PREFIX_PRODUCT.toString());
        Transaction transaction = new Transaction();
        for (int i=0; i<productList.length; i++) {
            try {
                transaction.addProduct(productList[i]);
            } catch (ClosedTransactionException e) {
                // TODO: Exception handling in AddTransaction command parse. Closed transaction not possible as of yet.
            }
        }
        return new AddTransactionCommand(transaction);
    }
}
