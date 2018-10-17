package seedu.address.model.gradebook;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.StorageController;
import seedu.address.storage.adapter.XmlAdaptedGradebook;

/**
 This class includes all necessary validation for gradebook objects.
 */
public class Gradebook {
    private static final String MESSAGE_FIND_SUCCESS = "Successfully found!";
    private static final String MESSAGE_FIND_FAIL = "Unsuccessful find";

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

    /**
     This method checks if component already exists in Trajectory.
     */
    public static boolean isDuplicateComponent (String moduleCode, String gradebookComponentName) {
        StorageController.retrieveData();
        boolean duplicate = false;
        if (GradebookManager.findGradebookComponent(moduleCode, gradebookComponentName)
                .feedbackToUser.contains(MESSAGE_FIND_SUCCESS)) {
            duplicate = true;
        }
        return duplicate;
    }

    /**
     This method finds gradebook component to a module in Trajectory.
     */
    public static CommandResult findGradebookComponent (String moduleCode, String gradebookComponentName) {
        String status = MESSAGE_FIND_FAIL;

        StringBuilder sb = new StringBuilder();
        for (XmlAdaptedGradebook gc: StorageController.getGradebookStorage()) {
            if (gc.getModuleCode().equals(moduleCode) && gc.getGradeComponentName().equals(gradebookComponentName)) {
                status = MESSAGE_FIND_SUCCESS;
                sb.append("Module Code: ");
                sb.append(gc.getModuleCode() + "\n");
                sb.append("Grade Component: ");
                sb.append(gc.getGradeComponentName() + "\n");
                sb.append("Maximum Marks: ");
                sb.append(gc.getGradeComponentMaxMarks() + "\n");
                sb.append("Weightage: ");
                sb.append(gc.getGradeComponentWeightage() + "\n");
            }
        }
        return new CommandResult("\n" + status + "\n" + sb.toString());
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
