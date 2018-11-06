//@@author liu-tianhang
package seedu.address.authentication;

import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.CurrentUser;
import seedu.address.commons.core.LoginInfo;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.LoginInfoManager;
import seedu.address.model.user.Password;
import seedu.address.model.user.UserName;

/**
 * Contains utility methods used for login check
 */
public class LoginUtils {
    private LoginInfoManager loginInfoManager;
    private LoginInfo userInfoInStorage;
    private UserName username;
    private Password password;
    private final Logger logger = LogsCenter.getLogger(LoginUtils.class);

    public LoginUtils(UserName username, Password password, LoginInfoManager loginInfoManager) {
        this.username = username;
        this.password = password;
        this.loginInfoManager = loginInfoManager;
    }
    /**
     * Check the password and username with logininfo list
     */
    public boolean isPasswordAndUserNameValid () {
        if (getLoginInfoFromStorage ().isPresent ()) {
            userInfoInStorage = getLoginInfoFromStorage().get();
        } else {
            return false;
        }
        if (isPasswordCorrect()) {
            setCurrentUser();
            return true;
        }
        return false;
    }

    /**
     * Returns true if able to find same username in storage
     */
    private Optional<LoginInfo> getLoginInfoFromStorage() {
        LoginInfo userInfoInStorage = loginInfoManager.getLoginInfo (username);
        if (userInfoInStorage == null) {
            return Optional.empty ();
        }
        return Optional.of(userInfoInStorage);
    }

    /**
     * Return true is password is correct
     */
    private boolean isPasswordCorrect() {
        String securePassword = userInfoInStorage.getPasswordString ();
        return PasswordUtils.verifyUserPassword(password.toString (), securePassword);
    }
    /**
     * Set current User
     */
    private void setCurrentUser() {
        CurrentUser.setLoginInfo (username, userInfoInStorage.getAuthenticationLevel ());
        logger.info (String.format ("User has login with user name : " + CurrentUser.getUserName ()));
    }
}
