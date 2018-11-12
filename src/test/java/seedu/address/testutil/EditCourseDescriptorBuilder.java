package seedu.address.testutil;

import seedu.address.logic.commands.CourseEditCommand.EditCourseDescriptor;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseCode;
import seedu.address.model.course.CourseName;
import seedu.address.model.course.FacultyName;



/**
 * A utility class to help with building EditCourseDescriptor objects.
 */
public class EditCourseDescriptorBuilder {

    private EditCourseDescriptor descriptor;

    public EditCourseDescriptorBuilder() {
        descriptor = new EditCourseDescriptor();
    }

    public EditCourseDescriptorBuilder(EditCourseDescriptor descriptor) {
        this.descriptor = new EditCourseDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCourseDescriptor} with fields containing {@code course}'s details
     */
    public EditCourseDescriptorBuilder(Course course) {
        descriptor = new EditCourseDescriptor();
        descriptor.setFacultyName(course.getFacultyName());
        descriptor.setCourseName(course.getCourseName());
        descriptor.setCourseCode(course.getCourseCode());
    }

    /**
     * Sets the {@code courseCode} of the {@code EditCourseDescriptor} that we are building.
     */
    public EditCourseDescriptorBuilder withCourseCode(String courseCode) {
        descriptor.setCourseCode(new CourseCode(courseCode));
        return this;
    }

    /**
     * Sets the {@code courseName} of the {@code EditCourseDescriptor} that we are building.
     */
    public EditCourseDescriptorBuilder withCourseName(String courseName) {
        descriptor.setCourseName(new CourseName(courseName));
        return this;
    }

    /**
     * Sets the {@code facultyName} of the {@code EditCourseDescriptor} that we are building.
     */
    public EditCourseDescriptorBuilder withFacultyName(String facultyName) {
        descriptor.setFacultyName(new FacultyName(facultyName));
        return this;
    }


    public EditCourseDescriptor build() {
        return descriptor;
    }
}
