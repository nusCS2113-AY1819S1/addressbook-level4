package seedu.address.model.user.accountant;

import seedu.address.analysis.Analysis;
import seedu.address.analysis.AnalysisManager;
import seedu.address.analysis.AnalysisPeriodType;
import seedu.address.analysis.PurchaseTransactionPredicate;
import seedu.address.analysis.SaleTransactionPredicate;
import seedu.address.model.LoginInfoManager;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyInventoryList;
import seedu.address.model.UserPrefs;
import seedu.address.model.drink.Price;
import seedu.address.model.transaction.ReadOnlyTransactionList;

/**
 * Represents the in-memory model of the accountant command
 */
public class AccountantModelManager extends ModelManager implements AccountantModel {
    private final Analysis analysis = new AnalysisManager(transactionList, filteredTransactions);

    public AccountantModelManager(ReadOnlyInventoryList inventoryList,
                                  UserPrefs userPrefs, LoginInfoManager loginInfoManager,
                                  ReadOnlyTransactionList transactionList) {
        super(inventoryList, userPrefs, loginInfoManager, transactionList);
    }

    @Override
    public Price analyseCosts(AnalysisPeriodType period) {
        updateFilteredTransactionListToShowPurchases(period);
        return analysis.analyseCost(period);
    }

    @Override
    public Price analyseRevenue(AnalysisPeriodType period) {
        updateFilteredTransactionListToShowSales(period);
        return analysis.analyseRevenue(period);
    }

    @Override
    public Price analyseProfit(AnalysisPeriodType period) {
        updateFilteredTransactionListToShowAll();
        return analysis.analyseProfit(period);
    }

    /**
     * Updates the {@code filteredTransactions} with Purchase predicate and {@code period} predicate.
     */
    private void updateFilteredTransactionListToShowPurchases(AnalysisPeriodType period) {
        updateFilteredTransactionList(period.getPeriodFilterPredicate().and(new PurchaseTransactionPredicate()));
    }

    /**
     * Updates the {@code filteredTransactions} with Sale predicate and {@code period} predicate.
     */
    private void updateFilteredTransactionListToShowSales(AnalysisPeriodType period) {
        updateFilteredTransactionList(period.getPeriodFilterPredicate().and(new SaleTransactionPredicate()));
    }

    private void updateFilteredTransactionListToShowAll() {
        updateFilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS);
    }
}
