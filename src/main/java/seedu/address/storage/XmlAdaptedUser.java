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
    private String addressBookFilePath;
    @XmlElement(required = true)
    private String salesHistoryFilePath;

    /**
     * Constructs an XmlAdaptedUser.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedUser() {}

    /**
     * Constructs an {@code XmlAdaptedUser} with the given person details.
     */
    public XmlAdaptedUser(String username, String password, String addressBookFilePath, String salesHistoryFilePath) {
        this.username = username;
        this.password = password;
        this.addressBookFilePath = addressBookFilePath;
        this.salesHistoryFilePath = salesHistoryFilePath;
    }

    /**
     * Converts a given User into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedUser
     */
    public XmlAdaptedUser(User source) {
        username = source.getUsername().fullUsername;
        password = source.getPassword().fullPassword;
        addressBookFilePath = source.getAddressBookFilePath().toString();
        salesHistoryFilePath = source.getSalesHistoryFilePath().toString();
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's User object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
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
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "AddressBook file path"));
        }

        if (!User.isValidAddressBookFilePath(Paths.get(addressBookFilePath), this.username)) {
            throw new IllegalValueException(String.format(User.MESSAGE_AB_FILEPATH_CONSTRAINTS, "AddressBook"));
        }
        final Path modelAddressBookFilePath = Paths.get(addressBookFilePath);

        if (salesHistoryFilePath == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "SalesHistory file path"));
        }

        if (!User.isValidSalesHistoryFilePath(Paths.get(salesHistoryFilePath), this.username)) {
            throw new IllegalValueException(String.format(User.MESSAGE_AB_FILEPATH_CONSTRAINTS, "SalesHistory"));
        }
        final Path modelSalesHistoryFilePath = Paths.get(salesHistoryFilePath);

        return new User(modelUsername, modelPassword, modelAddressBookFilePath, modelSalesHistoryFilePath);
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
                && Objects.equals(addressBookFilePath, otherUser.addressBookFilePath);

    }
}
