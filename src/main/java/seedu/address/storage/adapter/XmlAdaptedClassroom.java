package seedu.address.storage.adapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.model.classroom.ClassModule;
import seedu.address.model.classroom.ClassName;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.Enrollment;

/**
 * JAXB-friendly adapted version of the Classroom.
 */
@XmlRootElement(name = "class")
public class XmlAdaptedClassroom {
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
    public Classroom toModelType() {
        final ClassName modelClassName = new ClassName(className);
        final ClassModule modelClassModule = new ClassModule(moduleCode);
        final Enrollment modelEnrollment = new Enrollment(maxEnrollment);
        return new Classroom(modelClassName, modelClassModule, modelEnrollment);
    }
}
