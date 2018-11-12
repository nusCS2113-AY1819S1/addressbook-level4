package seedu.address.testutil;

import seedu.address.model.grades.Grades;

/**
 * Creates dummy grades for testing.
 */
public class GradeBuilder {
    public static final String DEFAULT_MODULE_CODE = "CS2113";
    public static final String DEFAULT_COMPONENT_NAME = "Finals";
    public static final String DEFAULT_ADMIN_NO = "A0169988P";
    public static final float DEFAULT_MARKS = 10;
    private String moduleCode;
    private String gradebookComponentName;
    private String adminNo;
    private float marks;

    /**
     * Empty constructor for GradeBuilder that initializes the
     * grade with default values.
     */
    public GradeBuilder() {
        moduleCode = DEFAULT_MODULE_CODE;
        gradebookComponentName = DEFAULT_COMPONENT_NAME;
        adminNo = DEFAULT_ADMIN_NO;
        marks = DEFAULT_MARKS;
    }

    /**
     * Sets the {@code moduleCode} of the {@code Grades} that we are building.
     */
    public GradeBuilder withModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
        return this;
    }

    /**
     * Sets the {@code gradebookComponentName} of the {@code Grades} that we are building.
     */
    public GradeBuilder withComponentName(String componentName) {
        this.gradebookComponentName = componentName;
        return this;
    }

    /**
     * Sets the {@code adminNo} of the {@code Grades} that we are building.
     */
    public GradeBuilder withAdminNo(String adminNo) {
        this.adminNo = adminNo;
        return this;
    }

    /**
     * Sets the {@code marks} of the {@code Grades} that we are building.
     */
    public GradeBuilder withMarks(float marks) {
        this.marks = marks;
        return this;
    }

    /**
     * Creates a gradebook component from the data in the GradebookBuilder object.
     *
     * @return Gradebook object
     */
    public Grades build() {
        return new Grades(moduleCode, gradebookComponentName, adminNo, marks);
    }
}
