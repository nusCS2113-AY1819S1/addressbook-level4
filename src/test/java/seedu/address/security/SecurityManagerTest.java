package seedu.address.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.security.SuccessfulLoginEvent;
import seedu.address.commons.events.security.UnsuccessfulLoginEvent;


public class SecurityManagerTest {

    private SecurityManager securityManager = new SecurityManager(false);
    private boolean succcessfulLoginEventCalled = false;
    private boolean unsuccessfulLoginEventCalled = false;

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

    @Test
    public void login_incorrectCredentials_raisedUnsuccessfulLoginEvent() {
        securityManager.login("test", "test1");
        assertFalse(this.succcessfulLoginEventCalled);
        assertTrue(this.unsuccessfulLoginEventCalled);
    }
}
