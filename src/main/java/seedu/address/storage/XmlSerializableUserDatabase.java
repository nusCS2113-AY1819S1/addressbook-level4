package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyUserDatabase;
import seedu.address.model.UserDatabase;

public class XmlSerializableUserDatabase {
    @XmlElement
    private List<XmlAdaptedUser> users;

    /**
     * Creates an empty XmlSerializableUserDatabase.
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
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson} or {@code XmlAdaptedTag}.
     */
    public UserDatabase toModelType() throws IllegalValueException {
        UserDatabase userDatabase = new UserDatabase();
        for (XmlAdaptedUser user : users) {
            userDatabase.addUser(user.toModelType());
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

        XmlSerializableUserDatabase otherUd = (XmlSerializableUserDatabase) other;
        return users.equals(otherUd.users);
    }
}
