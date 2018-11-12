package seedu.address.analysis;

import seedu.address.model.drink.Price;

/**
 * APIs for AnalysisManager (analyses of profit, revenue, cost, quantity sold)
 */
public interface Analysis {
    Price analyseProfit();

    Price analyseCost();

    Price analyseRevenue();

    // public Stock analyseQuantitiesSold();

}
