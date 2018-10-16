package seedu.address.model.gradebook;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.StorageController;

/**
 * The API of the GradebookManager component.
 */
public class GradebookManager {
    private static final String ADD_MESSAGE_SUCCESS = "Successfully Added! \nModule Code: %1$s"
            + "\nGradebook Component Name: %2$s" + "\nMaximum Marks: %3$s" + "\nWeightage: %4$s";
    private static final String EMPTY_RESULT = "Module code and gradebook component name cannot be empty";
    private static final String EXTRA_SPACE = "Extra spacings detected between %1$s";
    private static final String LIST_MESSAGE_SUCCESS = "Success! List of components in the module:";
    private static final String DELETE_MESSAGE_SUCCESS = "Successfully deleted!";
    private static final String DELETE_MESSAGE_FAIL = "Unsuccessful Deletion";
    private static final String FIND_MESSAGE_SUCCESS = "Successfully found!";
    private static final String FIND_MESSAGE_FAIL = "Unsuccessful find";
    private static final String DUPLICATE_RESULT = "Gradebook component already exist in Trajectory";

    /**
     This method adds gradebook component to a module in Trajectory.
     */
    public static String addGradebookComponent (String moduleCode,
                                                String gradebookComponentName,
                                                int gradebookComponentMaxMarks,
                                                int gradebookComponentWeightage) {
        String status = ADD_MESSAGE_SUCCESS;
        StorageController.retrieveData();

        boolean empty = Gradebook.emptyParams(moduleCode, gradebookComponentName);
        if (empty) {
            status = EMPTY_RESULT;
        }

        boolean duplicate = Gradebook.duplicateComponent(moduleCode, gradebookComponentName);
        if (duplicate) {
            status = DUPLICATE_RESULT;
        }

        boolean extraSpace = Gradebook.extraSpace(gradebookComponentName);
        if (extraSpace) {
            status = EXTRA_SPACE;
        }

        StorageController.getGradebookStorage().add(new XmlAdaptedGradebook(moduleCode, gradebookComponentName,
                gradebookComponentMaxMarks, gradebookComponentWeightage));
        StorageController.storeData();

        return String.format(status,
                moduleCode,
                gradebookComponentName,
                gradebookComponentMaxMarks,
                gradebookComponentWeightage);
    }

    /**
     This method lists gradebook component to a module in Trajectory.
     */
    public static CommandResult listGradebookComponent () {
        StorageController.retrieveData();
        StringBuilder sb = new StringBuilder();
        for (XmlAdaptedGradebook gc: StorageController.getGradebookStorage()) {
            sb.append("Module Code: ");
            sb.append(gc.getModuleCode() + "\n");
            sb.append("Grade Component Name: ");
            sb.append(gc.getGradeComponentName() + "\n");
        }
        return new CommandResult("\n" + LIST_MESSAGE_SUCCESS + "\n" + sb.toString());
    }

    /**
    This method deletes gradebook component to a module in Trajectory.
    */
    public static CommandResult deleteGradebookComponent (String moduleCode, String gradebookComponentName) {
        String status = DELETE_MESSAGE_FAIL;
        for (XmlAdaptedGradebook gc : StorageController.getGradebookStorage()) {
            if (gc.getModuleCode().equals(moduleCode) && gc.getGradeComponentName().equals(gradebookComponentName)) {
                StorageController.getGradebookStorage().remove(gc);
                StorageController.storeData();
                status = DELETE_MESSAGE_SUCCESS;
                break;
            }
        }
        return new CommandResult("\n" + status);
    }

    /**
     This method finds gradebook component to a module in Trajectory.
     */
    public static CommandResult findGradebookComponent (String moduleCode, String gradebookComponentName) {
        String status = FIND_MESSAGE_FAIL;
        StorageController.retrieveData();
        StringBuilder sb = new StringBuilder();
        for (XmlAdaptedGradebook gc: StorageController.getGradebookStorage()) {
            if (gc.getModuleCode().equals(moduleCode) && gc.getGradeComponentName().equals(gradebookComponentName)) {
                status = FIND_MESSAGE_SUCCESS;
                sb.append("Module Code: ");
                sb.append(gc.getModuleCode() + "\n");
                sb.append("Grade Component: ");
                sb.append(gc.getGradeComponentName() + "\n");
            }
        }
        return new CommandResult("\n" + status + "\n" + sb.toString());
    }
}
