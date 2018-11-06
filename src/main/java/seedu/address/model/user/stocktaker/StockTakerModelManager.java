package seedu.address.model.user.stocktaker;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.model.DrinkAttributeChangedEvent;
import seedu.address.model.LoginInfoManager;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyInventoryList;
import seedu.address.model.UserPrefs;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.Price;
import seedu.address.model.drink.exceptions.InsufficientQuantityException;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionList;

/**
 * The solid class for API of the stock taker
 */
public class StockTakerModelManager extends ModelManager implements StockTakerModel {
    public StockTakerModelManager (ReadOnlyInventoryList inventoryList, UserPrefs userPrefs,
                                   LoginInfoManager loginInfoManager, TransactionList transactionList) {
        super(inventoryList, userPrefs, loginInfoManager, transactionList);
    }
    /**
     * Raises an event to indicate the model has changed
     */
    protected void indicateDrinkAttributesChanged(Drink drink) {
        raise(new DrinkAttributeChangedEvent(drink));
    }

    @Override
    public void sellDrink(Transaction transaction) throws InsufficientQuantityException {
        Price defaultSalePrice = inventoryList.getDefaultSellingPrice(transaction.getDrinkTransacted());

        inventoryList.decreaseQuantity(transaction.getDrinkTransacted(), transaction.getQuantityTransacted());
        Price defaultAmountTransacted = new Price(Float.toString(defaultSalePrice.getValue()
                * transaction.getQuantityTransacted().getValue()));
        transaction.setAmountMoney(defaultAmountTransacted);
        recordTransaction(transaction);

        indicateDrinkAttributesChanged(transaction.getDrinkTransacted());
    }

    @Override
    public void buyDrink(Transaction transaction) {
        Price defaultCostPrice = inventoryList.getDefaultCostPrice(transaction.getDrinkTransacted());

        inventoryList.increaseDrinkQuantity(transaction.getDrinkTransacted(), transaction.getQuantityTransacted());
        Price defaultAmountTransacted = new Price(Float.toString(defaultCostPrice.getValue()
                * transaction.getQuantityTransacted().getValue()));
        transaction.setAmountMoney(defaultAmountTransacted);
        recordTransaction(transaction);

        indicateDrinkAttributesChanged(transaction.getDrinkTransacted());
    }

    private void recordTransaction(Transaction transaction) {
        transactionList.addTransaction(transaction);
    }


    /**
     * Returns an unmodifiable view of the list of {@code Transaction} backed by the internal list of
     * {@code transactionList}
     */
    @Override
    public ObservableList<Transaction> getTransactionList() {
        List<Transaction> transactions = transactionList.getTransactions();
        return FXCollections.unmodifiableObservableList(FXCollections.observableList(transactions));
    }

    @Override
    public String getTransactions() {
        return transactionList.toString();
    }
}
