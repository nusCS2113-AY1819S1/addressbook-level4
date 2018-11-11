package seedu.address.security;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Represents FreeTime Registered Users
 */
public class AppUsers {

    private ArrayList<AccountCredential> accountCredentials;

    public AppUsers() {
        this.accountCredentials = new ArrayList<>();
        //Adding accounts when users.json is not found
        accountCredentials.add(new AccountCredential());
        accountCredentials.add(new AccountCredential("NF", "test"));
        accountCredentials.add(new AccountCredential("Ben", "test"));
        accountCredentials.add(new AccountCredential("Jasper", "test"));
        accountCredentials.add(new AccountCredential("Alexis", "test"));
    }

    public ArrayList<AccountCredential> getAccountCredentials() {
        return accountCredentials;
    }

    public void updateAccountCredentials(ArrayList<AccountCredential> list) {
        this.accountCredentials = list;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AppUsers)) { //this handles null as well.
            return false;
        }

        AppUsers o = (AppUsers) other;

        return Objects.equals(accountCredentials, o.accountCredentials);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountCredentials);
    }
}
