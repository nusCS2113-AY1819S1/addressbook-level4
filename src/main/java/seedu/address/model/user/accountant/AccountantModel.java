package seedu.address.model.user.accountant;

import seedu.address.model.Model;
import seedu.address.model.drink.Price;

/**
 * This is the API manager for accountant role
 */
public interface AccountantModel extends Model {
    /**
     * Returns the total cost incurred.
     */
    Price analyseCosts();

}
