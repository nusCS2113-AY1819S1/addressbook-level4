package seedu.address.testutil;

import seedu.address.model.course.Course;
import seedu.address.model.course.CourseCode;
import seedu.address.model.course.CourseName;
import seedu.address.model.course.FacultyName;

public class CourseBuilder {
    public static final String DEFAULT_COURSE_NAME = "Computer Science";
    public static final String DEFAULT_COURSE_CODE = "CS";
    public static final String DEFAULT_FACULTY_NAME = "School of Computing";

    private CourseName courseName;
    private CourseCode courseCode;
    private FacultyName facultyName;

    public CourseBuilder() {
        courseName = new CourseName(DEFAULT_COURSE_NAME);
        courseCode = new CourseCode(DEFAULT_COURSE_CODE);
        facultyName = new FacultyName(DEFAULT_FACULTY_NAME);
    }

    public Course build() {
        return new Course(courseCode, courseName, facultyName);
    }

    public CourseBuilder withCourseName(String courseName) {
        this.courseName = new CourseName(courseName);
        return this;
    }
}
