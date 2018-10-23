package seedu.address.model.user.stocktaker;

import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

/**
 * The solid class for API of the stock taker
 */
public class StockTakerModelManager extends ModelManager implements StockTakerModel {
    public StockTakerModelManager (ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super(addressBook, userPrefs);
    }
}
