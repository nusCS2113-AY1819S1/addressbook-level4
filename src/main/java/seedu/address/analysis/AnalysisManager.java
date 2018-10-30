package seedu.address.analysis;

import seedu.address.model.drink.Price;
import seedu.address.model.transaction.TransactionList;

/**
 * Represents functions to analyse profit, revenue, cost, quantity sold.
 */
public class AnalysisManager extends ComponentManager implements Analysis {
    private TransactionList transactionList;

    public AnalysisManager(TransactionList transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public Price analyseProfit() {
        // TODO: stub
        return null;

    }

    @Override
    public Price analyseCost() {

        // TODO: stub
        return null;

    }

    @Override
    public Price analyseRevenue() {

        // TODO: stub
        return null;
    }





}
