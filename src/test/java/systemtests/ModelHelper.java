package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.login.Password;
import seedu.address.model.login.UniqueUserList;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.AuthenticatedException;
import seedu.address.model.login.exceptions.DuplicateUserException;
import seedu.address.model.product.Product;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Product> PREDICATE_MATCHING_NO_PERSONS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Product> toDisplay) {
        Optional<Predicate<Product>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredProductList(predicate.orElse(PREDICATE_MATCHING_NO_PERSONS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Product... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }
    /**
     * Updates the model to login as a valid user
     */
    public static void loginAs(Model model, Username username, Password password) {
        try {
            model.checkAuthentication(username, password);
        } catch (AuthenticatedException e) {
            throw new AssertionError("not possible");
        }
    }

    /**
     * Updates {@code model}'s users list.
     */
    public static void setUsersList(Model model, UniqueUserList uniqueUserList) {
        model.setUsersList(uniqueUserList);
    }

    /**
     * @see ModelHelper#setUsersList(Model, UniqueUserList)
     */
    public static void setUsersList(Model model, User... users) {
        UniqueUserList uniqueUserList = new UniqueUserList();
        for (User user : users) {
            try {
                uniqueUserList.add(user);
            } catch (DuplicateUserException e) {
                throw new AssertionError("not possible");
            }
        }
        setUsersList(model, uniqueUserList);
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Product} equals to {@code other}.
     */
    private static Predicate<Product> getPredicateMatching(Product other) {
        return product -> product.equals(other);
    }
}
