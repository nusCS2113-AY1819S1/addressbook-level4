package seedu.address.model.user;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.StorageController;
import seedu.address.storage.StorageManager;
import seedu.address.storage.adapter.XmlAdaptedUser;



/**
 * This class represents the model-level layer for user management.
 */
public class UserManager {

    private static UserManager initUM = new UserManager();
    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ArrayList<User> userList = new ArrayList<User>();
    private User loggedInUser;
    private boolean isAuthenticated = false;
    private boolean disarmAuthSystem = true;


    public UserManager() {
        loadUsers();
    }

    /**
     * Loads all the users from the storage-layer.
     */
    public void loadUsers() {
        for (XmlAdaptedUser xmlUser : StorageController.getUserStorage()) {
            userList.add(xmlUser.toModelType());
        }
    }

    public boolean isDisarmAuthSystem() {
        return disarmAuthSystem;
    }

    public static UserManager getInstance() {
        if (initUM == null) {
            initUM = new UserManager();
        }
        return initUM;
    }

    public void deleteUser (User user) {
        userList.remove(user);
    }
    public void addUser(User user) {
        userList.add(user);
    }

    /**
     * Authenticates the provided credentials of a user.
     */
    public boolean authenticate(User user) {

        if (!hasUser(user.getEmail())) {
            return false;
        }

        User foundUser = getUser(user.getEmail());

        try {
            if (foundUser.getUnhashedPassword().equals(user.getUnhashedPassword())) {
                isAuthenticated = true;
                loggedInUser = foundUser;
                logger.info("User has logged in.");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }
    /**
     * Checks if the logged-in user is a teacher.
     */
    public boolean isTeacher() {
        if (getLoggedInUserRole() == 2) {
            return true;
        } else {
            return false;
        }
    }
    public int getLoggedInUserRole() {
        if (isAuthenticated) {
            return loggedInUser.getRole();
        } else {
            return 0;
        }
    }
    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    /**
     * Check if a user account with a provided email address exists.
     */
    public boolean hasUser(String userEmail) {
        for (User c: userList) {
            if (userEmail.equalsIgnoreCase(c.getEmail())) {
                return true;
            }
        }

        return false;

    }

    public User getUser(String userEmail) {
        for (User c: userList) {
            if (userEmail.equalsIgnoreCase(c.getEmail())) {
                return c;
            }
        }

        throw new NullPointerException();

    }

    /**
     * Saves all users currently in memory to file.
     */
    public void saveUserList() {
        ArrayList<XmlAdaptedUser> xmlAdaptedUsers =
                userList.stream().map(XmlAdaptedUser::new).collect(Collectors.toCollection(ArrayList::new));
        StorageController.setUserStorage(xmlAdaptedUsers);
        StorageController.storeData();
    }


    public ArrayList<User> getUsers() {
        return userList;
    }

    public void setUsers(ArrayList<User> users) {
        this.userList = users;
    }
}
