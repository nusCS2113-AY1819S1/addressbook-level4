package seedu.address.model;

import java.nio.file.Paths;
import java.util.ArrayList;

import seedu.address.commons.util.XmlUtil;

/**
This class is a storage controller for the other datasets that work alongside the main student list.
 */
public class StorageController {

    private static final String STORAGE_COURSES = "courseList.xml";
    private static final String STORAGE_MODULES = "modules.xml";
    private static final String STORAGE_CLASSES = "classes.xml";
    private static final String STORAGE_GRADEBOOK = "gradebook.xml";
    private static final String STORAGE_NOTES = "notes.xml";

    private static ArrayList<Course> courseStorage = new ArrayList<Course>();
    private static ArrayList<Module> moduleStorage = new ArrayList<Module>();
    private static ArrayList<Course> classesStorage = new ArrayList<Course>();
    private static ArrayList<Course> gradebookStorage = new ArrayList<Course>();
    private static ArrayList<Course> noteStorage = new ArrayList<Course>();

    /**
     * This method retrieves all datasets saved locally.
     */
    public static void retrieveData() {
        try {
            CourseManager cm = XmlUtil.getDataFromFile(Paths.get(STORAGE_COURSES), CourseManager.class);
            courseStorage = cm.getList();

            ModuleManager moduleManager = XmlUtil.getDataFromFile(Paths.get(STORAGE_MODULES), ModuleManager.class);
            moduleStorage = moduleManager.getModules();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method stores all data within the arraylists above to local storage.
     */
    public static void storeData() {
        try {
            CourseManager cm = new CourseManager();
            cm.setCourseList(courseStorage);
            XmlUtil.saveDataToFile(Paths.get(STORAGE_COURSES), cm);

            ModuleManager moduleManager = new ModuleManager();
            moduleManager.setModules(moduleStorage);
            XmlUtil.saveDataToFile(Paths.get(STORAGE_MODULES), moduleManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Course> getCourseStorage() {
        return courseStorage;
    }

    public static void setCourseStorage(ArrayList<Course> courseStorage) {
        StorageController.courseStorage = courseStorage;
    }

    public static ArrayList<Module> getModuleStorage() {
        return moduleStorage;
    }

    public static void setModuleStorage(ArrayList<Module> moduleStorage) {
        StorageController.moduleStorage = moduleStorage;
    }

    public static ArrayList<Course> getClassesStorage() {
        return classesStorage;
    }

    public static void setClassesStorage(ArrayList<Course> classesStorage) {
        StorageController.classesStorage = classesStorage;
    }

    public static ArrayList<Course> getGradebookStorage() {
        return gradebookStorage;
    }

    public static void setGradebookStorage(ArrayList<Course> gradebookStorage) {
        StorageController.gradebookStorage = gradebookStorage;
    }

    public static ArrayList<Course> getNoteStorage() {
        return noteStorage;
    }

    public static void setNoteStorage(ArrayList<Course> noteStorage) {
        StorageController.noteStorage = noteStorage;
    }
}
