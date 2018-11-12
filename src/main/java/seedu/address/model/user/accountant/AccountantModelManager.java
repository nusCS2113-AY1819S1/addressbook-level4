package seedu.address.model.user.accountant;

import seedu.address.analysis.Analysis;
import seedu.address.analysis.AnalysisManager;
import seedu.address.analysis.PurchaseTransactionPredicate;
import seedu.address.analysis.SaleTransactionPredicate;
import seedu.address.analysis.TransactionPeriodPredicate;
import seedu.address.model.LoginInfoModel;
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
                                  UserPrefs userPrefs, LoginInfoModel loginInfoModel,
                                  ReadOnlyTransactionList transactionList) {
        super(inventoryList, userPrefs, loginInfoModel, transactionList);
    }

    @Override
    public Price analyseCosts(TransactionPeriodPredicate period) {
        updateFilteredTransactionListToShowPurchases(period);
        return analysis.analyseCost();
    }

    @Override
    public Price analyseRevenue(TransactionPeriodPredicate period) {
        updateFilteredTransactionListToShowSales(period);
        return analysis.analyseRevenue();
    }

    @Override
    public Price analyseProfit(TransactionPeriodPredicate period) {
        updateFilteredTransactionListToShowProfitPeriod(period);
        return analysis.analyseProfit();
    }

    /**
     * Updates the {@code filteredTransactions} with Purchase predicate and {@code period} predicate.
     */
    private void updateFilteredTransactionListToShowPurchases(TransactionPeriodPredicate period) {
        updateFilteredTransactionList(period.and(new PurchaseTransactionPredicate()));
        indicateTransactionListChanged();
    }

    /**
     * Updates the {@code filteredTransactions} with Sale predicate and {@code period} predicate.
     */
    private void updateFilteredTransactionListToShowSales(TransactionPeriodPredicate period) {
        updateFilteredTransactionList(period.and(new SaleTransactionPredicate()));
        indicateTransactionListChanged();
    }

    /**
     * Updates the {@code filteredTransactions} with {@code period} predicate.
     * For use by Profit analysis.
     */
    private void updateFilteredTransactionListToShowProfitPeriod(TransactionPeriodPredicate period) {
        updateFilteredTransactionList(period);
        indicateTransactionListChanged();
    }

    private void updateFilteredTransactionListToShowAll() {
        updateFilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS);
        indicateTransactionListChanged();
    }
}
