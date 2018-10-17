package seedu.address.testutil;

import seedu.address.model.login.LoginDetails;
import seedu.address.model.login.UserId;
import seedu.address.model.login.UserPassword;
import seedu.address.model.login.UserRole;

/**
 * A utility class to help with building LoginDetails objects.
 */
public class AccountBuilder {

    public static final String DEFAULT_USERID = "A1234567M";
    public static final String DEFAULT_USERPASSWORD = "zaq1xsw2cde3";

    private UserId userId;
    private UserPassword userPassword;
    private UserRole userRole;

    public AccountBuilder() {
        userId = new UserId(DEFAULT_USERID);
        userPassword = new UserPassword(DEFAULT_USERPASSWORD);
    }

    /**
     * Initializes the AccountBuilder with the data of {@code LoginToCopy}.
     */
    public AccountBuilder(LoginDetails loginToCopy) {
        userId = loginToCopy.getUserId();
        userPassword = loginToCopy.getUserPassword();
    }

    /**
     * Sets the {@code UserId} of the {@code LoginDetails} that we are building.
     */
    public AccountBuilder withUserId(String userid) {
        this.userId = new UserId(userid);
        return this;
    }

    /**
     * Sets the {@code UserPassword} of the {@code LoginDetails} that we are building.
     */
    public AccountBuilder withUserPassword(String userpassword) {
        this.userPassword = new UserPassword(userpassword);
        return this;
    }

    /**
     * Sets the {@code UserPassword} of the {@code LoginDetails} that we are building.
     */
    public AccountBuilder withUserRole(String userrole) {
        this.userRole = new UserRole(userrole);
        return this;
    }

    public LoginDetails build() {
        return new LoginDetails(userId, userPassword, userRole);
    }

}
