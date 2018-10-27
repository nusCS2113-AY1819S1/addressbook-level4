package seedu.address.model.grades;

import seedu.address.model.StorageController;
import seedu.address.storage.adapter.XmlAdaptedGrades;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * The API of the GradesManager component.
 */
public class GradesManager {
    private ArrayList<Grades> grades = new ArrayList<>();

    public GradesManager() {
        readGradesList();
    }
    public ArrayList<Grades> getGrades() {
        return grades;
    }

    /**
     * Gets grades list from storage and converts it to a Grade array list
     */
    private void readGradesList() {
        ArrayList<XmlAdaptedGrades> xmlGradesList = StorageController.getGradeStorage();
        for (XmlAdaptedGrades xmlGrades : xmlGradesList) {
            grades.add(xmlGrades.toGradeType());
        }
    }

    /**
     * Converts the Gradebook array list and invokes the StorageController to save the current gradebook list to file.
     */
    public void saveGradeList() {
        ArrayList<XmlAdaptedGrades> xmlAdaptedGrades =
                grades.stream().map(XmlAdaptedGrades::new).collect(Collectors.toCollection(ArrayList::new));
        StorageController.setGradeStorage(xmlAdaptedGrades);
        StorageController.storeData();
    }

    public void clearGrade() {
        grades.clear();
    }

    /**
     This method adds gradebook component to a module in Trajectory.
     */
    public void addGrade (Grades grade) {
        grades.add(grade);
    }

    /**
     This method checks if module code and component name have empty inputs.
     */
    public boolean isEmpty (String moduleCode, String gradebookComponentName, String adminNo) {
        boolean isEmpty = false;
        if (moduleCode.equals("") || gradebookComponentName.equals("") || adminNo.equals("")) {
            isEmpty = true;
        }
        return isEmpty;
    }


}
