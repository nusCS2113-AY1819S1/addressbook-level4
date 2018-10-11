package seedu.address.security;

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
     *
     * @return is authenticated for tests
     */
    public boolean getAuthentication();
}
