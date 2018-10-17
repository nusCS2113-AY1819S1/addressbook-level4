package seedu.address.model.course;

import java.util.ArrayList;
import java.util.stream.Collectors;

import seedu.address.model.StorageController;
import seedu.address.storage.adapter.XmlAdaptedCourse;

/**
 * This class represents the model-level layer for course management.
 */
public class CourseManager {

    private ArrayList<Course> courseList = new ArrayList<Course>();

    public CourseManager() {
        loadCourses();
    }

    /**
     * Loads all the courses from the storage-layer.
     */
    public void loadCourses() {
        for (XmlAdaptedCourse xmlCourse : StorageController.getCourseStorage()) {
            courseList.add(xmlCourse.toModelType());
        }
    }

    public void addModule(Course course) {
        courseList.add(course);
    }

    /**
     * Saves all courses currently in memory to file.
     */
    public void saveCourseList() {
        ArrayList<XmlAdaptedCourse> xmlAdaptedCourses =
                courseList.stream().map(XmlAdaptedCourse::new).collect(Collectors.toCollection(ArrayList::new));
        StorageController.setCourseStorage(xmlAdaptedCourses);
        StorageController.storeData();
    }


    public ArrayList<Course> getCourses() {
        return courseList;
    }

    public void setModules(ArrayList<Course> courses) {
        this.courseList = courses;
    }
}
