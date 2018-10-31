package seedu.address.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AppUsersTest {

    private static AppUsers appUsers;
    private static ArrayList<AccountCredential> list;
    private static ArrayList<AccountCredential> fakelist;

    @BeforeClass
    public static void testFixtureSetup() {
        list = new ArrayList<>();
        fakelist = new ArrayList<>();
        appUsers = new AppUsers();
    }

    @Before
    public void resetFixture() {
        list.clear();
        list.add(new AccountCredential("test", "test"));
    }

    @Test
    public void getAccountCredentials_correctListUpdatedAndReturned() {
        appUsers.updateAccountCredentials(list);
        assertTrue(appUsers.getAccountCredentials().equals(list));
    }

    @Test
    public void getAccountCredentials_correctListUpdatedTwiceAndReturned() {
        list.add(new AccountCredential("test1", "test"));
        appUsers.updateAccountCredentials(list);
        assertTrue(appUsers.getAccountCredentials().equals(list));
    }

    @Test
    public void getAccountCredentials_incorrectListReturned() {
        appUsers.updateAccountCredentials(list);
        assertFalse(appUsers.getAccountCredentials().equals(fakelist));
    }

}
