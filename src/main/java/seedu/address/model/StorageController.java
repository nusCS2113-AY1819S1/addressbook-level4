package seedu.address.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import seedu.address.commons.util.XmlUtil;
import seedu.address.storage.adapter.XmlAdaptedClassroom;
import seedu.address.storage.adapter.XmlAdaptedCourse;
import seedu.address.storage.adapter.XmlAdaptedGradebook;
import seedu.address.storage.adapter.XmlAdaptedModule;
import seedu.address.storage.adapter.XmlAdaptedNote;
import seedu.address.storage.serializable.XmlSerializableClassroomList;
import seedu.address.storage.serializable.XmlSerializableCourseList;
import seedu.address.storage.serializable.XmlSerializableGradebookList;
import seedu.address.storage.serializable.XmlSerializableModuleList;
import seedu.address.storage.serializable.XmlSerializableNoteList;

/**
 * This class is a storage controller for the other datasets that work alongside the main student list.
 */
public class StorageController {
    private static final String BASE_DIRECTORY = "data/";
    private static final String STORAGE_COURSES = BASE_DIRECTORY + "courseList.xml";
    private static final String STORAGE_MODULES = BASE_DIRECTORY + "modules.xml";
    private static final String STORAGE_CLASSES = BASE_DIRECTORY + "classes.xml";
    private static final String STORAGE_GRADEBOOK = BASE_DIRECTORY + "gradebook.xml";
    private static final String STORAGE_NOTES = BASE_DIRECTORY + "notes.xml";

    private static ArrayList<XmlAdaptedCourse> courseStorage = new ArrayList<XmlAdaptedCourse>();
    private static ArrayList<XmlAdaptedModule> moduleStorage = new ArrayList<>();
    private static ArrayList<Classroom> classesStorage = new ArrayList<Classroom>();
    private static ArrayList<XmlAdaptedGradebook> gradebookStorage = new ArrayList<>();
    private static ArrayList<XmlAdaptedNote> noteStorage = new ArrayList<>();

    /**
     * This method retrieves all datasets saved locally.
     */
    public static void retrieveData() {
        createFiles();
        try {
            XmlSerializableCourseList cl = XmlUtil
                    .getDataFromFile(Paths.get(STORAGE_COURSES), XmlSerializableCourseList.class);
            courseStorage = cl.getList();

            XmlSerializableModuleList moduleList =
                    XmlUtil.getDataFromFile(Paths.get(STORAGE_MODULES), XmlSerializableModuleList.class);
            moduleStorage = moduleList.getModules();

            XmlSerializableClassroomList classroomList =
                    XmlUtil.getDataFromFile(Paths.get(STORAGE_CLASSES), XmlSerializableClassroomList.class);
            classesStorage = classroomList.getClassroomList();

            XmlSerializableNoteList noteList =
                    XmlUtil.getDataFromFile(Paths.get(STORAGE_NOTES), XmlSerializableNoteList.class);
            noteStorage = noteList.getNotes();

            XmlSerializableGradebookList gradebookSerializable = XmlUtil.getDataFromFile(Paths.get(STORAGE_GRADEBOOK),
                    XmlSerializableGradebookList.class);
            gradebookStorage = gradebookSerializable.getGradebookList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method creates files for all datasets if they do not exist on the local filesystem.
     */
    private static void createFiles() {
        File classes = new File(STORAGE_CLASSES);
        File courses = new File(STORAGE_COURSES);
        File modules = new File(STORAGE_MODULES);
        File notes = new File(STORAGE_NOTES);
        File gradebook = new File(STORAGE_GRADEBOOK);
        try {
            classes.createNewFile();
            courses.createNewFile();
            modules.createNewFile();
            notes.createNewFile();
            gradebook.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     This method stores all data within the arraylists above to local storage.
     */
    public static void storeData() {
        try {
            XmlSerializableCourseList cl = new XmlSerializableCourseList();
            cl.setCourseList(courseStorage);
            XmlUtil.saveDataToFile(Paths.get(STORAGE_COURSES), cl);

            XmlSerializableModuleList moduleList = new XmlSerializableModuleList();
            moduleList.setModules(moduleStorage);
            XmlUtil.saveDataToFile(Paths.get(STORAGE_MODULES), moduleList);

            XmlSerializableClassroomList classroomList = new XmlSerializableClassroomList();
            classroomList.setClassroomList(classesStorage);
            XmlUtil.saveDataToFile(Paths.get(STORAGE_CLASSES), classroomList);

            XmlSerializableNoteList noteList = new XmlSerializableNoteList();
            noteList.setNotes(noteStorage);
            XmlUtil.saveDataToFile(Paths.get(STORAGE_NOTES), noteList);

            XmlSerializableGradebookList gradebookList = new XmlSerializableGradebookList();
            gradebookList.setGradebookList(gradebookStorage);
            XmlUtil.saveDataToFile(Paths.get(STORAGE_GRADEBOOK), gradebookList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<XmlAdaptedCourse> getCourseStorage() {
        return courseStorage;
    }

    public static void setCourseStorage(ArrayList<XmlAdaptedCourse> courseStorage) {
        StorageController.courseStorage = courseStorage;
    }

    public static ArrayList<XmlAdaptedModule> getModuleStorage() {
        return moduleStorage;
    }

    public static void setModuleStorage(ArrayList<XmlAdaptedModule> moduleList) {
        moduleStorage = moduleList;
    }

    public static ArrayList<XmlAdaptedClassroom> getClassesStorage() {
        return classesStorage;
    }

    public static void setClassesStorage(ArrayList<XmlAdaptedClassroom> classesStorage) {
        StorageController.classesStorage = classesStorage;
    }

    public static ArrayList<GradebookComponent> getGradebookStorage() {
        return gradebookStorage;
    }

    public static void setGradebookStorage(ArrayList<GradebookComponent> gradebookStorage) {
        StorageController.gradebookStorage = gradebookStorage;
    }

    public static ArrayList<XmlAdaptedNote> getNoteStorage() {
        return noteStorage;
    }

    public static void setNoteStorage(ArrayList<XmlAdaptedNote> noteList) {
        noteStorage = noteList;
    }
}
