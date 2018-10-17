package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.enrolledClass.EnrolledClass;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly adapted version of the EnrolledClass.
 */
public class XmlAdaptedEnrolledClass {

    @XmlValue
    private String enrolledClassName;

    /**
     * Constructs an XmlAdaptedEnrolledClass.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEnrolledClass() {}

    /**
     * Constructs a {@code XmlAdaptedEnrolledClass} with the given {@code enrolledClassName}.
     */
    public XmlAdaptedEnrolledClass(String enrolledClassName) {
        this.enrolledClassName = enrolledClassName;
    }

    /**
     * Converts a given EnrolledClass into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedEnrolledClass(EnrolledClass source) {
        enrolledClassName = source.enrolledClassName;
    }

    /**
     * Converts this jaxb-friendly adapted EnrolledClass object into the model's EnrolledClass object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public EnrolledClass toModelType() throws IllegalValueException {
        if (!EnrolledClass.isValidEnRolledClassName(enrolledClassName)) {
            throw new IllegalValueException(EnrolledClass.MESSAGE_ENROLLED_CLASS_CONSTRAINTS);
        }
        return new EnrolledClass(enrolledClassName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedEnrolledClass)) {
            return false;
        }

        return enrolledClassName.equals(((XmlAdaptedEnrolledClass) other).enrolledClassName);
    }
}
