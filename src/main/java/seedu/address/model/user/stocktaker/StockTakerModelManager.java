package seedu.address.model.user.stocktaker;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.LoginInfoManager;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyInventoryList;
import seedu.address.model.UserPrefs;
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
    @Override
    public void sellDrink(Transaction transaction) throws InsufficientQuantityException{
        Price defaultSalePrice = inventoryList.getDefaultSellingPrice(transaction.getDrinkTransacted());

        Price defaultAmountTransacted = new Price(Float.toString(defaultSalePrice.getValue()
                * transaction.getQuantityTransacted().getValue()));
        transaction.setAmountMoney(defaultAmountTransacted);
        recordTransaction(transaction);

        inventoryList.decreaseQuantity(transaction.getDrinkTransacted(), transaction.getQuantityTransacted());
    }

    @Override
    public void importDrink(Transaction transaction) {
        Price defaultCostPrice = inventoryList.getDefaultCostPrice(transaction.getDrinkTransacted());

        Price defaultAmountTransacted = new Price(Float.toString(defaultCostPrice.getValue()
                * transaction.getQuantityTransacted().getValue()));
        transaction.setAmountMoney(defaultAmountTransacted);
        recordTransaction(transaction);
        inventoryList.increaseQuantity(transaction.getDrinkTransacted(), transaction.getQuantityTransacted());
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
