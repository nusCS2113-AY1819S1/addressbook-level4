package seedu.address.security;

/***
 * API of Security Component
 */
public interface Security {

    /***
     * Login a user with associated password
     * @param username
     * @param password
     */
    void login(String username, String password);
}
