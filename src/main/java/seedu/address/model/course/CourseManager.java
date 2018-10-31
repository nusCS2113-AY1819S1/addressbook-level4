package seedu.address.model.course;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import seedu.address.model.StorageController;
import seedu.address.model.student.StudentManager;
import seedu.address.storage.adapter.XmlAdaptedCourse;
import seedu.address.ui.HtmlTableProcessor;

/**
 * This class represents the model-level layer for course management.
 */
public class CourseManager {

    private static CourseManager initCM = new CourseManager();
    private ArrayList<Course> courseList = new ArrayList<Course>();

    private CourseManager() {
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

    public void deleteCourse (Course course) {
        courseList.remove(course);
    }
    public void addCourse(Course course) {
        courseList.add(course);
    }

    public static CourseManager getInstance() {
        if (initCM == null) {
            initCM = new CourseManager();
        }
        return initCM;
    }

    /**
     * Checks if a course code already exists in Trajectory.
     * @param courseCode
     * @return
     */
    public boolean hasCourse(String courseCode) {
        for (Course c: courseList) {
            if (c.getCourseCode().toString().equals(courseCode)) {
                return true;
            }
        }
        return false;
    }


    public String getTableRepresentation() {
        StringBuilder sb = new StringBuilder();

        sb.append(HtmlTableProcessor.getH3Representation("Course List"));
        sb.append(HtmlTableProcessor.renderTableStart(new ArrayList<String>(
                Arrays.asList("Course Name", "Course Code", "School/Faculty"))));

        sb.append(HtmlTableProcessor.getTableItemStart());
        for (Course c : courseList) {
            sb.append(HtmlTableProcessor
                    .renderTableItem(new ArrayList<String>(Arrays
                            .asList(c.getCourseName().toString(),
                                    c.getCourseCode().toString(), c.getFacultyName().toString()))));
        }
        sb.append(HtmlTableProcessor.getTableItemEnd());
        return sb.toString();
    }
    public Course getCourse(String courseCode) {
        for (Course c: courseList) {
            if (courseCode.equalsIgnoreCase(c.getCourseCode().toString())) {
                return c;
            }
        }

        throw new NullPointerException();

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

    public void setCourses(ArrayList<Course> courses) {
        this.courseList = courses;
    }

    public String getOrderedByCourseRepresentation() {

        StringBuilder sb = new StringBuilder();
        sb.append(HtmlTableProcessor.getH2Representation("List of Students ordered by Courses"));
        for (Course c : courseList) {
            sb.append(StudentManager.getInstance().getStudentsInCourseRepresentation(c.getCourseCode().toString()));
        }
        return sb.toString();

    }
}
