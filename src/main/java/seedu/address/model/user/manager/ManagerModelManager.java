package seedu.address.model.user.manager;

import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

public class ManagerModelManager extends ModelManager implements ManagerModel {
    public ManagerModelManager (ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super(addressBook, userPrefs);
    }
}
