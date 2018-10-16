package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Friend;
import seedu.address.model.person.Name;

/**
 * Constructs a Friend
 */
public class XmlAdaptedFriend {
    @XmlValue
    private String friendName;

    /**
     * Constructs an XmlAdaptedFriend.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedFriend() {}

    /**
     * Constructs a {@code XmlAdaptedFriend} with the given {@code friendName}.
     */
    public XmlAdaptedFriend(String friendName) {
        this.friendName = friendName;
    }

    /**
     * Converts a given Friend into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedFriend(Friend source) {
        friendName = source.friendName.toString();
    }

    /**
     * Converts this jaxb-friendly adapted friend object into the model's Friend object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Friend toModelType() throws IllegalValueException {
        return new Friend(new Name(friendName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedFriend)) {
            return false;
        }

        return friendName.equals(((XmlAdaptedFriend) other).friendName);
    }
}
