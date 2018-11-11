package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AccountList;
import seedu.address.model.account.Account;

/**
 * A utility class containing a list of {@code Item} objects to be used in tests.
 */
public class TypicalAccounts {

    // Manually added
    public static final Account DAVIN = new AccountBuilder().withUsername("Davin")
            .withPassword("12345")
            .build();

    public static final Account KINWHYE = new AccountBuilder().withUsername("KinWhye")
            .withPassword("haha")
            .build();

    public static final Account JALIL = new AccountBuilder().withUsername("Jalil")
            .withPassword("nopassword")
            .build();

    //public static final String KEYWORD_MATCHING_AR = "ar"; // A keyword that matches "ar"

    private TypicalAccounts() {} // prevents instantiation

    /**
     * Returns an {@code AccountList} with all the typical accounts.
     */

    public static AccountList getTypicalAccountList() {
        AccountList ab = new AccountList();
        for (Account account : getTypicalAccounts()) {
            ab.addAccount(account);
        }
        return ab;
    }

    public static List<Account> getTypicalAccounts() {
        return new ArrayList<>(Arrays.asList(DAVIN, KINWHYE, JALIL));
    }


}
