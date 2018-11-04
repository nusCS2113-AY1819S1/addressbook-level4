package seedu.address.model.util;

import seedu.address.model.AccountList;
import seedu.address.model.ReadOnlyAccountList;
import seedu.address.model.account.Account;
import seedu.address.model.account.Password;
import seedu.address.model.account.Username;

/**
 * Contains utility methods for populating {@code AccountList} with sample data.
 */
public class SampleAccountDataUtil {
    public static Account[] getSampleAccounts() {
        return new Account[] {
            new Account(new Username("admin"), new Password("admin"))
        };
    }

    public static ReadOnlyAccountList getSampleAccountList() {
        AccountList sampleSl = new AccountList();
        for (Account sampleAccount : getSampleAccounts()) {
            sampleSl.addAccount(sampleAccount);
        }
        return sampleSl;
    }

}
