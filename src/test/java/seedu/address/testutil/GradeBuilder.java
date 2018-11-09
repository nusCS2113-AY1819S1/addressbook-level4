package seedu.address.testutil;

import seedu.address.model.grades.Grades;

public class GradeBuilder {
    public static final String DEFAULT_MODULE_CODE = "CS2113";
    public static final String DEFAULT_COMPONENT_NAME = "Finals";
    public static final String DEFAULT_ADMIN_NO = "A0169988P";
    public static final int DEFAULT_MARKS = 10;
    private String moduleCode;
    private String gradebookComponentName;
    private String adminNo;
    private float marks;

    public GradeBuilder() {
        moduleCode = DEFAULT_MODULE_CODE;
        gradebookComponentName = DEFAULT_COMPONENT_NAME;
        adminNo = DEFAULT_ADMIN_NO;
        marks = DEFAULT_MARKS;
    }

    public Grades build() {
        return new Grades(
                moduleCode,
                gradebookComponentName,
                adminNo,
                marks);
    }
}
