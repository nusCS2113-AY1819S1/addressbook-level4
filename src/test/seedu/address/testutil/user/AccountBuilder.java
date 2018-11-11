//@@author liu-tianhang
package seedu.address.testutil.user;

import seedu.address.commons.core.LoginInfo;
import seedu.address.model.user.AuthenticationLevel;
import seedu.address.model.user.Password;
import seedu.address.model.user.UserName;

/**
 * A utility class to help with building LoginInfo objects.
 */
public class AccountBuilder {
    public static final String DEFAULT_USER_NAME = "tester";
    public static final String DEFAULT_PASSWORD = "Gcf70h4aWQ1T9NMxE03XM3nq3nCmFGihnO4xMzHMgP0=";
    public static final String DEFAULT_AUTHENTICATION_LEVEL = "ADMIN";

    private UserName userName;
    private Password password;
    private AuthenticationLevel authenticationLevel;
    private LoginInfo loginInfo;
    public AccountBuilder() {
        userName = new UserName (DEFAULT_USER_NAME);
        password = new Password (DEFAULT_PASSWORD);
        authenticationLevel = new AuthenticationLevel (DEFAULT_AUTHENTICATION_LEVEL);
        loginInfo = new LoginInfo (userName, password, authenticationLevel);
    }

    public AccountBuilder(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }
    public AccountBuilder(UserName userName, Password password, AuthenticationLevel authenticationLevel) {
        this.userName = userName;
        this.password = password;
        this.authenticationLevel = authenticationLevel;
        loginInfo = new LoginInfo (userName, password, authenticationLevel);
    }

    /**
     * Set the {@code userName} of the {@code loginInfo}.
     */
    public AccountBuilder withUserName(UserName userName) {
        this.userName = userName;
        return this;
    }
    /**
     * Set the {@code password} of the {@code loginInfo}.
     */
    public AccountBuilder withPassword(Password password) {
        this.password = password;
        return this;
    }
    /**
     * Set the {@code authenticationLevel} of the {@code loginInfo}.
     */
    public AccountBuilder withAuthenticationLevel(AuthenticationLevel authenticationLevel) {
        this.authenticationLevel = authenticationLevel;
        return this;
    }

    /**
     * Returns a loginInfo object
     */
    public LoginInfo build() {
        return new LoginInfo (userName, password, authenticationLevel);
    }

}
