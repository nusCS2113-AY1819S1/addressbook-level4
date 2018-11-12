package systemtests;

import seedu.address.session.SessionManager;

//@@author jylee-git
/**
 * Enables short-circuit login using with a NRIC number, to facilitate in operations that require admin rights.
 */
public class SessionHelper extends SessionManager {

    /**
     * Force login with a NRIC with highest priority level, to enable operations
     * that require admin rights.
     */
    public static void forceLoginWithPriorityLevelOf(int priorityLevel) {
        forceLoginWith("F9999999P", priorityLevel);
    }

    public static void forceLoginWithPriorityLevelOf(String nric, int priorityLevel) {
        forceLoginWith(nric, priorityLevel);
    }

    public static void logoutOfSession() {
        SessionManager.forceLogout();
    }
}
