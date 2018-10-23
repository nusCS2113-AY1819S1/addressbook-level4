package seedu.address.model.login;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Represents a User in the user database.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class User {

    public static final String MESSAGE_AB_FILEPATH_CONSTRAINTS = "%s file path is incorrect.";

    private static final String AB_FILEPATH_FOLDER = "data/";
    private static final String AB_FILEPATH_PREFIX = "addressbook-";
    private static final String AB_SALESHISTORY_FILEPATH_PREFIX = "saleshistory-";
    private static final String AB_FILEPATH_POSTFIX = ".xml";

    private Username username;
    private Password password;
    private Path addressBookFilePath;
    private Path salesHistoryFilePath;

    public User() {
        this.username = new Username("default");
        this.password = new Password("password");
        this.addressBookFilePath = Paths.get(AB_FILEPATH_FOLDER, "addressbook-default.xml");
        this.salesHistoryFilePath = Paths.get(AB_FILEPATH_FOLDER, "saleshistory-default.xml");
    }

    /**
     * Creates a user instance
     */
    public User(Username username, Password password) {
        this(username,
                password,
                Paths.get(AB_FILEPATH_FOLDER, AB_FILEPATH_PREFIX + username + AB_FILEPATH_POSTFIX),
                Paths.get(AB_FILEPATH_FOLDER, AB_SALESHISTORY_FILEPATH_PREFIX + username + AB_FILEPATH_POSTFIX));
    }

    /**
     * Every field must be present and not null.
     */
    public User(Username username, Password password, Path addressBookFilePath, Path salesHistoryFilePath) {
        requireAllNonNull(username, password, addressBookFilePath);
        this.username = username;
        this.password = password;
        this.addressBookFilePath = addressBookFilePath;
        this.salesHistoryFilePath = salesHistoryFilePath;
    }

    /**
     * Returns true if user of the same name has the correct address book extension field.
     */
    public static boolean isValidAddressBookFilePath(Path test, String username) {
        return test.equals(Paths.get(AB_FILEPATH_FOLDER + AB_FILEPATH_PREFIX + username + AB_FILEPATH_POSTFIX))
                && !test.equals("");
    }

    /**
     * Returns true if user of the same name has the correct sales history extension field.
     */
    public static boolean isValidSalesHistoryFilePath(Path test, String username) {
        return test.equals(Paths.get(AB_FILEPATH_FOLDER + AB_SALESHISTORY_FILEPATH_PREFIX + username
                + AB_FILEPATH_POSTFIX))
                && !test.equals("");
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public Path getSalesHistoryFilePath() {
        return salesHistoryFilePath;
    }

    /**
     * Returns true if both user of the same name have at least one other identity field that is the same.
     */
    public boolean isSameUser(User otherUser) {
        if (otherUser == this) {
            return true;
        }

        return otherUser != null
                && otherUser.getUsername().equals(getUsername())
                && (otherUser.getPassword().equals(getPassword())
                || otherUser.getAddressBookFilePath().equals(getAddressBookFilePath()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof User)) {
            return false;
        }

        User otherPerson = (User) other;
        return otherPerson.getUsername().equals(this.getUsername())
                && otherPerson.getPassword().equals(this.getPassword())
                && otherPerson.getAddressBookFilePath().equals(this.getAddressBookFilePath());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(username, password, addressBookFilePath, salesHistoryFilePath);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Username: ")
                .append(getUsername())
                .append(" Password: ")
                .append(getPassword())
                .append(" Address Book File Path: ")
                .append(getAddressBookFilePath())
                .append(" Sales History File Path: ")
                .append(getSalesHistoryFilePath());
        return builder.toString();
    }
}
