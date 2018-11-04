package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AccountListChangedEvent;
import seedu.address.commons.events.model.OpenStockListVersionEvent;
import seedu.address.commons.events.model.SaveStockListVersionEvent;
import seedu.address.commons.events.model.StockListChangedEvent;
import seedu.address.model.account.Account;
import seedu.address.model.item.Item;

/**
 * Represents the in-memory model of the stock list data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedStockList versionedStockList;
    private final FilteredList<Item> filteredItems;
    private final VersionedAccountList versionedAccountList;
    private final FilteredList<Account> filteredAccounts;

    /**
     * Initializes a ModelManager with the given stockList and userPrefs.
     */
    public ModelManager(ReadOnlyStockList stockList, UserPrefs userPrefs, ReadOnlyAccountList accountList) {
        super();
        requireAllNonNull(stockList, userPrefs);

        logger.fine("Initializing with stock list: " + stockList + " and user prefs " + userPrefs);

        versionedStockList = new VersionedStockList(stockList);
        filteredItems = new FilteredList<>(versionedStockList.getItemList());

        logger.fine("Initializing with account list: " + accountList);

        versionedAccountList = new VersionedAccountList(accountList);
        filteredAccounts = new FilteredList<>(versionedAccountList.getAccountList());
    }

    public ModelManager() {
        this(new StockList(), new UserPrefs(), new AccountList());
    }

    @Override
    public void resetData(ReadOnlyStockList newData) {
        versionedStockList.resetData(newData);
        indicateStockListChanged();
    }

    @Override
    public ReadOnlyStockList getStockList() {
        return versionedStockList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateStockListChanged() {
        raise(new StockListChangedEvent(versionedStockList));
    }

    //@@author kelvintankaiboon
    /** Raises an event to indicate that saveCommand has been called */
    private void indicateSaveStockListVersion(String fileName) {
        raise(new SaveStockListVersionEvent(versionedStockList, fileName));
    }

    @Override
    public void saveStockList(String fileName) {
        indicateSaveStockListVersion(fileName);
    }

    /** Raises an event to indicate that OpenCommand has been called */
    private void indicateOpenStockListVersion(String fileName) {
        raise(new OpenStockListVersionEvent(versionedStockList, fileName));
    }

    @Override
    public void openStockList(String fileName) {
        indicateOpenStockListVersion(fileName);
    }
    //@@author

    @Override
    public boolean hasItem(Item item) {
        requireNonNull(item);
        return versionedStockList.hasItem(item);
    }

    @Override
    public void deleteItem(Item target) {
        versionedStockList.removeItem(target);
        indicateStockListChanged();
    }

    @Override
    public void addItem(Item item) {
        versionedStockList.addItem(item);
        updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        indicateStockListChanged();
    }

    @Override
    public void updateItem(Item target, Item editedItem) {
        requireAllNonNull(target, editedItem);

        versionedStockList.updateItem(target, editedItem);
        indicateStockListChanged();
    }

    //=========== Filtered Item List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Item} backed by the internal list of
     * {@code versionedStockList}
     */
    @Override
    public ObservableList<Item> getFilteredItemList() {
        return FXCollections.unmodifiableObservableList(filteredItems);
    }

    @Override
    public void updateFilteredItemList(Predicate<Item> predicate) {
        requireNonNull(predicate);
        filteredItems.setPredicate(predicate);
    }

    //@@author gaoqikai
    @Override
    //update the stock list that is filtered by tag
    public void updateFilteredItemListByTag(Predicate<Item> predicate) {
        requireNonNull(predicate);
        filteredItems.setPredicate(predicate);
    }
    //@@author

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoStockList() {
        return versionedStockList.canUndo();
    }

    @Override
    public boolean canRedoStockList() {
        return versionedStockList.canRedo();
    }

    @Override
    public void undoStockList() {
        versionedStockList.undo();
        indicateStockListChanged();
    }

    @Override
    public void redoStockList() {
        versionedStockList.redo();
        indicateStockListChanged();
    }

    @Override
    public void commitStockList() {
        versionedStockList.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedStockList.equals(other.versionedStockList)
                && filteredItems.equals(other.filteredItems);
    }

    //=========== Account List =============================================================

    @Override
    public void resetAccountData(ReadOnlyAccountList newData) {
        versionedAccountList.resetData(newData);
        indicateAccountListChanged();
    }

    @Override
    public ReadOnlyAccountList getAccountList() {
        return versionedAccountList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAccountListChanged() {
        raise(new AccountListChangedEvent(versionedAccountList));
    }

    @Override
    public boolean hasAccount(Account account) {
        requireNonNull(account);
        return versionedAccountList.hasAccount(account);
    }

    @Override
    public void deleteAccount(Account target) {
        versionedAccountList.removeAccount(target);
        indicateAccountListChanged();
    }

    @Override
    public void addAccount(Account account) {
        versionedAccountList.addAccount(account);
        updateFilteredAccountList(PREDICATE_SHOW_ALL_ACCOUNTS);
        indicateAccountListChanged();
    }

    @Override
    public void updateAccount(Account target, Account editedAccount) {
        requireAllNonNull(target, editedAccount);

        versionedAccountList.updateAccount(target, editedAccount);
        indicateAccountListChanged();
    }

    //=========== Filtered Account List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Item} backed by the internal list of
     * {@code versionedAccountList}
     */
    @Override
    public ObservableList<Account> getFilteredAccountList() {
        return FXCollections.unmodifiableObservableList(filteredAccounts);
    }

    @Override
    public void updateFilteredAccountList(Predicate<Account> predicate) {
        requireNonNull(predicate);
        filteredAccounts.setPredicate(predicate);
    }

}
