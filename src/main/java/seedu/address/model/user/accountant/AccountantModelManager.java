package seedu.address.model.user.accountant;

import seedu.address.model.LoginInfoManager;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyInventoryList;
import seedu.address.model.UserPrefs;

/**
 * Represents the in-memory model of the accountant command
 */
public class AccountantModelManager extends ModelManager implements AccountantModel {
    public AccountantModelManager(ReadOnlyInventoryList inventoryList ,
                                  UserPrefs userPrefs, LoginInfoManager loginInfoManager) {
        super(inventoryList, userPrefs, loginInfoManager);
    }

}
