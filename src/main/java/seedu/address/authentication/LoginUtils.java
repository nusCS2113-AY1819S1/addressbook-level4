package seedu.address.authentication;

import java.util.logging.Logger;

import seedu.address.commons.core.CurrentUser;
import seedu.address.commons.core.LoginInfo;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.LoginInfoManager;
import seedu.address.model.user.Password;
import seedu.address.model.user.UserName;

/**
 * Contains utility methods used for login check d
 */
public class LoginUtils {
    private LoginInfoManager loginInfoManager;

    private UserName username;
    private Password password;
    private final Logger logger = LogsCenter.getLogger(LoginUtils.class);

    public LoginUtils(String username, String password, LoginInfoManager loginInfoManager) {
        this.username = new UserName (username);
        this.password = new Password (password);
        this.loginInfoManager = loginInfoManager;
    }
    /**
     * Returns validity of username
     * If usernameField is empty, return false else return true
     *
     * @return boolean value
     */
    public boolean isUsernameEmpty () {
        if (username.equals ("")) {
            return false;
        }
        return true;
    }
    /**
     * Returns validity of password.
     * If passwordField is empty, return false else return true.
     *
     * @return boolean value
     */
    public boolean isPasswordEmpty () {
        if (password.equals ("")) {
            return false;
        }
        return true;
    }

    /**
     * check the password and username with logininfo list
     */
    public boolean isPasswordAndUserNameValid () {
        LoginInfo userInfoInStorage = loginInfoManager.getLoginInfo (username.toString ());
        if (userInfoInStorage == null) {
            return false;
        }
        boolean usernameMatch = username.toString ().matches (userInfoInStorage.getUserName ());

        String securePassword = userInfoInStorage.getPassword ();
        boolean passwordMatch = PasswordUtils.verifyUserPassword(password.toString (), securePassword);

        if (passwordMatch && usernameMatch) {
            CurrentUser.setLoginInfo (username.toString (), userInfoInStorage.getAuthenticationLevel ());
            logger.info (String.format ("User has login with user name : " + CurrentUser.getUserName ()));
            return true;
        }
        return false;
    }

}
