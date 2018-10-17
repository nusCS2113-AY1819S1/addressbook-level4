package seedu.address.model.gradebook;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.StorageController;
import seedu.address.storage.adapter.XmlAdaptedGradebook;

/**
 * The API of the GradebookManager component.
 */
public class GradebookManager {
    private static final String MESSAGE_ERROR_EMPTY = "Module code and gradebook component name cannot be empty";
    private static final String MESSAGE_ADD_SUCCESS = "Successfully Added! \nModule Code: %1$s"
            + "\nGradebook Component Name: %2$s" + "\nMaximum Marks: %3$s" + "\nWeightage: %4$s";
    private static final String MESSAGE_ERROR_DUPLICATE = "Gradebook component already exist in Trajectory";
    private static final String MESSAGE_LIST_SUCCESS = "Number of Grade Components Listed: ";
    private static final String MESSAGE_FIND_SUCCESS = "Successfully found!";
    private static final String MESSAGE_FIND_FAIL = "Unsuccessful find";
    private static final String DELETE_MESSAGE_SUCCESS = "Module Code: %1$s , Gradebook Component: %$2s."
            + "Successfully deleted!";
    private static final String DELETE_MESSAGE_FAIL = "Unsuccessful Deletion";

    /**
     This method adds gradebook component to a module in Trajectory.
     */
    public static String addGradebookComponent (String moduleCode,
                                                String gradebookComponentName,
                                                int gradebookMaxMarks,
                                                int gradebookWeightage) {
        String status = MESSAGE_ADD_SUCCESS;
        boolean isEmpty = Gradebook.hasEmptyParams(moduleCode, gradebookComponentName);
        boolean hasDuplicate = Gradebook.isDuplicateComponent(moduleCode, gradebookComponentName);

        if (isEmpty) {
            status = MESSAGE_ERROR_EMPTY;
        }

        if (hasDuplicate) {
            status = MESSAGE_ERROR_DUPLICATE;
        }

        if (!isEmpty && !hasDuplicate) {
            StorageController.getGradebookStorage().add(new XmlAdaptedGradebook(moduleCode, gradebookComponentName,
                    gradebookMaxMarks, gradebookWeightage));
            StorageController.storeData();
        }

        return String.format(status,
                moduleCode,
                gradebookComponentName,
                gradebookMaxMarks,
                gradebookWeightage);
    }

    /**
     This method lists gradebook component to a module in Trajectory.
     */
    public static CommandResult listGradebookComponent () {
        StorageController.retrieveData();
        CommandResult result = Gradebook.listGradebookComponent();
        return result;
    }

    /**
    This method deletes gradebook component to a module in Trajectory.
    */
    public static String deleteGradebookComponent (String moduleCode, String gradebookComponentName) {
        String status = DELETE_MESSAGE_FAIL;
        for (XmlAdaptedGradebook gc : StorageController.getGradebookStorage()) {
            if (gc.getModuleCode().equals(moduleCode) && gc.getGradeComponentName().equals(gradebookComponentName)) {
                StorageController.getGradebookStorage().remove(gc);
                StorageController.storeData();
                status = DELETE_MESSAGE_SUCCESS;
                break;
            }
        }
        return String.format(status , moduleCode, gradebookComponentName);
    }

    /**
     This method finds gradebook component to a module in Trajectory.
     */
    public static CommandResult findGradebookComponent (String moduleCode, String gradebookComponentName) {
        StorageController.retrieveData();
        CommandResult result = Gradebook.findGradebookComponent(moduleCode, gradebookComponentName);
        return result;
    }
}
