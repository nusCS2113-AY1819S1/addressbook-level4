package seedu.address.model.gradebook;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
    private static final String DELETE_MESSAGE_SUCCESS = "\nModule Code: %1$s \nGradebook Component: %2$s \n"
            + "Successfully deleted!";
    private static final String DELETE_MESSAGE_FAIL = "\nUnsuccessful Deletion";

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
     * Converts the Note array list and invokes the StorageController to save the current note list to file.
     */
    public void saveGradebookList() {
        ArrayList<XmlAdaptedGradebook> xmlAdaptedGradebooks =
                gradebooks.stream().map(XmlAdaptedGradebook::new).collect(Collectors.toCollection(ArrayList::new));
        StorageController.setGradebookStorage(xmlAdaptedGradebooks);
        StorageController.storeData();
    }

    /**
     This method adds gradebook component to a module in Trajectory.
     */
    public String addGradebookComponent (String moduleCode,
                                                String gradebookComponentName,
                                                int gradebookMaxMarks,
                                                int gradebookWeightage) {
        String status = MESSAGE_ADD_SUCCESS;
        boolean isEmpty = Gradebook.hasEmptyParams(moduleCode, gradebookComponentName);
        boolean hasDuplicate = isDuplicateComponent(moduleCode, gradebookComponentName);

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
        return String.format(status,
                moduleCode,
                gradebookComponentName);
    }

    /**
     This method finds gradebook component to a module in Trajectory.
     */
    public Gradebook findGradebookComponent (String moduleCode, String gradebookComponentName) {
        for (Gradebook gradebook : gradebooks) {
            if (gradebook.getModuleCode().equals(moduleCode)
                    && gradebook.getGradeComponentName().equals(gradebookComponentName)) {
                return gradebook;
            }
        }
        return null;
    }

    /**
     This method checks if component already exists in Trajectory.
     */
    public boolean isDuplicateComponent (String moduleCode, String gradebookComponentName) {
        StorageController.retrieveData();
        boolean duplicate = false;

        Gradebook gradebook = findGradebookComponent(moduleCode, gradebookComponentName);
        if (gradebook != null) {
            duplicate = true;
        }

        return duplicate;
    }
}

