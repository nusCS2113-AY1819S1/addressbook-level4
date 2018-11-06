package seedu.address.analysis;

import seedu.address.model.drink.Price;

/**
 * APIs for AnalysisManager (analyses of profit, revenue, cost, quantity sold)
 */
public interface Analysis {
    Price analyseProfit(AnalysisPeriodType period);

    Price analyseCost(AnalysisPeriodType period);

    Price analyseRevenue(AnalysisPeriodType period);

    // public Stock analyseQuantitiesSold();

}
