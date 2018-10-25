package seedu.address.model.gradebook;

import java.util.ArrayList;
import java.util.stream.Collectors;

import seedu.address.model.StorageController;
import seedu.address.storage.adapter.XmlAdaptedGradebook;

/**
 * The API of the GradebookManager component.
 */
public class GradebookManager {
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
     * Converts the Gradebook array list and invokes the StorageController to save the current gradebook list to file.
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
    public void addGradebookComponent (Gradebook gradebook) {
        gradebooks.add(gradebook);
    }

    /**
     This method deletes gradebook component to a module in Trajectory.
     */
    public void deleteGradebookComponent (Gradebook gradebook) {
        gradebooks.remove(gradebook);
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
     This method checks if module code and component name have empty inputs.
     */
    public boolean isEmpty (String moduleCode, String gradebookComponentName) {
        boolean isEmpty = false;
        if (moduleCode.equals("") || gradebookComponentName.equals("")) {
            isEmpty = true;
        }
        return isEmpty;
    }

    /**
     This method checks if module code and component name already exist.
     */
    public boolean isDuplicate (String moduleCode, String gradebookComponentName) {
        boolean isDuplicate = false;
        Gradebook gradebook = findGradebookComponent(moduleCode, gradebookComponentName);
        if (gradebook != null) {
            isDuplicate = true;
        }
        return isDuplicate;
    }

    /**
     This method checks if maximum marks are within acceptable range.
     */
    public boolean isMaxMarksValid (int gradebookMaxMarks) {
        boolean isMaxMarksValid = false;
        if (gradebookMaxMarks >= 0 && gradebookMaxMarks <= 100) {
            isMaxMarksValid = true;
        }
        return isMaxMarksValid;
    }

    /**
     This method checks if maximum marks are within acceptable range.
     */
    public boolean isWeightageValid (int gradebookWeightage) {
        boolean isWeightageValid = false;
        if (gradebookWeightage >= 0 && gradebookWeightage <= 100) {
            isWeightageValid = true;
        }
        return isWeightageValid;
    }
    /**
     This method checks if weightage adds up to a maximum of 100.
     */
    public boolean hasWeightageExceed (String moduleCode, int gradebookWeightage) {
        boolean hasWeightageExceed = false;
        int totalWeightage = 0;
        for (Gradebook gradebook : gradebooks) {
            if (gradebook.getModuleCode().equals(moduleCode)) {
                totalWeightage += gradebook.getGradeComponentWeightage();
            }
        }
        if (totalWeightage + gradebookWeightage > 101) {
            hasWeightageExceed = true;
        }
        return hasWeightageExceed;
    }
}

