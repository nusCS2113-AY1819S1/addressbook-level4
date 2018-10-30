package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.analysis.Analysis;
import seedu.address.analysis.AnalysisManager;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LoginInfo;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.InventoryListChangedEvent;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.Price;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionList;
import seedu.address.model.user.Password;
import seedu.address.model.user.UserName;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    protected LoginInfoManager loginInfoManager;
    private final FilteredList<Drink> filteredDrinks;
    private final InventoryList inventoryList;
    private final TransactionList transactionList;
    private final Analysis analysis;

    /**
     * Initializes a ModelManager with the given inventoryList, userPrefs and transactionList
     */
    public ModelManager(ReadOnlyInventoryList inventoryList, UserPrefs userPrefs, LoginInfoManager loginInfoManager,
                        TransactionList transactionList) {
        super();
        requireAllNonNull(inventoryList, userPrefs);

        logger.fine("Initializing with inventory list: " + inventoryList + " and user prefs " + userPrefs);

        this.inventoryList = new InventoryList(inventoryList);
        filteredDrinks = new FilteredList<>(inventoryList.getDrinkList());
        this.loginInfoManager = loginInfoManager;
        this.transactionList = transactionList;
        analysis = new AnalysisManager(transactionList);
        // TODO: transaction manager, facade for transactions
    }

    public ModelManager() {
        this(new InventoryList(), new UserPrefs(), new LoginInfoManager(), new TransactionList());
    }

    @Override
    public void resetData(ReadOnlyInventoryList newData) {
        inventoryList.resetData(newData);
        indicateInventoryListChanged();
    }

    @Override
    public ReadOnlyInventoryList getInventoryList() {
        return inventoryList;
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateInventoryListChanged() {
        raise(new InventoryListChangedEvent(inventoryList));
    }

    @Override
    public boolean hasDrink(Drink drink) {
        requireNonNull(drink);
        return inventoryList.hasDrink(drink);
    }

    @Override
    public void deleteDrink(Drink target) {
        inventoryList.removeDrink(target);
        indicateInventoryListChanged();
    }

    @Override
    public void addDrink(Drink drink) {
        inventoryList.addDrink(drink);
        updateFilteredDrinkList(PREDICATE_SHOW_ALL_DRINKS);
        indicateInventoryListChanged();
    }

    /*
    @Override
    public void updateDrink(Drink target, Drink editedDrink) {
        requireAllNonNull(target, editedDrink);

        inventoryList.updateDrink(target, editedDrink);
        indicateInventoryListChanged();
    }
    */

    private Drink findDrinkByName(Drink drink) {
        Drink actualDrinkRef = inventoryList.findDrinkByName(drink);
        return actualDrinkRef;
    }

    //=========== Filtered Drink List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Drink} backed by the internal list of
     * {@code inventoryList}
     */
    @Override
    public ObservableList<Drink> getFilteredDrinkList() {
        return FXCollections.unmodifiableObservableList(filteredDrinks);
    }

    @Override
    public void updateFilteredDrinkList(Predicate<Drink> predicate) {
        requireNonNull(predicate);
        filteredDrinks.setPredicate(predicate);
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
        return inventoryList.equals(other.inventoryList)
                && inventoryList.equals(other.inventoryList);
    }

    // ========== transaction commands ====================================
    @Override
    public void sellDrink(Transaction transaction) {
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

    // ========== Analysis commands =================================================
    @Override
    public Price analyseCosts() {
        return analysis.analyseCost();
    }



    //=========== Login feature command ==============================================

    @Override
    public void changePassword(UserName userName, Password newHashedPassword) {
        loginInfoManager.changePassword(userName, newHashedPassword);
    }

    @Override
    public LoginInfo getLoginInfo(UserName userName) {
        return loginInfoManager.getLoginInfo(userName);
    }

    @Override
    public boolean isUserNameExist(UserName userName) {
        return loginInfoManager.isUserNameExist(userName);
    }

}
