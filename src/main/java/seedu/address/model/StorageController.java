package seedu.address.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import seedu.address.commons.util.XmlUtil;
import seedu.address.storage.adapter.XmlAdaptedClassroom;
import seedu.address.storage.adapter.XmlAdaptedClassroomAttendance;
import seedu.address.storage.adapter.XmlAdaptedCourse;
import seedu.address.storage.adapter.XmlAdaptedGradebook;
import seedu.address.storage.adapter.XmlAdaptedModule;
import seedu.address.storage.adapter.XmlAdaptedNote;
import seedu.address.storage.adapter.XmlAdaptedStudentModule;
import seedu.address.storage.adapter.XmlAdaptedUser;
import seedu.address.storage.serializable.XmlSerializableClassroomAttendanceList;
import seedu.address.storage.serializable.XmlSerializableClassroomList;
import seedu.address.storage.serializable.XmlSerializableCourseList;
import seedu.address.storage.serializable.XmlSerializableGradebookList;
import seedu.address.storage.serializable.XmlSerializableModuleList;
import seedu.address.storage.serializable.XmlSerializableNoteList;
import seedu.address.storage.serializable.XmlSerializableStudentModuleList;
import seedu.address.storage.serializable.XmlSerializableUserList;

/**
 * This class is a storage controller for the other datasets that work alongside the main student list.
 */
public class StorageController {
    private static final String BASE_DIRECTORY = "data/";
    private static final String TEST_DIRECTORY = "testStorage/";
    private static String workingDirectory = TEST_DIRECTORY;

    private static final String STORAGE_COURSES = workingDirectory + "courseList.xml";
    private static final String STORAGE_MODULES = workingDirectory + "modules.xml";
    private static final String STORAGE_CLASSES = workingDirectory + "classes.xml";
    private static final String STORAGE_GRADEBOOK = workingDirectory + "gradebook.xml";
    private static final String STORAGE_NOTES = workingDirectory + "notes.xml";
    private static final String STORAGE_USERS = workingDirectory + "users.xml";

    private static final String STORAGE_STUDENT_MODULE = workingDirectory + "studentModule.xml";
    private static final String STORAGE_CLASS_ATTENDANCE = workingDirectory + "classAttendance.xml";

    private static ArrayList<XmlAdaptedCourse> courseStorage = new ArrayList<>();
    private static ArrayList<XmlAdaptedModule> moduleStorage = new ArrayList<>();
    private static ArrayList<XmlAdaptedClassroom> classesStorage = new ArrayList<>();
    private static ArrayList<XmlAdaptedGradebook> gradebookStorage = new ArrayList<>();
    private static ArrayList<XmlAdaptedNote> noteStorage = new ArrayList<>();
    private static ArrayList<XmlAdaptedUser> userStorage = new ArrayList<>();

    private static ArrayList<XmlAdaptedStudentModule> studentModuleStorage = new ArrayList<>();
    private static ArrayList<XmlAdaptedClassroomAttendance> classAttendanceStorage = new ArrayList<>();

    /**
     * This method switches the class to use a test directory with fresh empty files.
     */
    public static void enterTestMode() {
        workingDirectory = TEST_DIRECTORY;
        wipeAllTestData();
        createFiles();
    }
    /**
     * This method wipes all local test data files.
     */
    public static void wipeAllTestData() {
        for (File file: new File(TEST_DIRECTORY).listFiles()) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }

    }
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

            XmlSerializableUserList ul = XmlUtil
                    .getDataFromFile(Paths.get(STORAGE_USERS), XmlSerializableUserList.class);
            userStorage = ul.getList();

            XmlSerializableStudentModuleList studentModuleList =
                    XmlUtil.getDataFromFile(Paths.get(STORAGE_STUDENT_MODULE), XmlSerializableStudentModuleList.class);
            studentModuleStorage = studentModuleList.getStudentModuleList();

            XmlSerializableClassroomAttendanceList classroomAttendanceList =
                    XmlUtil.getDataFromFile(Paths.get(STORAGE_CLASS_ATTENDANCE),
                            XmlSerializableClassroomAttendanceList.class);
            classAttendanceStorage = classroomAttendanceList.getClassroomAttendanceList();

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
        File users = new File(STORAGE_USERS);
        File studentModule = new File(STORAGE_STUDENT_MODULE);
        File classAttendance = new File(STORAGE_CLASS_ATTENDANCE);
        try {
            classes.createNewFile();
            courses.createNewFile();
            modules.createNewFile();
            notes.createNewFile();
            gradebook.createNewFile();
            users.createNewFile();
            studentModule.createNewFile();
            classAttendance.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method stores all data within the arraylists above to local storage.
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

            XmlSerializableUserList ul = new XmlSerializableUserList();
            ul.setUserList(userStorage);
            XmlUtil.saveDataToFile(Paths.get(STORAGE_USERS), ul);

            XmlSerializableStudentModuleList studentModuleList = new XmlSerializableStudentModuleList();
            studentModuleList.setStudentModuleList(studentModuleStorage);
            XmlUtil.saveDataToFile(Paths.get(STORAGE_STUDENT_MODULE), studentModuleList);

            XmlSerializableClassroomAttendanceList classroomAttendanceList =
                    new XmlSerializableClassroomAttendanceList();
            classroomAttendanceList.setClassroomAttendanceList(classAttendanceStorage);
            XmlUtil.saveDataToFile(Paths.get(STORAGE_CLASS_ATTENDANCE), classroomAttendanceList);
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

    public static ArrayList<XmlAdaptedGradebook> getGradebookStorage() {
        return gradebookStorage;
    }

    public static void setGradebookStorage(ArrayList<XmlAdaptedGradebook> gradebookStorage) {
        StorageController.gradebookStorage = gradebookStorage;
    }

    public static ArrayList<XmlAdaptedNote> getNoteStorage() {
        return noteStorage;
    }

    public static void setNoteStorage(ArrayList<XmlAdaptedNote> noteList) {
        noteStorage = noteList;
    }

    public static ArrayList<XmlAdaptedUser> getUserStorage() {
        return userStorage;
    }

    public static void setUserStorage(ArrayList<XmlAdaptedUser> userStorage) {
        StorageController.userStorage = userStorage;
    }

    public static ArrayList<XmlAdaptedStudentModule> getStudentModuleStorage() {
        return studentModuleStorage;
    }

    public static void setStudentModuleStorage(ArrayList<XmlAdaptedStudentModule> studentModuleStorage) {
        StorageController.studentModuleStorage = studentModuleStorage;
    }

    public static ArrayList<XmlAdaptedClassroomAttendance> getClassAttendanceStorage() {
        return classAttendanceStorage;
    }

    public static void setClassAttendanceStorage(ArrayList<XmlAdaptedClassroomAttendance> classAttendanceStorage) {
        StorageController.classAttendanceStorage = classAttendanceStorage;
    }
}
