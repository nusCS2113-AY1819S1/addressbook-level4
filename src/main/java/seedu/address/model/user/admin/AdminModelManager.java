package seedu.address.model.user.admin;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.analysis.AnalysisPeriodType;
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
import seedu.address.model.user.AuthenticationLevel;
import seedu.address.model.user.Password;
import seedu.address.model.user.UserName;

/**
 * This is the API model for Admin command
 */
public class AdminModelManager extends ModelManager implements AdminModel {
    public AdminModelManager(ReadOnlyInventoryList inventoryList, UserPrefs userPrefs,
                             LoginInfoManager loginInfoManager, TransactionList transactionList) {
        super(inventoryList, userPrefs, loginInfoManager, transactionList);
    }


    /**
     * Raises an event to indicate the model has changed
     */
    protected void indicateDrinkAttributesChanged(Drink drink) {
        raise(new DrinkAttributeChangedEvent(drink));
    }
    //===============manager command====================//
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

    //=====================Stock taker commands====================
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

    //=====================Manager command=========================
    @Override
    public void createNewAccount (UserName userName, Password password, AuthenticationLevel authenticationLevel) {
        loginInfoManager.createNewAccount (userName, password, authenticationLevel);
    }

    @Override
    public void deleteAccount (UserName userName) {
        loginInfoManager.deleteAccount (userName);
    }

    //=====================Accountant command======================
    @Override
    public Price analyseCosts(AnalysisPeriodType period) {
        return analysis.analyseCost(period);
    }
    /*
        @Override
        public Price analyseRevenue() {
            return analysis.analyseRevenue();
        }
        @Override
        public Price analyseProfit() {
            return analysis.analyseProfit();
        }
    */
}
