package seedu.address.model.gradebook;

/**
 This class includes all necessary validation for gradebook objects.
 */
public class Gradebook {
    private String moduleCode;
    private String gradebookComponentName;
    private String gradebookNewComponentName;
    private int gradebookMaxMarks;
    private int gradebookWeightage;

    public Gradebook(
            String moduleCode,
            String gradebookComponentName,
            int gradebookMaxMarks,
            int gradebookWeightage) {
        this.moduleCode = moduleCode;
        this.gradebookComponentName = gradebookComponentName;
        this.gradebookMaxMarks = gradebookMaxMarks;
        this.gradebookWeightage = gradebookWeightage;
    }

    public Gradebook(
            String moduleCode,
            String gradebookComponentName,
            String gradebookNewComponentName,
            int gradebookMaxMarks,
            int gradebookWeightage) {
        this.moduleCode = moduleCode;
        this.gradebookComponentName = gradebookComponentName;
        this.gradebookNewComponentName = gradebookNewComponentName;
        this.gradebookMaxMarks = gradebookMaxMarks;
        this.gradebookWeightage = gradebookWeightage;
    }

    public Gradebook(String moduleCode, String gradebookComponentName) {
        this.moduleCode = moduleCode;
        this.gradebookComponentName = gradebookComponentName;
    }

    /**
     This method checks if user inputs empty values for module code or gradebook component name.
     */
    public static boolean hasEmptyParams(String moduleCode, String gradebookComponentName) {
        boolean empty = false;
        if (moduleCode.equals("") || gradebookComponentName.equals("")) {
            empty = true;
        }
        return empty;
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getgradebookNewComponentName() {
        return this.gradebookNewComponentName;
    }

    public String getGradeComponentName() {
        return this.gradebookComponentName;
    }

    public void setGradeComponentName (String gradebookComponentName) {
        this.gradebookComponentName = gradebookComponentName;
    }

    public int getGradeComponentMaxMarks() {
        return this.gradebookMaxMarks;
    }

    public void setgradebookMaxMarks (int gradebookMaxMarks) {
        this.gradebookMaxMarks = gradebookMaxMarks;
    }

    public int getGradeComponentWeightage() {
        return this.gradebookWeightage;
    }

    public void setgradebookWeightage (int gradebookWeightage) {
        this.gradebookWeightage = gradebookWeightage;
    }
}
