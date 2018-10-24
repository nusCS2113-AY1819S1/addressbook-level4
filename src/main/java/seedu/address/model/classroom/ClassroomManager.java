package seedu.address.model.classroom;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.StorageController;
import seedu.address.storage.adapter.XmlAdaptedClassroom;

/**
 * This classroom manager stores classrooms for Trajectory.
 */
public class ClassroomManager {
    private static final Logger logger = LogsCenter.getLogger(ClassroomManager.class);

    private static ClassroomManager classroomManager = null;
    private ArrayList<Classroom> classroomList = new ArrayList<>();

    private ClassroomManager() {
        readClassroomList();
    }

    public static ClassroomManager getInstance() {
        if (classroomManager == null) {
            classroomManager = new ClassroomManager();
        }
        return classroomManager;
    }

    /**
     * Adds a new classroomList to the in-memory array list
     */
    public void addClassroom(Classroom classroom) {
        classroomList.add(classroom);
    }

    /**
     * Finds a classroom from classroomList
     */
    public Classroom findClassroom(String className, String moduleCode) {
        for (Classroom classroom : classroomList) {
            if (classroom.getClassName().getValue().equalsIgnoreCase(className)
                    && classroom.getModuleCode().getValue().equalsIgnoreCase(moduleCode)) {
                return classroom;
            }
        }
        return null;
    }

    /**
     * Removes a classroom from classroomList
     */
    public void deleteClassroom(Classroom classroom) {
        classroomList.remove(classroom);
    }

    /**
     * Gets the classroom list from storage and converts it to a Classroom array list
     */
    private void readClassroomList() {
        ArrayList<XmlAdaptedClassroom> xmlClassroomList = StorageController.getClassesStorage();
        for (XmlAdaptedClassroom xmlClassroom : xmlClassroomList) {
            try {
                classroomList.add(xmlClassroom.toModelType());
            } catch (IllegalValueException e) {
                logger.info("Illegal values found when reading classroom list: " + e.getMessage());
            }
        }
    }

    /**
     * Converts the classroom array list and invokes the StorageController to save the current classroom list to file
     */
    public void saveClassroomList() {
        ArrayList<XmlAdaptedClassroom> xmlClassroomList =
                classroomList
                        .stream()
                        .map(XmlAdaptedClassroom::new)
                        .collect(Collectors.toCollection(ArrayList::new));
        StorageController.setClassesStorage(xmlClassroomList);
        StorageController.storeData();
    }

    public ArrayList<Classroom> getClassroomList() {
        return this.classroomList;
    }

    public void setClassroomList(ArrayList<Classroom> classroomList) {
        this.classroomList = classroomList;
    }

    /**
     * Returns true if a classroom with the same identity as {@code classToCreate} exists in the classroom list.
     */
    public boolean hasClassroom(Classroom classToCreate) {
        requireNonNull(classToCreate);
        return (findClassroom(classToCreate.getClassName().getValue(),
                classToCreate.getModuleCode().getValue())) != null;
    }
}
