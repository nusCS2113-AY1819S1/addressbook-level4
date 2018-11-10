package seedu.address.model.user.manager;

import java.util.Set;

import seedu.address.commons.core.LoginInfo;
import seedu.address.model.Model;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.Price;
import seedu.address.model.tag.Tag;
import seedu.address.model.user.UserName;

/**
 * The interface for ManagerModel which control the api for Manager role
 */
public interface ManagerModel extends Model {
    //=========== Login feature command========================================

    /**
     * Create account based on {@code userName} {@code password} {@code authenticationLevel}
     */
    void createNewAccount(LoginInfo loginInfo);

    /**
     * Delete a account based on the name
     */
    void deleteAccount(UserName userName);

    //=============drink command===================================//
    /**
     * Deletes the given drink.
     * The drink must exist in the inventory list.
     */
    void deleteDrink(Drink target);

    /**
     * Adds the given drink.
     * {@code drink} must not already exist in the address book.
     */
    void addDrink(Drink drink);

    // ================ EDIT DRINK DETAILS COMMANDS =========================

    /**
     * Replaces the selling price of {@code drinkToEdit} with {@code newSellingPrice}
     */
    void updateSellingPrice(Drink drinkToEdit, Price newSellingPrice);

    /**
     * Replaces the cost price of {@code drinkToEdit} with {@code newCostPrice}
     */
    void updateCostPrice(Drink drinkToEdit, Price newCostPrice);

    /**
     * Replaces the tags of {@code drinkToEdit} with {@code newTags}
     */
    void updateTags(Drink drinkToEdit, Set<Tag> newTags);
}
