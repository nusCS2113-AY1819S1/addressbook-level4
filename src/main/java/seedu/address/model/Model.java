package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.account.Account;
import seedu.address.model.item.Item;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Item> PREDICATE_SHOW_ALL_ITEMS = unused -> true;
    Predicate<Account> PREDICATE_SHOW_ALL_ACCOUNTS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyStockList newData);
    void resetAccountData(ReadOnlyAccountList newData);

    /** Returns the StockList */
    ReadOnlyStockList getStockList();
    ReadOnlyAccountList getAccountList();

    //@@author kelvintankaiboon
    /** Saves the current version of the StockList */
    void saveStockList(String fileName);
    //@@author

    /**
     * Returns true if a item with the same identity as {@code item} exists in the stock list.
     */
    boolean hasItem(Item item);
    boolean hasAccount(Account account);

    /**
     * Deletes the given item.
     * The item must exist in the stock list.
     */
    void deleteItem(Item target);
    void deleteAccount(Account target);

    /**
     * Adds the given item.
     * {@code item} must not already exist in the stock list.
     */
    void addItem(Item item);
    void addAccount(Account account);

    /**
     * Replaces the given item {@code target} with {@code editedItem}.
     * {@code target} must exist in the stock list.
     * The item identity of {@code editedItem} must not be the same as another existing item in the stock list.
     */
    void updateItem(Item target, Item editedItem);
    void updateAccount(Account target, Account editedAccount);

    /** Returns an unmodifiable view of the filtered item list */
    ObservableList<Item> getFilteredItemList();
    ObservableList<Account> getFilteredAccountList();

    /**
     * Updates the filter of the filtered item list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredItemList(Predicate<Item> predicate);
    void updateFilteredAccountList(Predicate<Account> predicate);

    /**
     * Updates the filter of the filtered item list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredItemListByTag(Predicate<Item> predicate);

    /**
     * Returns true if the model has previous stock list states to restore.
     */

    boolean canUndoStockList();

    /**
     * Returns true if the model has undone stock list states to restore.
     */
    boolean canRedoStockList();

    /**
     * Restores the model's stock list to its previous state.
     */
    void undoStockList();

    /**
     * Restores the model's stock list to its previously undone state.
     */
    void redoStockList();

    /**
     * Saves the current stock list state for undo/redo.
     */
    void commitStockList();


}
