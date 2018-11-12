package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COURSE_CODE_DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.COURSE_FACULTY_DESC_SOC;
import static seedu.address.logic.commands.CommandTestUtil.COURSE_NAME_DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_CODE_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_FACULTY_SOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_NAME_CS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.CourseEditCommand;
import seedu.address.model.course.CourseCode;
import seedu.address.model.course.CourseName;
import seedu.address.model.course.FacultyName;
import seedu.address.testutil.EditCourseDescriptorBuilder;

/**
 * This is a test class for CourseEditCommandParser.
 */
public class CourseEditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, CourseEditCommand.MESSAGE_USAGE);

    private CourseEditCommandParser parser = new CourseEditCommandParser();


    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "course edit c/CEG n/", CourseName.MESSAGE_COURSE_NAME_CONSTRAINTS);
        assertParseFailure(parser, "course edit c/CEG f/", FacultyName.MESSAGE_COURSE_FACULTY_NAME_CONSTRAINTS);
        assertParseFailure(parser, "course edit c/CEG n/ f/", CourseName.MESSAGE_COURSE_NAME_CONSTRAINTS);
    }
    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = COURSE_NAME_DESC_CS + COURSE_CODE_DESC_CS + COURSE_FACULTY_DESC_SOC;

        CourseEditCommand.EditCourseDescriptor descriptor = new EditCourseDescriptorBuilder()
                .withCourseCode(VALID_COURSE_CODE_CS).withCourseName(VALID_COURSE_NAME_CS)
                .withFacultyName(VALID_COURSE_FACULTY_SOC).build();
        CourseEditCommand expectedCommand = new CourseEditCommand(new CourseCode(VALID_COURSE_CODE_CS), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = COURSE_NAME_DESC_CS + COURSE_CODE_DESC_CS;

        CourseEditCommand.EditCourseDescriptor descriptor = new EditCourseDescriptorBuilder()
                .withCourseCode(VALID_COURSE_CODE_CS).withCourseName(VALID_COURSE_NAME_CS).build();
        CourseEditCommand expectedCommand = new CourseEditCommand(new CourseCode(VALID_COURSE_CODE_CS), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
