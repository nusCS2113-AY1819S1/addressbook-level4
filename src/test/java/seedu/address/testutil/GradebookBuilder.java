package seedu.address.testutil;

import seedu.address.model.gradebook.Gradebook;
import seedu.address.model.module.ModuleCode;

public class GradebookBuilder {

    public static final String DEFAULT_MODULE_CODE = "CS2113";
    public static final String DEFAULT_GRADEBOOK_COMPONENT_NAME = "Finals";
    public static final int DEFAULT_GRADEBOOK_MAX_MARKS = 20;
    public static final int DEFAULT_GRADEBOOK_WEIGHTAGE = 10;

    private String moduleCode;
    private String gradebookComponentName;
    private int gradebookMaxMarks;
    private int gradebookWeightage;

    public GradebookBuilder() {
        moduleCode = DEFAULT_MODULE_CODE;
        gradebookComponentName = DEFAULT_GRADEBOOK_COMPONENT_NAME;
        gradebookMaxMarks = DEFAULT_GRADEBOOK_MAX_MARKS;
        gradebookWeightage = DEFAULT_GRADEBOOK_WEIGHTAGE;
    }

    /**
     * Initializes the GradebookBuilder with the data of {@code gradebookToCopy}.
     */
    public GradebookBuilder(Gradebook gradebookToCopy) {
        moduleCode = gradebookToCopy.getModuleCode();
        gradebookComponentName = gradebookToCopy.getGradeComponentName();
        gradebookMaxMarks = gradebookToCopy.getGradeComponentMaxMarks();
        gradebookWeightage = gradebookToCopy.getGradeComponentWeightage();
    }

    /**
     * Initializes the GradebookBuilder with given parameters.
     */
    public GradebookBuilder(String moduleCode,
                            String gradebookComponentName,
                            int gradebookMaxMarks,
                            int gradebookWeightage) {
        this.moduleCode = moduleCode;
        this.gradebookComponentName = gradebookComponentName;
        this.gradebookMaxMarks = gradebookMaxMarks;
        this.gradebookWeightage = gradebookWeightage;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getGradebookComponentName() {
        return gradebookComponentName;
    }

    public void setGradebookComponentName(String gradebookComponentName) {
        this.gradebookComponentName = gradebookComponentName;
    }

    public int getGradebookMaxMarks() {
        return this.gradebookMaxMarks;
    }

    public void setGradebookMaxMarks(int gradebookMaxMarks) {
        this.gradebookMaxMarks = gradebookMaxMarks;
    }

    public int getGradebookWeightage() {
        return gradebookWeightage;
    }

    public void setGradebookWeightage(int gradebookWeightage) {
        this.gradebookWeightage = gradebookWeightage;
    }

    public Gradebook build() {
        return new Gradebook(
                moduleCode,
                gradebookComponentName,
                gradebookMaxMarks,
                gradebookWeightage);
    }
}
