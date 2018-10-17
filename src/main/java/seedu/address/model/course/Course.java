package seedu.address.model.course;

/**
 * This is a representation of a course.
 */
public class Course {

    private String courseCode;
    private String courseName;
    private String facultyName;

    public Course(String courseCode, String courseName, String facultyName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.facultyName = facultyName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }
}
