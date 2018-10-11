package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.login.Password;
import seedu.address.model.login.UniqueUsersList;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.AuthenticatedException;
import seedu.address.model.login.exceptions.DuplicateUserException;
import seedu.address.model.login.exceptions.UserNotFoundException;

import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class UserDatabase implements ReadOnlyUserDatabase {

    private static final Logger logger = LogsCenter.getLogger(UserDatabase.class);

    private static final String AB_FILEPATH_FOLDER = "data/";
    private static final String AB_FILEPATH_PREFIX = "addressbook-";
    private static final String AB_FILEPATH_POSTFIX = ".xml";
    private UniqueUsersList users;

    private boolean hasLoggedIn;
    private User loggedInUser;

    {
        users = new UniqueUsersList();
    }

    public UserDatabase() {
        hasLoggedIn = false;
    }

    /**
     * Creates an UserDatabase using the Users in the {@code toBeLoaded}
     */
    public UserDatabase(ReadOnlyUserDatabase toBeLoaded) {
        this();
        resetData(toBeLoaded);
    }

    /**
     * Creates an UserDatabase using the Users  in the {@code toBeLoaded} and logged-in status
     */
    public UserDatabase(ReadOnlyUserDatabase toBeLoaded, boolean loggedin) {
        this();
        resetData(toBeLoaded);
        hasLoggedIn = loggedin;
    }

    public boolean hasUser(User user) {
        requireNonNull(user);
        return users.contains(user);
    }


    public User getUser(Username username) {
        return users.getUser(username.toString());
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Returns the login status of the user.
     */
    public boolean hasLoggedIn() {
        return hasLoggedIn;
    }

    /**
     * Sets the login status of the user to {@code status}.
     * @param status
     */
    public void setLoginStatus(boolean status) {
        hasLoggedIn = status;
    }

    /**
     * Sets the unique users list to {@code uniqueUserList}
     */
    public void setUniqueUserList(UniqueUsersList uniqueUserList) {
        users = uniqueUserList;
    }

    /**
     * Checks the login credentials whether it matches any user in UserDatabase.
     *
     * @param username
     * @param password
     * @throws AuthenticatedException is the user is already logged in.
     */
    public boolean checkLoginCredentials(Username username, Password password) throws AuthenticatedException {
        User toCheck = new User(username, password, Paths.get(AB_FILEPATH_FOLDER, AB_FILEPATH_PREFIX + username + AB_FILEPATH_POSTFIX));
        logger.fine("Attempting to check credentials for login");

        if (hasLoggedIn) {
            throw new AuthenticatedException();
        } else if (!users.contains(toCheck)) {
            logger.fine("Login credentials match failed. Login failed.");
            return hasLoggedIn;
        } else {
            hasLoggedIn = true;
            loggedInUser = toCheck;
            logger.fine("Login credentials match. Login successful.");
            return hasLoggedIn;
        }
    }

    /**
     * Checks whether input credentials matches a valid user.
     *
     * @param username
     * @param password
     * @return
     * @throws AuthenticatedException
     */
    public boolean checkCredentials(Username username, Password password) throws AuthenticatedException {
        User toCheck = new User(username, password,
                Paths.get(AB_FILEPATH_FOLDER,AB_FILEPATH_PREFIX + username + AB_FILEPATH_POSTFIX));
        logger.fine("Attempting to check credentials for permissions.");
        if (!hasLoggedIn) {
            return users.contains(toCheck);
        } else {
            throw new AuthenticatedException();
        }
    }

    public void setUsers(List<User> users) throws DuplicateUserException { this.users.setUsers(users); }

    /**
     * Adds a user to the User Database.
     *
     * @throws DuplicateUserException if an equivalent user already exists.
     */
    public void addUser(User user) throws DuplicateUserException { users.add(user); }

    /**
     * Replaces the given user {@code target} in the list with {@code editedUser}.
     *
     * @throws DuplicateUserException if updating the user's details causes the user to be equivalent to
     *      another existing user in the list.
     * @throws UserNotFoundException if {@code target} could not be found in the list.
     */
    public void updateUserPassword(User target, User userWithNewPassword)
            throws UserNotFoundException {
        requireNonNull(userWithNewPassword);
        users.setUser(target, userWithNewPassword);
    }


    /**
     * Removes {@code key} from this {@code UserDatabase}.
     */
    public void removeUser(User key) { users.remove(key); }

    /**
     * Resets the existing user list of this {@code UserDatabase} with {@code newData}.
     */
    private void resetData(ReadOnlyUserDatabase newData) {
        requireNonNull(newData);
            setUsers(newData.getUsersList());
    }

    @Override
    public ObservableList<User> getUsersList() { return users.asObservableList(); }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UserDatabase
                && this.users.equals(((UserDatabase) other).users));
    }

    @Override
    public int hashCode() {
        return users.hashCode();
    }
}
