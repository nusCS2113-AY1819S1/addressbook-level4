package seedu.address.model.user.accountant;

import seedu.address.analysis.AnalysisPeriodType;
import seedu.address.model.LoginInfoManager;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyInventoryList;
import seedu.address.model.UserPrefs;
import seedu.address.model.drink.Price;
import seedu.address.model.transaction.TransactionList;

/**
 * Represents the in-memory model of the accountant command
 */
public class AccountantModelManager extends ModelManager implements AccountantModel {
    public AccountantModelManager(ReadOnlyInventoryList inventoryList,
                                  UserPrefs userPrefs, LoginInfoManager loginInfoManager,
                                  TransactionList transactionList) {
        super(inventoryList, userPrefs, loginInfoManager, transactionList);
    }

    @Override
    public Price analyseCosts(AnalysisPeriodType period) {
        return analysis.analyseCost(period);
    }
    /*
        @Override
        public Price analyseRevenue(AnalysisPeriodType period) {
            return analysis.analyseRevenue(period);
        }
        @Override
        public Price analyseProfit(AnalysisPeriodType period) {
            return analysis.analyseProfit(period);
        }
    */
}
