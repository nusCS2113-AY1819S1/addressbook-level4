package seedu.address.testutil;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.UserDatabase;
import seedu.address.model.login.Password;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;

/**
 * A utility class containing a list of {@code User} objects to be used in tests.
 */
public class TypicalUsers {

    private static final String DEFAULT_AB_PATH = "data/addressbook-user.xml";
    private static final String AMY_AB_PATH = "data/addressbook-amy.xml";
    private static final String BOB_AB_PATH = "data/addressbook-bob.xml";
    private static final String DEFAULT_DB_PATH = "data/distributor-user.xml";
    private static final String AMY_DB_PATH = "data/distributor-amy.xml";
    private static final String BOB_DB_PATH = "data/distributor-bob.xml";
    private static final String DEFAULT_SALESHISTORY_PATH = "data/saleshistory-user.xml";
    private static final String AMY_SALESHISTORY_PATH = "data/saleshistory-amy.xml";
    private static final String BOB_SALESHISTORY_PATH = "data/saleshistory-bob.xml";

    public static final User DEFAULT_USER = new User(new Username("user"), new Password("pass"),
            Paths.get(DEFAULT_AB_PATH), Paths.get(DEFAULT_DB_PATH), Paths.get(DEFAULT_SALESHISTORY_PATH));
    public static final User AMY = new User(new Username("amy"), new Password("pass"),
            Paths.get(AMY_AB_PATH), Paths.get(AMY_DB_PATH), Paths.get(AMY_SALESHISTORY_PATH));
    public static final User BOB = new User(new Username("bob"), new Password("pass"),
            Paths.get(BOB_AB_PATH), Paths.get(BOB_DB_PATH), Paths.get(BOB_SALESHISTORY_PATH));

    public static final User JOHN = new User(new Username("John"), new Password("pass"));
    public static final User RICK = new User(new Username("Rick"), new Password("pass"));


    private TypicalUsers() {} // prevents instantiation

    /**
     * Returns an {@code UserDatabase} with all the typical users.
     */
    public static UserDatabase getTypicalUserDatabase() {
        UserDatabase ud = new UserDatabase();
        for (User user: getTypicalUsers()) {
            ud.addUser(user);
        }
        return ud;
    }

    public static List<User> getTypicalUsers() {
        return new ArrayList<>(Arrays.asList(DEFAULT_USER, AMY, BOB));
    }

}
