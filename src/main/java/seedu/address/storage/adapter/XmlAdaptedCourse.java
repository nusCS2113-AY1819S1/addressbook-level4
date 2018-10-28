package seedu.address.storage.adapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.model.course.Course;
import seedu.address.model.course.CourseCode;
import seedu.address.model.course.CourseName;
import seedu.address.model.course.FacultyName;

/**
 * This represents a course in Trajectory.
 */
@XmlRootElement(name = "course")

public class XmlAdaptedCourse {

    private String courseCode;
    private String courseName;
    private String facultyName;

    public XmlAdaptedCourse(){

    }
    public XmlAdaptedCourse(String courseCode, String courseName, String originFaculty) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.facultyName = originFaculty;
    }

    public XmlAdaptedCourse(Course course) {
        this.courseCode = course.getCourseCode().toString();
        this.courseName = course.getCourseName().toString();
        this.facultyName = course.getFacultyName().toString();
    }

    @XmlElement(name = "courseCode")
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    @XmlElement(name = "courseName")
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @XmlElement(name = "facultyName")
    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public Course toModelType() {
        return new Course(new CourseCode(courseCode), new CourseName(courseName), new FacultyName(facultyName));
    }
}
