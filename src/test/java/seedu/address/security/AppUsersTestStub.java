package seedu.address.security;

import java.util.ArrayList;

/**
 * Test Class to instantiate AppUsers with one account of username "test" and password "test"
 */
public class AppUsersTestStub extends AppUsers {
    private ArrayList<AccountCredential> userlist;

    public AppUsersTestStub() {
        super();
        userlist = new ArrayList<>();
        userlist.add(new AccountCredential());
        updateAccountCredentials(userlist);
    }
}
