package seedu.address.analysis;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.model.drink.Price;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionList;
import seedu.address.model.transaction.TransactionType;

/**
 * Represents functions to analyse profit, revenue, cost, quantity sold.
 */
public class AnalysisManager extends ComponentManager implements Analysis {
    private TransactionList transactionList;
    private FilteredList<Transaction> filteredTransactions;

    public AnalysisManager(TransactionList transactionList) {
        this.transactionList = transactionList;
        filteredTransactions = new FilteredList<>(transactionList.getTransactionsAsObservableList());
    }

    @Override
    public Price analyseProfit(AnalysisPeriodType period) {
        updateFilteredTransactionsList(period.periodFilterPredicate());
        // return transactionList.calculateTotalProfit();
        return null;
    }

    @Override
    public Price analyseCost(AnalysisPeriodType period) {
        updateFilteredTransactionsList(period.periodFilterPredicate());
        return calculateTotalCost();
    }

    @Override
    public Price analyseRevenue(AnalysisPeriodType period) {
        // return transactionList.calculateTotalRevenue();
        return null;
    }

    private void updateFilteredTransactionsList(Predicate<Transaction> periodPredicate) {
        requireNonNull(periodPredicate);
        filteredTransactions.setPredicate(periodPredicate);
    }

    /**
     * Calculates the total cost of all the transactions.
     * @return total cost incurred for all transactions
     */
    private Price calculateTotalCost() {
        float totalCost = 0;
        for (Transaction transaction : filteredTransactions) {
            if (transaction.getTransactionType() == TransactionType.PURCHASE) {
                totalCost += transaction.getAmountMoney().getValue();
            }
        }

        return new Price(Float.toString(totalCost));
    }
}
