package seedu.address.model;

import java.nio.file.Paths;
import java.util.ArrayList;

import seedu.address.commons.util.XmlUtil;
import seedu.address.model.note.Note;

/**
 This class is a storage controller for the other datasets that work alongside the main student list.
 */
public class StorageController {

    private static final String STORAGE_COURSES = "courseList.xml";
    private static final String STORAGE_MODULES = "modules.xml";
    private static final String STORAGE_CLASSES = "classes.xml";
    private static final String STORAGE_GRADEBOOK = "gradebook.xml";
    private static final String STORAGE_NOTES = "notes.xml";

//    private static ArrayList<Course> courseStorage = new ArrayList<Course>();
//    private static ArrayList<Course> moduleStorage = new ArrayList<Course>();
//    private static ArrayList<Course> classesStorage = new ArrayList<Course>();
//    private static ArrayList<Course> gradebookStorage = new ArrayList<Course>();
    private static ArrayList<Note> noteStorage = new ArrayList<Note>();

    /**
     This method retrieves all datasets saved locally.
     */
    public static void retrieveData() {
        try {
            NotesManager nm = (NotesManager) XmlUtil.getDataFromFile(Paths.get(STORAGE_NOTES), NotesManager.class);
            noteStorage = nm.getList();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     This method stores all data within the arraylists above to local storage.
     */
    public static void storeData() {
        try {
            NotesManager nm = new NotesManager();
            nm.setNotesList(noteStorage);
            XmlUtil.saveDataToFile(Paths.get(STORAGE_NOTES), nm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public static ArrayList<Note> getCourseStorage() {
//        return courseStorage;
//    }
//
//    public static void setCourseStorage(ArrayList<Course> courseStorage) {
//        StorageController.courseStorage = courseStorage;
//    }
//
//    public static ArrayList<Course> getModuleStorage() {
//        return moduleStorage;
//    }
//
//    public static void setModuleStorage(ArrayList<Course> moduleStorage) {
//        StorageController.moduleStorage = moduleStorage;
//    }
//
//    public static ArrayList<Course> getClassesStorage() {
//        return classesStorage;
//    }
//
//    public static void setClassesStorage(ArrayList<Course> classesStorage) {
//        StorageController.classesStorage = classesStorage;
//    }
//
//    public static ArrayList<Course> getGradebookStorage() {
//        return gradebookStorage;
//    }
//
//    public static void setGradebookStorage(ArrayList<Course> gradebookStorage) {
//        StorageController.gradebookStorage = gradebookStorage;
//    }

    public static ArrayList<Note> getNoteStorage() {
        return noteStorage;
    }

    public static void setNoteStorage(ArrayList<Note> noteStorage) {
        StorageController.noteStorage = noteStorage;
    }
}