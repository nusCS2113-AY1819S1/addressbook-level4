package seedu.address.testutil;

import seedu.address.model.classroom.ClassName;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.Enrollment;
import seedu.address.model.module.ModuleCode;

/**
 * A utility class to help with building Classroom objects.
 */
public class ClassroomBuilder {

    public static final String DEFAULT_CLASSNAME = "T16";
    public static final String DEFAULT_ModuleCode = "CG1111";
    public static final String DEFAULT_ENROLLMENT = "20";

    private ClassName className;
    private ModuleCode moduleCode;
    private Enrollment maxEnrollment;

    public ClassroomBuilder() {
        className = new ClassName(DEFAULT_CLASSNAME);
        moduleCode = new ModuleCode(DEFAULT_ModuleCode);
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
     * Sets the {@code ModuleCode} of the {@code Classroom} that we are building.
     */
    public ClassroomBuilder withModuleCode(String moduleCode) {
        this.moduleCode = new ModuleCode(moduleCode);
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
