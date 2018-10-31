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
import seedu.address.storage.adapter.XmlAdaptedGrades;
import seedu.address.storage.adapter.XmlAdaptedModule;
import seedu.address.storage.adapter.XmlAdaptedNote;
import seedu.address.storage.adapter.XmlAdaptedStudentModule;
import seedu.address.storage.adapter.XmlAdaptedUser;
import seedu.address.storage.serializable.XmlSerializableClassroomAttendanceList;
import seedu.address.storage.serializable.XmlSerializableClassroomList;
import seedu.address.storage.serializable.XmlSerializableCourseList;
import seedu.address.storage.serializable.XmlSerializableGradeList;
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
    private static String workingDirectory = BASE_DIRECTORY;

    private static String storageCourses = workingDirectory + "courseList.xml";
    private static String storageModules = workingDirectory + "modules.xml";
    private static String storageClasses = workingDirectory + "classes.xml";
    private static String storageGradebook = workingDirectory + "gradebook.xml";
    private static String storageNotes = workingDirectory + "notes.xml";
    private static String storageUsers = workingDirectory + "users.xml";

    private static String storageStudentModule = workingDirectory + "studentModule.xml";
    private static String storageClassAttendance = workingDirectory + "classAttendance.xml";
    private static String storageGrades = workingDirectory + "grades.xml";

    private static ArrayList<XmlAdaptedCourse> courseStorage = new ArrayList<>();
    private static ArrayList<XmlAdaptedModule> moduleStorage = new ArrayList<>();
    private static ArrayList<XmlAdaptedClassroom> classesStorage = new ArrayList<>();
    private static ArrayList<XmlAdaptedGradebook> gradebookStorage = new ArrayList<>();
    private static ArrayList<XmlAdaptedNote> noteStorage = new ArrayList<>();
    private static ArrayList<XmlAdaptedUser> userStorage = new ArrayList<>();
    private static ArrayList<XmlAdaptedGrades> gradesStorage = new ArrayList<>();

    private static ArrayList<XmlAdaptedStudentModule> studentModuleStorage = new ArrayList<>();
    private static ArrayList<XmlAdaptedClassroomAttendance> classAttendanceStorage = new ArrayList<>();

    /**
     * This method switches the class to use a test directory with fresh empty files.
     */
    public static void enterTestMode() {
        workingDirectory = TEST_DIRECTORY;

        storageCourses = workingDirectory + "courseList.xml";
        storageModules = workingDirectory + "modules.xml";
        storageClasses = workingDirectory + "classes.xml";
        storageGradebook = workingDirectory + "gradebook.xml";
        storageNotes = workingDirectory + "notes.xml";
        storageUsers = workingDirectory + "users.xml";

        storageStudentModule = workingDirectory + "studentModule.xml";
        storageClassAttendance = workingDirectory + "classAttendance.xml";
        storageGrades = workingDirectory + "grades.xml";

        System.out.println(workingDirectory);
        System.out.println(storageCourses);
        createTestFolder();
        wipeAllTestData();
        createFiles();
    }

    public static void createTestFolder() {
        new File(TEST_DIRECTORY).mkdirs();
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
                    .getDataFromFile(Paths.get(storageCourses), XmlSerializableCourseList.class);
            courseStorage = cl.getList();

            XmlSerializableModuleList moduleList =
                    XmlUtil.getDataFromFile(Paths.get(storageModules), XmlSerializableModuleList.class);
            moduleStorage = moduleList.getModules();

            XmlSerializableClassroomList classroomList =
                    XmlUtil.getDataFromFile(Paths.get(storageClasses), XmlSerializableClassroomList.class);
            classesStorage = classroomList.getClassroomList();

            XmlSerializableNoteList noteList =
                    XmlUtil.getDataFromFile(Paths.get(storageNotes), XmlSerializableNoteList.class);
            noteStorage = noteList.getNotes();

            XmlSerializableGradebookList gradebookSerializable = XmlUtil.getDataFromFile(Paths.get(storageGradebook),
                    XmlSerializableGradebookList.class);
            gradebookStorage = gradebookSerializable.getGradebookList();

            XmlSerializableUserList ul = XmlUtil
                    .getDataFromFile(Paths.get(storageUsers), XmlSerializableUserList.class);
            userStorage = ul.getList();

            XmlSerializableStudentModuleList studentModuleList =
                    XmlUtil.getDataFromFile(Paths.get(storageStudentModule), XmlSerializableStudentModuleList.class);
            studentModuleStorage = studentModuleList.getStudentModuleList();

            XmlSerializableClassroomAttendanceList classroomAttendanceList =
                    XmlUtil.getDataFromFile(Paths.get(storageClassAttendance),
                            XmlSerializableClassroomAttendanceList.class);
            classAttendanceStorage = classroomAttendanceList.getClassroomAttendanceList();

            XmlSerializableGradeList gradeSerializable = XmlUtil.getDataFromFile(Paths.get(storageGrades),
                    XmlSerializableGradeList.class);
            gradesStorage = gradeSerializable.getGradeList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method creates files for all datasets if they do not exist on the local filesystem.
     */
    private static void createFiles() {
        new File(BASE_DIRECTORY).mkdirs();
        File classes = new File(storageClasses);
        File courses = new File(storageCourses);
        File modules = new File(storageModules);
        File notes = new File(storageNotes);
        File gradebook = new File(storageGradebook);
        File users = new File(storageUsers);
        File studentModule = new File(storageStudentModule);
        File classAttendance = new File(storageClassAttendance);
        File grades = new File(storageGrades);

        try {
            classes.createNewFile();
            courses.createNewFile();
            modules.createNewFile();
            notes.createNewFile();
            gradebook.createNewFile();
            users.createNewFile();
            studentModule.createNewFile();
            classAttendance.createNewFile();
            grades.createNewFile();
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
            XmlUtil.saveDataToFile(Paths.get(storageCourses), cl);

            XmlSerializableModuleList moduleList = new XmlSerializableModuleList();
            moduleList.setModules(moduleStorage);
            XmlUtil.saveDataToFile(Paths.get(storageModules), moduleList);

            XmlSerializableClassroomList classroomList = new XmlSerializableClassroomList();
            classroomList.setClassroomList(classesStorage);
            XmlUtil.saveDataToFile(Paths.get(storageClasses), classroomList);

            XmlSerializableNoteList noteList = new XmlSerializableNoteList();
            noteList.setNotes(noteStorage);
            XmlUtil.saveDataToFile(Paths.get(storageNotes), noteList);

            XmlSerializableGradebookList gradebookList = new XmlSerializableGradebookList();
            gradebookList.setGradebookList(gradebookStorage);
            XmlUtil.saveDataToFile(Paths.get(storageGradebook), gradebookList);

            XmlSerializableUserList ul = new XmlSerializableUserList();
            ul.setUserList(userStorage);
            XmlUtil.saveDataToFile(Paths.get(storageUsers), ul);

            XmlSerializableStudentModuleList studentModuleList = new XmlSerializableStudentModuleList();
            studentModuleList.setStudentModuleList(studentModuleStorage);
            XmlUtil.saveDataToFile(Paths.get(storageStudentModule), studentModuleList);

            XmlSerializableClassroomAttendanceList classroomAttendanceList =
                    new XmlSerializableClassroomAttendanceList();
            classroomAttendanceList.setClassroomAttendanceList(classAttendanceStorage);
            XmlUtil.saveDataToFile(Paths.get(storageClassAttendance), classroomAttendanceList);

            XmlSerializableGradeList gradeList = new XmlSerializableGradeList();
            gradeList.setGradeList(gradesStorage);
            XmlUtil.saveDataToFile(Paths.get(storageGrades), gradeList);

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

    public static ArrayList<XmlAdaptedGrades> getGradeStorage() {
        return gradesStorage;
    }

    public static void setGradeStorage(ArrayList<XmlAdaptedGrades> gradesStorage) {
        StorageController.gradesStorage = gradesStorage;

    }
}
