package seedu.address.model.course;

/**
 * This is a representation of a course.
 */
public class Course {

    private CourseCode courseCode;
    private CourseName courseName;
    private FacultyName facultyName;

    public Course(CourseCode courseCode, CourseName courseName, FacultyName facultyName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.facultyName = facultyName;
    }

    public CourseCode getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(CourseCode courseCode) {
        this.courseCode = courseCode;
    }

    public CourseName getCourseName() {
        return courseName;
    }

    public void setCourseName(CourseName courseName) {
        this.courseName = courseName;
    }

    public FacultyName getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(FacultyName facultyName) {
        this.facultyName = facultyName;
    }
}
