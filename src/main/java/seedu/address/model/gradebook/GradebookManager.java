package seedu.address.model.gradebook;

import java.util.ArrayList;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.StorageController;
import seedu.address.storage.adapter.XmlAdaptedGradebook;

/**
 * The API of the GradebookManager component.
 */
public class GradebookManager {
    private static final String MESSAGE_ERROR_EMPTY = "Module code and gradebook component name cannot be empty";
    private static final String MESSAGE_ADD_SUCCESS = "\nSuccessfully Added! \nModule Code: %1$s"
            + "\nGradebook Component Name: %2$s" + "\nMaximum Marks: %3$s" + "\nWeightage: %4$s";
    private static final String MESSAGE_ERROR_DUPLICATE = "Gradebook component already exist in Trajectory";
    private static final String MESSAGE_FIND_SUCCESS = "Successfully found!";
    private static final String MESSAGE_FIND_FAIL = "Unsuccessful find";
    private static final String MESSAGE_LIST_SUCCESS = "\nNumber of Grade Components Listed: %1$s \n%2$s";
    private static final String DELETE_MESSAGE_SUCCESS = "Successfully deleted!";
    private static final String DELETE_MESSAGE_FAIL = "Unsuccessful Deletion";

    private ArrayList<Gradebook> gradebooks = new ArrayList<>();

    public GradebookManager() {
        readGradebookComponentsList();
    }

    public ArrayList<Gradebook> getGradebooks() {
        return gradebooks;
    }


    /**
     * Gets gradebook component list from storage and converts it to a Gradebook array list
     */
    private void readGradebookComponentsList() {
        ArrayList<XmlAdaptedGradebook> xmlGradebookList = StorageController.getGradebookStorage();
        for (XmlAdaptedGradebook xmlGradebook : xmlGradebookList) {
            gradebooks.add(xmlGradebook.toGradebookType());
        }
    }

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
        StorageController.retrieveData();
        CommandResult result = Gradebook.findGradebookComponent(moduleCode, gradebookComponentName);
        return result;
    }
}

