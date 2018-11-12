package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_FACULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseCode;
import seedu.address.model.course.CourseManager;
import seedu.address.model.course.CourseName;
import seedu.address.model.course.FacultyName;



/**
 * Modifies the details of an existing course
 */
public class CourseEditCommand extends Command {

    public static final String COMMAND_WORD = "course edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Modifies course details identified by the course code "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_COURSE_CODE + "COURSE_CODE "
            + "[" + PREFIX_COURSE_NAME + "COURSE_NAME]\n"
            + "[" + PREFIX_COURSE_FACULTY + "FACULTY_NAME]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_COURSE_CODE + "CEG "
            + PREFIX_COURSE_NAME + "Computer Engineering"
            + PREFIX_COURSE_FACULTY + "Faculty of Engineering";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited course: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final CourseCode courseCode;
    private final EditCourseDescriptor editCourseDescriptor;

    public CourseEditCommand(CourseCode courseCode, EditCourseDescriptor editedCourse) {
        requireNonNull(editedCourse);

        this.courseCode = courseCode;
        this.editCourseDescriptor = new EditCourseDescriptor(editedCourse);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        CourseManager cm = CourseManager.getInstance();
        Course newCourse = cm.getCourse(courseCode.toString());

        if (newCourse == null) {
            throw new CommandException("This course code cannot be found");
        }

        CourseName updatedCourseName = editCourseDescriptor.getCourseName().orElse(newCourse.getCourseName());
        FacultyName updatedFacultyName = editCourseDescriptor.getFacultyName().orElse(newCourse.getFacultyName());

        Course updatedCourse = new Course(newCourse.getCourseCode(), updatedCourseName, updatedFacultyName);

        cm.updateCourse(newCourse, updatedCourse);
        cm.saveCourseList();

        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS,
                updatedCourse.getCourseName()), cm.getTableRepresentation());
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditCourseDescriptor {
        private CourseName courseName;
        private CourseCode courseCode;
        private FacultyName facultyName;

        public EditCourseDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditCourseDescriptor(EditCourseDescriptor toCopy) {
            setCourseName(toCopy.courseName);
            setFacultyName(toCopy.facultyName);
            setCourseCode(courseCode);

        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(courseCode, courseName, facultyName);
        }

        public Optional<CourseName> getCourseName() {
            return Optional.ofNullable(courseName);
        }

        public void setCourseName(CourseName courseName) {
            this.courseName = courseName;
        }

        public Optional<FacultyName> getFacultyName() {
            return Optional.ofNullable(facultyName);
        }

        public void setFacultyName(FacultyName facultyName) {
            this.facultyName = facultyName;
        }

        public Optional<CourseCode> getCourseCode() {
            return Optional.ofNullable(courseCode);
        }

        public void setCourseCode(CourseCode courseCode) {
            this.courseCode = courseCode;
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCourseDescriptor)) {
                return false;
            }

            // state check
            EditCourseDescriptor e = (EditCourseDescriptor) other;

            return getCourseCode().equals(e.getCourseCode())
                    && getCourseName().equals(e.getCourseName())
                    && getFacultyName().equals(e.getFacultyName());
        }


    }


}
