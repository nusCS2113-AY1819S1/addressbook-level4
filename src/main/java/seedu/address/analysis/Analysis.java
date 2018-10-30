package seedu.address.analysis;

import seedu.address.model.drink.Price;

/**
 * APIs for AnalysisManager (analyses of profit, revenue, cost, quantity sold)
 */
public interface AnalysisApi {
    public Price analyseProfit();

    public Price analyseCost();

    public Price analyseRevenue();

    // public Stock analyseQuantitiesSold();

}
