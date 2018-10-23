package seedu.address.model.gradebook;

/**
 This class includes all necessary validation for gradebook objects.
 */
public class Gradebook {
    private String moduleCode;
    private String gradebookComponentName;
    private int gradebookMaxMarks;
    private int gradebookWeightage;

    public Gradebook(String moduleCode, String gradebookComponentName,
                     int gradebookMaxMarks, int gradebookWeightage) {
        this.moduleCode = moduleCode;
        this.gradebookComponentName = gradebookComponentName;
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

    public String getGradeComponentName() {
        return this.gradebookComponentName;
    }

    public int getGradeComponentMaxMarks() {
        return this.gradebookMaxMarks;
    }

    public int getGradeComponentWeightage() {
        return this.gradebookWeightage;
    }
}
