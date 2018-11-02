package seedu.address.model.user.stocktaker;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.drink.exceptions.InsufficientQuantityException;
import seedu.address.model.transaction.Transaction;

/**
 * The API for stockTaker
 */
public interface StockTakerModel extends Model {
    /**
     * Decreases the quantity of the drink in the {@code transaction}.
     */
    void sellDrink(Transaction transaction) throws InsufficientQuantityException;

    /**
     * Increases the quantity of the dirnk in the {@code transaction}
     */
    void importDrink(Transaction transaction);

    /**
     * Returns an unmodifiable view of the transaction list
     */
    ObservableList<Transaction> getTransactionList();
    String getTransactions(); //TODO: will be removed when UI is up

}
