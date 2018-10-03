package seedu.address.model;

import java.io.File;
import java.io.IOException;
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
    private static ArrayList<Course> moduleStorage = new ArrayList<Course>();
    private static ArrayList<Course> classesStorage = new ArrayList<Course>();
    private static ArrayList<Course> gradebookStorage = new ArrayList<Course>();
    private static ArrayList<Course> noteStorage = new ArrayList<Course>();



    /**
    This method retrieves all datasets saved locally.
   */
    public static void retrieveData() {
        createFiles();

        try {
            CourseManager cm = (CourseManager) XmlUtil.getDataFromFile(Paths.get(STORAGE_COURSES), CourseManager.class);
            courseStorage = cm.getList();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     This method creates files for all datasets if they do not exist on the local filesystem.
     */
    private static void createFiles() {
        File gradebook = new File(STORAGE_GRADEBOOK);
        File classes = new File(STORAGE_CLASSES);
        File courses = new File(STORAGE_COURSES);
        File modules = new File(STORAGE_MODULES);
        File notes = new File(STORAGE_NOTES);
        try {
            gradebook.createNewFile();
            classes.createNewFile();
            courses.createNewFile();
            modules.createNewFile();
            notes.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
  This method stores all data within the arraylists above to local storage.
   */
    public static void storeData() {
        try {
            CourseManager cm = new CourseManager();
            cm.setCourseList(courseStorage);
            XmlUtil.saveDataToFile(Paths.get(STORAGE_COURSES), cm);
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

    public static ArrayList<Course> getModuleStorage() {
        return moduleStorage;
    }

    public static void setModuleStorage(ArrayList<Course> moduleStorage) {
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
