package seedu.address.model.user.manager;

import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

/**
 * Contain api that is usable for manager role
 */
public class ManagerModelManager extends ModelManager implements ManagerModel {
    public ManagerModelManager (ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super(addressBook, userPrefs);
    }
}
