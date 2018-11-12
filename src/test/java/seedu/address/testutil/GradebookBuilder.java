package seedu.address.testutil;

import seedu.address.model.gradebook.Gradebook;

/**
 * Creates dummy gradebook components for testing.
 */
public class GradebookBuilder {
    public static final String DEFAULT_MODULE_CODE = "CS2113";
    public static final String DEFAULT_GRADEBOOK_COMPONENT_NAME = "Finals";
    public static final int DEFAULT_GRADEBOOK_MAX_MARKS = 20;
    public static final int DEFAULT_GRADEBOOK_WEIGHTAGE = 10;
    private String moduleCode;
    private String gradebookComponentName;
    private int gradebookMaxMarks;
    private int gradebookWeightage;

    /**
     * Empty constructor for GradebookBuilder that initializes the
     * gradebook with default values.
     */
    public GradebookBuilder() {
        moduleCode = DEFAULT_MODULE_CODE;
        gradebookComponentName = DEFAULT_GRADEBOOK_COMPONENT_NAME;
        gradebookMaxMarks = DEFAULT_GRADEBOOK_MAX_MARKS;
        gradebookWeightage = DEFAULT_GRADEBOOK_WEIGHTAGE;
    }

    /**
     * Sets the {@code moduleCode} of the {@code Gradebook} that we are building.
     */
    public GradebookBuilder withModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
        return this;
    }

    /**
     * Sets the {@code gradebookComponentName} of the {@code Gradebook} that we are building.
     */
    public GradebookBuilder withComponentName(String componentName) {
        this.gradebookComponentName = componentName;
        return this;
    }

    /**
     * Sets the {@code gradebookMaxMarks} of the {@code Gradebook} that we are building.
     */
    public GradebookBuilder withMaxmarks(int maxMarks) {
        this.gradebookMaxMarks = maxMarks;
        return this;
    }

    /**
     * Sets the {@code gradebookWeightage} of the {@code Gradebook} that we are building.
     */
    public GradebookBuilder withWeightage(int weightage) {
        this.gradebookWeightage = weightage;
        return this;
    }

    /**
     * Creates a gradebook component from the data in the GradebookBuilder object.
     *
     * @return Gradebook object
     */
    public Gradebook build() {
        return new Gradebook(moduleCode, gradebookComponentName, gradebookMaxMarks, gradebookWeightage);
    }
}
