package seedu.address.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import seedu.address.commons.util.XmlUtil;
import seedu.address.model.gradebook.GradebookComponent;
import seedu.address.model.gradebook.GradebookManager;
import seedu.address.model.note.Note;
import seedu.address.storage.adapter.XmlAdaptedClassroom;
import seedu.address.storage.adapter.XmlAdaptedModule;
import seedu.address.storage.serializable.XmlSerializableClassroomList;
import seedu.address.storage.serializable.XmlSerializableModuleList;

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

    private static ArrayList<Course> courseStorage = new ArrayList<Course>();
    private static ArrayList<XmlAdaptedModule> moduleStorage = new ArrayList<>();
    private static ArrayList<XmlAdaptedClassroom> classesStorage = new ArrayList<XmlAdaptedClassroom>();
    private static ArrayList<GradebookComponent> gradebookStorage = new ArrayList<GradebookComponent>();
    private static ArrayList<Note> noteStorage = new ArrayList<Note>();

    /**
     * This method retrieves all datasets saved locally.
     */
    public static void retrieveData() {
        createFiles();

        try {
            CourseManager cm = XmlUtil.getDataFromFile(Paths.get(STORAGE_COURSES), CourseManager.class);
            courseStorage = cm.getList();

            XmlSerializableModuleList moduleList =
                    XmlUtil.getDataFromFile(Paths.get(STORAGE_MODULES), XmlSerializableModuleList.class);
            moduleStorage = moduleList.getModules();

            XmlSerializableClassroomList classroomList =
                    XmlUtil.getDataFromFile(Paths.get(STORAGE_CLASSES), XmlSerializableClassroomList.class);
            classesStorage = classroomList.getClassroomList();

            NotesManager nm = (NotesManager) XmlUtil.getDataFromFile(Paths.get(STORAGE_NOTES), NotesManager.class);
            noteStorage = nm.getList();

            GradebookManager gradeManager = XmlUtil.getDataFromFile(Paths.get(STORAGE_GRADEBOOK),
                    GradebookManager.class);
            gradebookStorage = gradeManager.getList();
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

            XmlSerializableModuleList moduleList = new XmlSerializableModuleList();
            moduleList.setModules(moduleStorage);
            XmlUtil.saveDataToFile(Paths.get(STORAGE_MODULES), moduleList);

            XmlSerializableClassroomList classroomList = new XmlSerializableClassroomList();
            classroomList.setClassroomList(classesStorage);
            XmlUtil.saveDataToFile(Paths.get(STORAGE_CLASSES), classroomList);

            NotesManager nm = new NotesManager();
            nm.setNotesList(noteStorage);
            XmlUtil.saveDataToFile(Paths.get(STORAGE_NOTES), nm);

            GradebookManager gm = new GradebookManager();
            gm.setGradebookComponentList(gradebookStorage);
            XmlUtil.saveDataToFile(Paths.get(STORAGE_GRADEBOOK), gm);
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

    public static ArrayList<Note> getNoteStorage() {
        return noteStorage;
    }

    public static void setNoteStorage(ArrayList<Note> noteStorage) {
        StorageController.noteStorage = noteStorage;
    }
}
