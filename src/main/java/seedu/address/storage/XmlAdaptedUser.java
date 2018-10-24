package seedu.address.storage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.login.Password;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;

/**
 * JAXB-friendly version of the User.
 */
public class XmlAdaptedUser {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "User's %s field is missing!";

    @XmlElement(required = true)
    private String username;
    @XmlElement(required = true)
    private String password;
    @XmlElement(required = true)
<<<<<<< HEAD
    private String addressbookfilepath;
    @XmlElement(required = true)
    private String distributorbookfilepath;
=======
    private String addressBookFilePath;
    @XmlElement(required = true)
    private String salesHistoryFilePath;
>>>>>>> upstream/master

    /**
     * Constructs an XmlAdaptedUser.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedUser() {}

    /**
     * Constructs an {@code XmlAdaptedUser} with the given product details.
     */
<<<<<<< HEAD
    public XmlAdaptedUser(String username, String password,
                          String addressbookfilepath, String distributorbookfilepath) {
        this.username = username;
        this.password = password;
        this.addressbookfilepath = addressbookfilepath;
        this.distributorbookfilepath = distributorbookfilepath;
=======
    public XmlAdaptedUser(String username, String password, String addressBookFilePath, String salesHistoryFilePath) {
        this.username = username;
        this.password = password;
        this.addressBookFilePath = addressBookFilePath;
        this.salesHistoryFilePath = salesHistoryFilePath;
>>>>>>> upstream/master
    }

    /**
     * Converts a given User into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedUser
     */
    public XmlAdaptedUser(User source) {
        username = source.getUsername().fullUsername;
        password = source.getPassword().fullPassword;
<<<<<<< HEAD
        addressbookfilepath = source.getAddressBookFilePath().toString();
        distributorbookfilepath = source.getDistributorBookFilePath().toString();
=======
        addressBookFilePath = source.getAddressBookFilePath().toString();
        salesHistoryFilePath = source.getSalesHistoryFilePath().toString();
>>>>>>> upstream/master
    }

    /**
     * Converts this jaxb-friendly adapted product object into the model's User object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted product
     */
    public User toModelType() throws IllegalValueException {
        if (username == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Username.class.getSimpleName()));
        }
        if (!Username.isValidUsername(username)) {
            throw new IllegalValueException(Username.MESSAGE_USERNAME_CONSTRAINTS);
        }
        final Username modelUsername = new Username(username);

        if (password == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Password.class.getSimpleName()));
        }
        if (!Password.isValidPassword(password)) {
            throw new IllegalValueException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        final Password modelPassword = new Password(password);

        if (addressBookFilePath == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "ProductDatabase file path"));
        }

<<<<<<< HEAD
        if (distributorbookfilepath == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "DistributorBook file path"));
        }

        if (!User.isValidAddressBookFilePath(Paths.get(addressbookfilepath), this.username)) {
            throw new IllegalValueException(User.MESSAGE_AB_FILEPATH_CONSTRAINTS);
=======
        if (!User.isValidAddressBookFilePath(Paths.get(addressBookFilePath), this.username)) {
            throw new IllegalValueException(String.format(User.MESSAGE_AB_FILEPATH_CONSTRAINTS, "AddressBook"));
>>>>>>> upstream/master
        }
        final Path modelAddressBookFilePath = Paths.get(addressBookFilePath);

<<<<<<< HEAD
        final Path modelAddressBookFilePath = Paths.get(addressbookfilepath);
        final Path modelDistributorBookFilePath = Paths.get(distributorbookfilepath);

        return new User(modelUsername, modelPassword, modelAddressBookFilePath, modelDistributorBookFilePath);
=======
        if (salesHistoryFilePath == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "SalesHistory file path"));
        }

        if (!User.isValidSalesHistoryFilePath(Paths.get(salesHistoryFilePath), this.username)) {
            throw new IllegalValueException(String.format(User.MESSAGE_AB_FILEPATH_CONSTRAINTS, "SalesHistory"));
        }
        final Path modelSalesHistoryFilePath = Paths.get(salesHistoryFilePath);

        return new User(modelUsername, modelPassword, modelAddressBookFilePath, modelSalesHistoryFilePath);
>>>>>>> upstream/master
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedUser)) {
            return false;
        }

        XmlAdaptedUser otherUser = (XmlAdaptedUser) other;
        return Objects.equals(username, otherUser.username)
                && Objects.equals(password, otherUser.password)
<<<<<<< HEAD
                && Objects.equals(addressbookfilepath, otherUser.addressbookfilepath)
                && Objects.equals(distributorbookfilepath, otherUser.distributorbookfilepath);
=======
                && Objects.equals(addressBookFilePath, otherUser.addressBookFilePath);
>>>>>>> upstream/master

    }
}
