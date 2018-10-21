package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.login.LoginDetails;
import seedu.address.model.login.UserId;
import seedu.address.model.login.UserIdContainsKeywordsPredicate;
import seedu.address.model.login.UserPassword;
import seedu.address.model.login.UserRole;

/**
 * A utility class to help with building LoginDetails objects.
 */
public class AccountBuilder {

    public static final String DEFAULT_USERID = "A1234567M";
    public static final String DEFAULT_USERPASSWORD = "zaq1xsw2cde3";
    public static final String DEFAULT_ROLE = "member";
    public static final String DEFAULT_LOGIN_DETAILS = "A1234567M zaq1xsw2cde3 member";

    private UserId userId;
    private UserPassword userPassword;
    private UserRole userRole;

    public AccountBuilder() {
        userId = new UserId(DEFAULT_USERID);
        userPassword = new UserPassword(DEFAULT_USERPASSWORD);
        userRole = new UserRole(DEFAULT_ROLE);
    }

    /**
     * Initializes the AccountBuilder with the data of {@code LoginToCopy}.
     */
    public AccountBuilder(LoginDetails loginToCopy) {
        userId = loginToCopy.getUserId();
        userPassword = loginToCopy.getUserPassword();
        userRole = loginToCopy.getUserRole();
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
     * Sets the {@code UserRole} of the {@code LoginDetails} that we are building.
     */
    public AccountBuilder withUserRole(String userrole) {
        this.userRole = new UserRole(userrole);
        return this;
    }

    public LoginDetails build() {
        return new LoginDetails(userId, userPassword, userRole);
    }

    /**
     * Sets and returns the idPredicate of the {@code UserIdContainsKeywordsPredicate} that we are building.
     * @return a UserIdContainsKeywordsPredicate object with the default login details
     */
    public UserIdContainsKeywordsPredicate buildUserIdPredicate() {
        String trimmedArgs = DEFAULT_LOGIN_DETAILS.trim();
        String[] keywords = trimmedArgs.split("\\s+");
        List<String> keywordsList = new ArrayList<>(Arrays.asList(keywords));
        return new UserIdContainsKeywordsPredicate(keywordsList);
    }

}
