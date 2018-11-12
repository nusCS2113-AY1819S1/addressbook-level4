package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AccountList;
import seedu.address.model.account.Account;

/**
 * A utility class containing a list of {@code Account} objects to be used in tests.
 */
public class TypicalAccounts {

    // Manually added
    public static final Account ADMIN = new AccountBuilder().withUsername("admin")
            .withPassword("admin").build();

    public static final Account JOHN = new AccountBuilder().withUsername("john")
            .withPassword("doe").build();

    public static final String KEYWORD_MATCHING_JO = "jo"; // A keyword that matches "jo"

    private TypicalAccounts() {} // prevents instantiation

    /**
     * Returns an {@code AccountList} with all the typical accounts.
     */
    public static AccountList getTypicalAccountList() {
        AccountList al = new AccountList();
        for (Account account : getTypicalAccounts()) {
            al.addAccount(account);
        }
        return al;
    }


    public static List<Account> getTypicalAccounts() {
        return new ArrayList<>(Arrays.asList(ADMIN, JOHN));
    }


}
