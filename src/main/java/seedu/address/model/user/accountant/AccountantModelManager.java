package seedu.address.model.user.accountant;

import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

/**
 * Represents the in-memory model of the accountant command
 */
public class AccountantModelManager extends ModelManager implements AccountantModel {
    public AccountantModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super(addressBook, userPrefs);
    }

}
