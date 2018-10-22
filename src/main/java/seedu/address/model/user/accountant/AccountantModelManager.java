package seedu.address.model.user.accountant;

import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

public class AccountantModelManager extends ModelManager implements AccountantModel{
    public AccountantModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super(addressBook, userPrefs);
    }

}
