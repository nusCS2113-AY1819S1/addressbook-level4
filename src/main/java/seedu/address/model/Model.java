package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LoginInfo;
import seedu.address.model.drink.Drink;
import seedu.address.model.transaction.ReadOnlyTransactionList;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.user.Password;
import seedu.address.model.user.UserName;

/**
 * The API of the Model component.
 */
public interface Model {


    //==============Drink Model===========================//
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Drink> PREDICATE_SHOW_ALL_DRINKS = unused -> true;
    Predicate<Transaction> PREDICATE_SHOW_ALL_TRANSACTIONS = unused -> true;

    Predicate<Drink> PREDICATE_SHOW_NO_DRINKS = unused -> false;

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyInventoryList newData);

    /**
     * Returns the inventoryList
     */
    ReadOnlyInventoryList getInventoryList();

    /**
     * Returns true if a drink with the same identity as {@code drink} exists in the address book.
     */
    boolean hasDrink(Drink drink);


    // ================ filtered drinks =======================

    /**
     * Returns an unmodifiable view of the filtered inventory list
     */
    ObservableList<Drink> getFilteredDrinkList();
    //=============common command==========================/

    /**
     * Updates the filter of the filtered drink list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDrinkList(Predicate<Drink> predicate);


    // ================ transactions =========================

    /**
     * Returns the transactionList
     */
    ReadOnlyTransactionList getTransactionList();


    /**
     * Returns an unmodifiable view of the filtered transaction list
     */
    ObservableList<Transaction> getFilteredTransactionList();

    /**
     * Updates the filter of the filtered drink list
     *
     * @param predicate
     */
    void updateFilteredTransactionList(Predicate<Transaction> predicate);


    //================= login function command ========================//

    /**
     * change user password based on {@code userName} and {@code newHashedPassword}
     */
    void changePassword(UserName userName, Password newHashedPassword);

    /**
     * Get user loginInfo base on userName
     */
    LoginInfo getLoginInfo(UserName userName);

    /**
     * Check a userName exist in list
     */
    boolean isUserNameExist(UserName userName);


}
