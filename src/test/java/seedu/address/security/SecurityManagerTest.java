package seedu.address.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.TEST;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.security.SuccessfulLoginEvent;
import seedu.address.commons.events.security.UnsuccessfulLoginEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.User;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.AddressBookBuilder;


public class SecurityManagerTest {

    private static SecurityManager securityManager;
    private static Model model;
    private static Logic logic;
    private static UserPrefs userPrefs;
    private static AppUsers appUsers;

    private static boolean succcessfulLoginEventCalled;
    private static boolean unsuccessfulLoginEventCalled;

    public SecurityManagerTest() {
        registerAsAnEventHandler(this);
    }

    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }

    //Set up subscribe to test whether raise events are successfully raised and subscribed
    @Subscribe
    public void handleSuccessfulLoginEvent(SuccessfulLoginEvent loginSuccess) {
        this.succcessfulLoginEventCalled = true;
    }

    @Subscribe
    public void handleUnsuccessfulLoginEvent(UnsuccessfulLoginEvent loginFailure) {
        this.unsuccessfulLoginEventCalled = true;
    }

    @BeforeClass
    public static void testFixtureSetup() {
        succcessfulLoginEventCalled = false;
        unsuccessfulLoginEventCalled = false;
        userPrefs = new UserPrefs();
        appUsers = new AppUsersTestStub();
        AddressBook addressBook = new AddressBookBuilder().withPerson(TEST).build();

        model = new ModelManager(addressBook, userPrefs);
        logic = new LogicManager(model);

        securityManager = new SecurityManager(false, logic, appUsers);
    }

    @Before
    public void resetFixture() {
        succcessfulLoginEventCalled = false;
        unsuccessfulLoginEventCalled = false;
        securityManager.logout();
    }

    @Test
    public void login_correctCredentials_isAuthenticatedToTrue() {
        securityManager.login("test", "test");
        assertTrue(securityManager.getAuthentication());
    }

    @Test
    public void login_incorrectCredentials_isAuthenticatedRemainsFalse() {
        securityManager.login("test", "test1");
        assertFalse(securityManager.getAuthentication());
    }

    @Test
    public void login_correctCredentials_raisedSuccessfulLoginEvent() {
        securityManager.login("test", "test");
        assertTrue(this.succcessfulLoginEventCalled);
        assertFalse(this.unsuccessfulLoginEventCalled);
    }

    @Ignore("Ignore till correction exceptions can be raised")
    @Test
    public void login_incorrectCredentials_raisedUnsuccessfulLoginEvent() {
        securityManager.login("test", "test1");
        assertFalse(this.succcessfulLoginEventCalled);
        assertTrue(this.unsuccessfulLoginEventCalled);
    }

    @Test
    public void logout_isAuthenticatedToFalse() {
        securityManager.login("test", "test");
        securityManager.logout();
        assertFalse(securityManager.getAuthentication());
    }

    @Test
    public void register_existingUser_returnExistingUserExists() {
        boolean testFlag = false;
        if (securityManager.register("test", "test",
                "test@test.com", "88888888", "Lucky Road") == RegisterFlag.USER_ALREADY_EXISTS) {
            testFlag = true;
        }
        assertTrue(testFlag);
    }

    @Test
    public void register_incompleteAddress_returnIncompleteField() {
        boolean testFlag = false;
        if (securityManager.register("test1", "test",
                "test", "8888888", "") == RegisterFlag.INCOMPLETE_FIELD) {
            testFlag = true;
        }
        assertTrue(testFlag);
    }

    @Test
    public void register_validCompleteFields_returnSuccess() {
        boolean testFlag = false;
        if (securityManager.register("test1", "test",
                "test@test.com", "88888888", "Testy Road") == RegisterFlag.SUCCESS) {
            testFlag = true;
        }
        assertTrue(testFlag);
    }

    @Test
    public void getUser_correctUserReturned() {
        securityManager.login("test", "test");
        User currentUser = securityManager.getUser();
        String userName = currentUser.getName().toString();
        assertEquals("test", userName);
    }

    @Test
    public void getUser_afterLoggedOut_nullReturnedInsteadOfUser() {
        securityManager.login("test", "test");
        securityManager.logout();
        User currentUser = securityManager.getUser();
        assertNull(currentUser);
    }
}
