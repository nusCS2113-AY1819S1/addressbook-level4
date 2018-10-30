package seedu.address.model.user.stocktaker;

import seedu.address.model.LoginInfoManager;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyInventoryList;
import seedu.address.model.UserPrefs;
import seedu.address.model.transaction.TransactionList;

/**
 * The solid class for API of the stock taker
 */
public class StockTakerModelManager extends ModelManager implements StockTakerModel {
    public StockTakerModelManager (ReadOnlyInventoryList inventoryList, UserPrefs userPrefs,
                                   LoginInfoManager loginInfoManager, TransactionList transactionList) {
        super(inventoryList, userPrefs, loginInfoManager, transactionList);
    }
}
