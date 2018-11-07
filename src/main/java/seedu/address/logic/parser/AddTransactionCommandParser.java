package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.commands.AddTransactionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.timeidentifiedclass.Transaction;
import seedu.address.model.timeidentifiedclass.exceptions.ClosedTransactionException;

/**
 * Parses input arguments and creates a new AddTransactionCommand object
 */
public class AddTransactionCommandParser implements Parser<AddTransactionCommand> {

    /**
     * This method checks if the args contains an empty product name.
     * This method is to be called on a args with no trailing whitespaces.
     * @param args the argument itself
     * @return true if and only if the args ends with "pr/"
     */
    private boolean containsEmptyProductAtEnd(String args) {
        int argLength = args.length();
        if (argLength < 3) {
            return false;
        }
        if (args.charAt(argLength - 1) == '/'
                && args.charAt(argLength - 2) == 'r'
                && args.charAt(argLength - 3) == 'p') {
            return true;
        }
        return false;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddTransactionCommand
     * and returns an AddTransactionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTransactionCommand parse(String args) throws ParseException {
        args = args.trim();

        if (containsEmptyProductAtEnd(args)) {
            throw new ParseException("Some products in the transaction have no name! "
                    + "Please enter their names before trying again");
        }

        ArrayList<String> productsToAdd = new ArrayList<>(Arrays.asList(args.split(PREFIX_PRODUCT.toString())));
        // removing empty string from split function
        if (productsToAdd.size() > 0 && productsToAdd.get(0).equals("")) {
            productsToAdd.remove(0);
        }

        productsToAdd.trimToSize();
        if (productsToAdd.size() == 0) {
            throw new ParseException("Transaction entered has no products!");
        }

        Transaction transaction = new Transaction();

        for (String product : productsToAdd) {
            product = product.trim();
            try {
                if (product.length() == 0) {
                    throw new ParseException("Some products in the transaction have no name! "
                            + "Please enter their names before trying again");
                }
                transaction.addProduct(product);
            } catch (ClosedTransactionException e) {
                // TODO: Exception handling in AddTransaction command parse. Closed transaction not possible as of yet.
            }
        }
        return new AddTransactionCommand(transaction);
    }
}
