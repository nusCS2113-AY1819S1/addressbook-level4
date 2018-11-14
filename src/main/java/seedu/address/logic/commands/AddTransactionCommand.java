package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
// TODO: To add transaction items with quantity. [v2.0]
// import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Product;
import seedu.address.model.product.RemainingItems;
import seedu.address.model.timeidentifiedclass.Transaction;
import seedu.address.model.timeidentifiedclass.exceptions.DuplicateTransactionException;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;


/**
 * Adds a transaction to the address book.
 */
public class AddTransactionCommand extends Command {

    public static final String COMMAND_WORD = "transaction";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a transaction. "
            + "Parameters: "
            + PREFIX_PRODUCT + "PRODUCT NAME"
            + PREFIX_PRODUCT + "PRODUCT NAME"
            + "... \n"
            + "Example: "
            + PREFIX_PRODUCT + "Apples "
            + PREFIX_PRODUCT + "Milk "
            + PREFIX_PRODUCT + "Milk";

    private static final String MESSAGE_SUCCESS = "Transaction successfully recorded for time %s";
    private final Transaction toAdd;

    public AddTransactionCommand(Transaction transaction) {
        requireNonNull(transaction);
        toAdd = transaction;
    }


    @Override
    //@@garagaristahir
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Map<String, Integer> transactionRecord = toAdd.getTransactionRecord();
        List<String> listWithProductsToAdd = new ArrayList<>(transactionRecord.keySet());
        List<Integer> values = transactionRecord.values().stream().collect(Collectors.toList());
        boolean invalidInventory = false;

        for (String x : listWithProductsToAdd) {
            if (!model.hasProductName(x)) {
                throw new CommandException("One or more of the products you have "
                        + "typed in does not exist in your inventory");
            }
        }

        for (String name : listWithProductsToAdd) {
            for (Product product : model.getProductInfoBook().getProductList()) {
                if (product.getName().fullName.equals(name.substring(0, 1).toUpperCase() + name.substring(1))) {
                    int newRemainingItems = Integer.parseInt(product.getRemainingItems().value)
                            - values.get(listWithProductsToAdd.indexOf(name));
                    if (newRemainingItems < 0) {
                        invalidInventory = true;
                        RemainingItems itemsRemaining = new RemainingItems("0");
                        Product updateProduct = new Product(product.getName(), product.getSerialNumber(),
                                product.getDistributor(), product.getProductInfo(), itemsRemaining, product.getTags());
                        model.updateProduct(product, updateProduct);
                    } else {
                        RemainingItems itemsRemaining = new RemainingItems(Integer.toString(newRemainingItems));
                        Product updateProduct = new Product(product.getName(), product.getSerialNumber(),
                                product.getDistributor(), product.getProductInfo(), itemsRemaining, product.getTags());
                        model.updateProduct(product, updateProduct);
                    }

                }
            }
        }
        //@@ParasK26
        try {
            model.addTransaction(toAdd);
        } catch (InvalidTimeFormatException e) {
            return new CommandResult(e.getExceptionMessage() + ". Upon adding this transaction");
        } catch (DuplicateTransactionException e) {
            return new CommandResult(e.getLocalizedMessage() + ". Upon adding this transaction");
        }

        model.commitSalesHistory();
        model.commitProductDatabase();
        if (invalidInventory) {
            return new CommandResult("FYI: The inventory does not seem to be uptodate. You have less"
                    + " products then what you just sold\n" + MESSAGE_SUCCESS + toAdd.getTransactionTime());
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getTransactionTime()));
        }
    }
}

