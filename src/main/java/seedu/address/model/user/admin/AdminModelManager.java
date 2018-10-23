package seedu.address.model.user.admin;

import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

public class AdminModelManager extends ModelManager implements AdminModel{
    public AdminModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super(addressBook, userPrefs);
    }

    @Override
    public boolean isValid () {
        return false;
    }
}
