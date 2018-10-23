package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.enrolledModule.EnrolledModule;

/**
 * JAXB-friendly adapted version of the EnrolledModule.
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
     * Constructs a {@code XmlAdaptedEnrolledClass} with the given {@code enrolledModuleName}.
     */
    public XmlAdaptedEnrolledClass(String enrolledClassName) {
        this.enrolledClassName = enrolledClassName;
    }

    /**
     * Converts a given EnrolledModule into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedEnrolledClass(EnrolledModule source) {
        enrolledClassName = source.enrolledModuleName;
    }

    /**
     * Converts this jaxb-friendly adapted EnrolledModule object into the model's EnrolledModule object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public EnrolledModule toModelType() throws IllegalValueException {
        if (!EnrolledModule.isValidEnRolledModuleName(enrolledClassName)) {
            throw new IllegalValueException(EnrolledModule.MESSAGE_ENROLLED_MODULE_CONSTRAINTS);
        }
        return new EnrolledModule(enrolledClassName);
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
