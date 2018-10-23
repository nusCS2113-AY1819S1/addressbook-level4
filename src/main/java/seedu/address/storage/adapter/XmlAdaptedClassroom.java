package seedu.address.storage.adapter;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.classroom.ClassModule;
import seedu.address.model.classroom.ClassName;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.Enrollment;

/**
 * JAXB-friendly adapted version of the Classroom.
 */
@XmlRootElement(name = "class")
public class XmlAdaptedClassroom {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Classroom's %s field is missing!";

    //class-specific fields
    @XmlElement(name = "className", required = true, nillable = true)
    private String className;
    @XmlElement(name = "moduleCode", required = true, nillable = true)
    private String moduleCode;
    @XmlElement(name = "maxEnrollment", required = true, nillable = true)
    private String maxEnrollment;

    /**
     * Constructs an XmlAdaptedClassroom.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedClassroom() {
    }

    /**
     * Constructs an {@code XmlAdaptedClassroom} with the given classroom details
     */
    public XmlAdaptedClassroom(String className, String moduleCode, String maxEnrollment) {
        this.className = className;
        this.moduleCode = moduleCode;
        this.maxEnrollment = maxEnrollment;
    }

    /**
     * Converts a Classroom into an {@code XmlAdaptedClassroom} for JAXB use
     */
    public XmlAdaptedClassroom(Classroom classroom) {
        this.className = classroom.getClassName().getValue();
        this.moduleCode = classroom.getModuleCode().getValue();
        this.maxEnrollment = classroom.getMaxEnrollment().getValue();
    }

    /**
     * Converts this XmlAdaptedClassroom into the model's Classroom object
     */
    public Classroom toModelType() throws IllegalValueException {
        if (className == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ClassName.class.getSimpleName()));
        }
        if (!ClassName.isValidClassName(className)) {
            throw new IllegalValueException(ClassName.MESSAGE_CLASSNAME_CONSTRAINTS);
        }
        final ClassName modelClassName = new ClassName(className);

        if (moduleCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ClassModule.class.getSimpleName()));
        }
        if (!ClassModule.isValidClassModule(moduleCode)) {
            throw new IllegalValueException(ClassModule.MESSAGE_CLASSMODULE_CONSTRAINTS);
        }
        final ClassModule modelClassModule = new ClassModule(moduleCode);

        if (maxEnrollment == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Enrollment.class.getSimpleName()));
        }
        if (!Enrollment.isValidEnrollment(maxEnrollment)) {
            throw new IllegalValueException(Enrollment.MESSAGE_ENROLLMENT_CONSTRAINTS);
        }
        final Enrollment modelEnrollment = new Enrollment(maxEnrollment);

        return new Classroom(modelClassName, modelClassModule, modelEnrollment);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedClassroom)) {
            return false;
        }

        XmlAdaptedClassroom otherClassroom = (XmlAdaptedClassroom) other;
        return Objects.equals(className, otherClassroom.className)
                && Objects.equals(moduleCode, otherClassroom.moduleCode)
                && Objects.equals(maxEnrollment, otherClassroom.maxEnrollment);
    }
}
