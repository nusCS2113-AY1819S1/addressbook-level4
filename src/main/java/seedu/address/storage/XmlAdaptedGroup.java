package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

/**
 * JAXB-friendly version of the Group.
 */

public class XmlAdaptedGroup {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Group's %s field is missing!";

    @XmlElement(required = true)
    private String name;

    /**
     * Constructs an XmlAdaptedGroup.
     * This is the no-arg constructor that is required by JAXB.
     */

    public XmlAdaptedGroup() {}

    /**
     * Constructs an {@code XmlAdaptedGroup} with the given group details.
     */
    public XmlAdaptedGroup(String name) {
        this.name = name;
    }
    /**
     * Converts a given Group into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedGroup
     */
    public XmlAdaptedGroup(Group source) {
        name = source.getName().fullName;
    }

    /**
     * Converts this jaxb-friendly adapted group object into the model's Group object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted group
     */
    public Group toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }

        final Name modelName = new Name(name);

        return new Group(modelName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedGroup)) {
            return false;
        }

        XmlAdaptedGroup otherGroup = (XmlAdaptedGroup) other;
        return Objects.equals(name, otherGroup.name);
    }

}
