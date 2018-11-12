package seedu.address.model.user.accountant;

import seedu.address.analysis.TransactionPeriodPredicate;
import seedu.address.model.Model;
import seedu.address.model.drink.Price;

/**
 * This is the API manager for accountant role
 */
public interface AccountantModel extends Model {
    /**
     * Returns the total cost incurred in specified period.
     */
    Price analyseCosts(TransactionPeriodPredicate period);

    /**
     * Returns the total revenue earned in specified period.
     */
    Price analyseRevenue(TransactionPeriodPredicate period);

    /**
     * Returns the total profit earned in specified period.
     */
    Price analyseProfit(TransactionPeriodPredicate period);

}
