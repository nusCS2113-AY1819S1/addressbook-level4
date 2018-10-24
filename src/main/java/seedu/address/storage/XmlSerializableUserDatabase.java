package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyUserDatabase;
import seedu.address.model.UserDatabase;
import seedu.address.model.login.User;

/**
 * An Immutable User Database that is serializable to XML format
 */
@XmlRootElement(name = "userdatabase")
public class XmlSerializableUserDatabase {

    public static final String MESSAGE_DUPLICATE_USER = "Users list contains duplicate user(s).";

    @XmlElement
    private List<XmlAdaptedUser> users;

    /**
     * Creates an empty XmlSerializableUserDatabase
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableUserDatabase() {
        users = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableUserDatabase(ReadOnlyUserDatabase src) {
        this();
        users.addAll(src.getUsersList().stream().map(XmlAdaptedUser::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code ProductDatabase} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedUser}
     */
    public UserDatabase toModelType() throws IllegalValueException {
        UserDatabase userDatabase = new UserDatabase();
        for (XmlAdaptedUser u : users) {
            User user = u.toModelType();
            if (userDatabase.hasUser(user)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_USER);
            }
            userDatabase.addUser(user);
        }
        return userDatabase;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableUserDatabase)) {
            return false;
        }
        return users.equals(((XmlSerializableUserDatabase) other).users);
    }
}
