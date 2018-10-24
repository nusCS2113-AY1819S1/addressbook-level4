package seedu.address.security;

import seedu.address.model.User;

/***
 * API of Security Component
 */
public interface Security {

    /***
     * Login a user with associated password
     * @param username Username
     * @param password Password
     */
    void login(String username, String password);

    /***
     * Logs out
     */
    void logout();

    /***
     * Registers the user
     * Returns 1 if successful, 2 if user exists, 3 if the fields are incomplete
     */
    RegisterFlag register(String username, String password, String email, String phone, String address);

    /***
     *
     * @return is authenticated for tests
     */
    boolean getAuthentication();

    /**
     *
     * @return the current authenticated User
     */
    User getUser();
}
