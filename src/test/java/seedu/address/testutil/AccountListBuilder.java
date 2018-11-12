package seedu.address.testutil;

import seedu.address.model.AccountList;
import seedu.address.model.account.Account;

/**
 * A utility class to help with building Accountlist objects.
 * Example usage: <br>
 *     {@code AccountList al = new AccountListBuilder().withAccount(ADMIN).build();}
 */
public class AccountListBuilder {

    private AccountList accountList;

    public AccountListBuilder() {
        accountList = new AccountList();
    }

    public AccountListBuilder(AccountList accountList) {
        this.accountList = accountList;
    }

    /**
     * Adds a new {@code Account} to the {@code AccountList} that we are building.
     */
    public AccountListBuilder withAccount(Account account) {
        accountList.addAccount(account);
        return this;
    }

    public AccountList build() {
        return accountList;
    }
}
