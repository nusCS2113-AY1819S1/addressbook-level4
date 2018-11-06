package seedu.address.model.user.accountant;

import seedu.address.analysis.AnalysisPeriodType;
import seedu.address.model.Model;
import seedu.address.model.drink.Price;

/**
 * This is the API manager for accountant role
 */
public interface AccountantModel extends Model {
    /**
     * Returns the total cost incurred.
     */
    Price analyseCosts(AnalysisPeriodType period);

    /**
     * Returns the total revenue earned.
     */
    //    Price analyseRevenue();

    /**
     * Returns the total profit earned.
     */
    //    Price analyseProfit();

}
