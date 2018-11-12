package seedu.address.testutil;

import seedu.address.model.grades.Grades;

/**
 * Creates dummy grades for testing.
 */
public class GradeBuilder {
    public static final String DEFAULT_MODULE_CODE = "CS2113";
    public static final String DEFAULT_COMPONENT_NAME = "Finals";
    public static final String DEFAULT_ADMIN_NO = "A0169988P";
    public static final int DEFAULT_MARKS = 10;
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
     * Creates a grade from the data in the GradeBuilder object.
     *
     * @return Grades object
     */
    public Grades build() {
        return new Grades(
                moduleCode,
                gradebookComponentName,
                adminNo,
                marks);
    }
}
