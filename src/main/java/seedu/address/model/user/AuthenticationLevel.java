package seedu.address.model.user;

import static java.util.Objects.requireNonNull;
import static seedu.address.authentication.AuthenticationLevelConstant.AUTH_ACCOUNTANT;
import static seedu.address.authentication.AuthenticationLevelConstant.AUTH_ADMIN;
import static seedu.address.authentication.AuthenticationLevelConstant.AUTH_MANAGER;
import static seedu.address.authentication.AuthenticationLevelConstant.AUTH_STOCK_TAKER;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents the authenticationLevel in the loginInfo list.
 */
public class AuthenticationLevel {
    public static final String MESSAGE_AUTHENTICATIONLEVEL_CONSTRAINTS =
            "Only the following authentication level is allow: "
                    + AUTH_ADMIN + " "
                    + AUTH_MANAGER + " "
                    + AUTH_ACCOUNTANT + " "
                    + AUTH_STOCK_TAKER;
    private String authenticationLevel;

    public AuthenticationLevel(){}

    public AuthenticationLevel(String authenticationLevel) {
        requireNonNull(authenticationLevel);
        checkArgument(isAuthenticationLevelValid (authenticationLevel), MESSAGE_AUTHENTICATIONLEVEL_CONSTRAINTS);
        this.authenticationLevel = authenticationLevel;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isAuthenticationLevelValid(String authenticationLevel) {
        if (authenticationLevel.equals (AUTH_ADMIN)
                || authenticationLevel.equals (AUTH_MANAGER)
                || authenticationLevel.equals (AUTH_STOCK_TAKER)
                || authenticationLevel.equals (AUTH_ACCOUNTANT)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString () {
        return authenticationLevel;
    }
    public boolean equals(String userName) {
        return this.authenticationLevel.equals (authenticationLevel);
    }
}
