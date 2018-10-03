package seedu.address.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This represents a course in Trajectory.
 */
@XmlRootElement(name = "course")

public class Course {

    private String courseCode;
    private String courseName;
    private String facultyName;

    public Course(){

    }
    public Course(String courseCode, String courseName, String originFaculty) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.facultyName = originFaculty;
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
}
