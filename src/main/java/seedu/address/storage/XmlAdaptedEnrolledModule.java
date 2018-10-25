package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.enrolledModule.EnrolledModule;

/**
 * JAXB-friendly adapted version of the EnrolledModule.
 */
public class XmlAdaptedEnrolledModule {

    @XmlValue
    private String enrolledModuleName;

    /**
     * Constructs an XmlAdaptedEnrolledModule.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEnrolledModule() {}

    /**
     * Constructs a {@code XmlAdaptedEnrolledModule} with the given {@code enrolledModuleName}.
     */
    public XmlAdaptedEnrolledModule(String enrolledModuleName) {
        this.enrolledModuleName = enrolledModuleName;
    }

    /**
     * Converts a given EnrolledModule into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedEnrolledModule(EnrolledModule source) {
        enrolledModuleName = source.enrolledModuleName;
    }

    /**
     * Converts this jaxb-friendly adapted EnrolledModule object into the model's EnrolledModule object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public EnrolledModule toModelType() throws IllegalValueException {
        if (!EnrolledModule.isValidEnRolledModuleName(enrolledModuleName)) {
            throw new IllegalValueException(EnrolledModule.MESSAGE_ENROLLED_MODULE_CONSTRAINTS);
        }
        return new EnrolledModule(enrolledModuleName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedEnrolledModule)) {
            return false;
        }

        return enrolledModuleName.equals(((XmlAdaptedEnrolledModule) other).enrolledModuleName);
    }
}
