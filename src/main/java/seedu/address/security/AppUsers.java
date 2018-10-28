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
        accountCredentials.add(new AccountCredential());
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
