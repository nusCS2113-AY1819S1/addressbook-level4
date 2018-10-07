package seedu.address.logic.parser.validationCheck;

import seedu.address.commons.core.LoginInfo;

/**
 * Reresent the interface that check for user Authentication
 */
public interface UserAuthenticationCheck {

    /**
     * Check user Authetication from {@code CurrentUser}
     * @param loginInfo
     * @return true if user has the right to
     */
    public String checkAuthentication();
    public boolean isAuthenticationValid(LoginInfo loginInfo);
}
