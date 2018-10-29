package seedu.address.model.user.admin;

import seedu.address.model.LoginInfoManager;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyInventoryList;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.AuthenticationLevel;
import seedu.address.model.user.Password;
import seedu.address.model.user.UserName;

/**
 * This is the API model for Admin command
 */
public class AdminModelManager extends ModelManager implements AdminModel {
    public AdminModelManager(ReadOnlyInventoryList inventoryList, UserPrefs userPrefs,
                             LoginInfoManager loginInfoManager) {
        super(inventoryList, userPrefs, loginInfoManager);
    }

    @Override
    public boolean isValid () {
        return false;
    }

    @Override
    public void createNewAccount (UserName userName, Password password, AuthenticationLevel authenticationLevel) {
        loginInfoManager.createNewAccount (userName, password, authenticationLevel);
    }

    @Override
    public void deleteAccount (UserName userName) {
        loginInfoManager.deleteAccount (userName);
    }
}
