package seedu.address.testutil;

import seedu.address.model.classroom.ClassModule;
import seedu.address.model.classroom.ClassName;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.Enrollment;

/**
 * A utility class to help with building Classroom objects.
 */
public class ClassroomBuilder {

    public static final String DEFAULT_CLASSNAME = "T16";
    public static final String DEFAULT_CLASSMODULE = "CG1111";
    public static final String DEFAULT_ENROLLMENT = "20";

    private ClassName className;
    private ClassModule moduleCode;
    private Enrollment maxEnrollment;

    public ClassroomBuilder() {
        className = new ClassName(DEFAULT_CLASSNAME);
        moduleCode = new ClassModule(DEFAULT_CLASSMODULE);
        maxEnrollment = new Enrollment(DEFAULT_ENROLLMENT);
    }

    /**
     * Initializes the ClassroomBuilder with the data of {@code ClassroomToCopy}.
     */
    public ClassroomBuilder(Classroom ClassroomToCopy) {
        className = ClassroomToCopy.getClassName();
        moduleCode = ClassroomToCopy.getModuleCode();
        maxEnrollment = ClassroomToCopy.getMaxEnrollment();
    }

    /**
     * Sets the {@code ClassName} of the {@code Classroom} that we are building.
     */
    public ClassroomBuilder withClassName(String className) {
        this.className = new ClassName(className);
        return this;
    }

    /**
     * Sets the {@code ClassModule} of the {@code Classroom} that we are building.
     */
    public ClassroomBuilder withClassModule(String moduleCode) {
        this.moduleCode = new ClassModule(moduleCode);
        return this;
    }

    /**
     * Sets the {@code Enrollment} of the {@code Classroom} that we are building.
     */
    public ClassroomBuilder withEnrollment(String maxEnrollment) {
        this.maxEnrollment = new Enrollment(maxEnrollment);
        return this;
    }

    public Classroom build() {
        return new Classroom(className, moduleCode, maxEnrollment);
    }

}
