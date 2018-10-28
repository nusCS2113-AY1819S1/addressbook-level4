package seedu.address.model.login;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class UserTest {

    private static final Username VALID_USERNAME = new Username("inventarie");
    private static final Password VALID_PASSWORD = new Password("password");
    private static final Path VALID_ADD_FILE_PATH = Paths.get("addressbook-inventarie.xml");
    private static final Path VALID_DIST_FILE_PATH = Paths.get("distributorbook-inventarie.xml");
    private static final Path VALID_SH_FILE_PATH = Paths.get("saleshistory-inventarie.xml");

    @Test
    public void constructor_null_throwsNullPointerException() {
        // ================== normal constructor ======================

        // null username
        Assert.assertThrows(NullPointerException.class, () -> new User(null, VALID_PASSWORD));

        // null password
        Assert.assertThrows(NullPointerException.class, () -> new User(VALID_USERNAME, null));

        // ================== overloaded constructor ======================

        // null username
        Assert.assertThrows(NullPointerException.class, () -> new User(null, VALID_PASSWORD,
                VALID_ADD_FILE_PATH, VALID_DIST_FILE_PATH, VALID_SH_FILE_PATH));

        // null password
        Assert.assertThrows(NullPointerException.class, () -> new User(VALID_USERNAME, null,
                VALID_ADD_FILE_PATH, VALID_DIST_FILE_PATH, VALID_SH_FILE_PATH));

        // null address book file path
        Assert.assertThrows(NullPointerException.class, () -> new User(VALID_USERNAME, VALID_PASSWORD,
                null, VALID_DIST_FILE_PATH, VALID_SH_FILE_PATH));

        // null distributor book file path
        Assert.assertThrows(NullPointerException.class, () -> new User(VALID_USERNAME, VALID_PASSWORD,
                VALID_ADD_FILE_PATH, null, VALID_SH_FILE_PATH));

        // null sales history file path
        Assert.assertThrows(NullPointerException.class, () -> new User(VALID_USERNAME, VALID_PASSWORD,
                VALID_ADD_FILE_PATH, VALID_DIST_FILE_PATH, null));

    }
}
