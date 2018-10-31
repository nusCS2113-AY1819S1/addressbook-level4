package seedu.address.model.gradebook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import seedu.address.model.StorageController;
import seedu.address.storage.adapter.XmlAdaptedGradebook;
import seedu.address.ui.HtmlTableProcessor;

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

    public void clearGradebook() {
        gradebooks.clear();
    }

    /**
     This method adds gradebook component to a module in Trajectory.
     */
    public void addGradebookComponent (Gradebook gradebook) {
        gradebooks.add(gradebook);
    }

    /**
     This method adds gradebook component to a module in Trajectory.
     */
    public String listGradebookComponent () {
        StringBuilder sb = new StringBuilder();
        int index = 1;

        sb.append(HtmlTableProcessor.getH3Representation("Gradebook List"));
        sb.append(HtmlTableProcessor.renderTableStart(new ArrayList<String>(
                Arrays.asList("Index", "Module Code", "Component Name", "Maximum Marks", "Weightage"))));

        sb.append(HtmlTableProcessor.getTableItemStart());
        for (Gradebook gradebook: getGradebooks()) {
            sb.append(HtmlTableProcessor
                    .renderTableItem(new ArrayList<String>(Arrays
                            .asList(Integer.toString(index++),
                                    gradebook.getModuleCode(),
                                    gradebook.getGradeComponentName(),
                                    Integer.toString(gradebook.getGradeComponentMaxMarks()),
                                    Integer.toString(gradebook.getGradeComponentWeightage())))));
        }
        sb.append(HtmlTableProcessor.getTableItemEnd());
        return sb.toString();
    }

    /**
     This method gets size of gradebook.
     */
    public int getGradebookSize () {
        int count = getGradebooks().size();
        return count;
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
     This method checks if module code have empty inputs.
     */
    public boolean isModuleCodeEmpty (String moduleCode) {
        boolean isModuleCodeEmpty = false;
        if (moduleCode.equals("")) {
            isModuleCodeEmpty = true;
        }
        return isModuleCodeEmpty;
    }

    /**
     This method checks if component name have empty inputs.
     */
    public boolean isComponentNameEmpty (String componentName) {
        boolean isComponentNameEmpty = false;
        if (componentName.equals("")) {
            isComponentNameEmpty = true;
        }
        return isComponentNameEmpty;
    }

    /**
     This method checks if module code and component name have empty inputs.
     */
    public boolean isModuleCodeAndComponentNameEmpty (String moduleCode, String gradebookComponentName) {
        boolean isModuleCodeAndComponentNameEmpty = false;
        if (moduleCode.equals("") && gradebookComponentName.equals("")) {
            isModuleCodeAndComponentNameEmpty = true;
        }
        return isModuleCodeAndComponentNameEmpty;
    }

    /**
     This method checks if edited params are valid.
     */
    public boolean isParamsValid (String newGradebookComponentName, int maxMarks, int weightage) {
        boolean isParamsValid = true;
        if (newGradebookComponentName.equals("") && maxMarks == 0 && weightage == 0) {
            isParamsValid = false;
        }
        return isParamsValid;
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
     This method checks if maximum marks and weightage are within acceptable range.
     */
    public boolean isIntParamsValid (int gradebookMaxMarks, int gradebookWeightage) {
        boolean isIntParamsValid = true;
        if (!isMaxMarksValid(gradebookMaxMarks) && !isWeightageValid(gradebookWeightage)) {
            isIntParamsValid = false;
        }
        return isIntParamsValid;
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
     This method checks if edited weightage adds up to a maximum of 100.
     */
    public boolean hasEditWeightageExceed (String moduleCode, String gradeComponentName, int gradebookWeightage) {
        boolean hasEditWeightageExceed = false;
        int totalWeightage = 0;
        for (Gradebook gradebook : gradebooks) {
            if (gradebook.getModuleCode().equals(moduleCode)
                    && !gradebook.getGradeComponentName().equals(gradeComponentName)) {
                totalWeightage += gradebook.getGradeComponentWeightage();
            }
        }
        if (totalWeightage + gradebookWeightage > 100) {
            hasEditWeightageExceed = true;
        }
        return hasEditWeightageExceed;
    }

    /**
     This method checks if added weightage adds up to a maximum of 100.
     */
    public boolean hasAddWeightageExceed (String moduleCode, int gradebookWeightage) {
        boolean hasAddWeightageExceed = false;
        int totalWeightage = 0;
        for (Gradebook gradebook : gradebooks) {
            if (gradebook.getModuleCode().equals(moduleCode)) {
                totalWeightage += gradebook.getGradeComponentWeightage();
            }
        }
        if (totalWeightage + gradebookWeightage > 100) {
            hasAddWeightageExceed = true;
        }
        return hasAddWeightageExceed;
    }

    /**
     This method checks if marks assigned is within range of max marks of grade component.
     */
    public boolean hasMarksExceed (String moduleCode, String gradebookComponentName, float studentMarks) {
        boolean isMarksValid = true;
        GradebookManager gradebookManager = new GradebookManager();
        Gradebook gradebook = gradebookManager.findGradebookComponent(moduleCode, gradebookComponentName);

        if (studentMarks > gradebook.getGradeComponentMaxMarks()) {
            isMarksValid = false;
        }

        return isMarksValid;
    }

    /**
     This method gets max marks of grade component.
     */
    public int getMaxMarks (String moduleCode, String gradebookComponentName) {
        GradebookManager gradebookManager = new GradebookManager();
        Gradebook gradebook = gradebookManager.findGradebookComponent(moduleCode, gradebookComponentName);
        return gradebook.getGradeComponentMaxMarks();
    }

    /**
     This method checks if gradebook component exist.
     */
    public boolean isGradeComponentValid (String moduleCode, String gradebookComponentName) {
        boolean isGradeComponentValid = true;
        GradebookManager gradebookManager = new GradebookManager();
        Gradebook gradebook = gradebookManager.findGradebookComponent(moduleCode,
                gradebookComponentName);
        if (gradebook == null) {
            isGradeComponentValid = false;
        }
        return isGradeComponentValid;
    }
}

