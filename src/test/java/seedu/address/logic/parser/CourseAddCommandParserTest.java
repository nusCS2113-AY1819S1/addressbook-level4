package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COURSE_CODE_DESC_CEG;
import static seedu.address.logic.commands.CommandTestUtil.COURSE_CODE_DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.COURSE_FACULTY_DESC_FOE;
import static seedu.address.logic.commands.CommandTestUtil.COURSE_FACULTY_DESC_SOC;
import static seedu.address.logic.commands.CommandTestUtil.COURSE_NAME_DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COURSE_CODE_NUMBERS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.CourseAddCommand;
import seedu.address.model.StorageController;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseCode;
import seedu.address.testutil.CourseBuilder;

/**
 * This is a test file for CourseAddCommandParser.
 */
public class CourseAddCommandParserTest {
    private CourseAddCommandParser parser = new CourseAddCommandParser();

    @Before
    public void setUp() {
        StorageController.enterTestMode();
    }

    @Test
    public void parse_allFieldsPresent_success() {
        CourseBuilder courseBuilder = new CourseBuilder();
        Course course = courseBuilder.build();
        assertParseSuccess(parser,
                 COURSE_CODE_DESC_CS + COURSE_NAME_DESC_CS + COURSE_FACULTY_DESC_SOC, new CourseAddCommand(course));
        assertParseSuccess(parser,
                COURSE_CODE_DESC_CEG + COURSE_CODE_DESC_CS
                        + COURSE_NAME_DESC_CS + COURSE_FACULTY_DESC_SOC, new CourseAddCommand(course));
        assertParseSuccess(parser,
                COURSE_FACULTY_DESC_FOE + COURSE_CODE_DESC_CS
                        + COURSE_NAME_DESC_CS + COURSE_FACULTY_DESC_SOC, new CourseAddCommand(course));
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CourseAddCommand.MESSAGE_USAGE);
        assertParseFailure(parser, CourseAddCommand.COMMAND_WORD, expectedMessage);
        // missing course code
        assertParseFailure(parser, COURSE_NAME_DESC_CS + COURSE_FACULTY_DESC_SOC, expectedMessage);
        // missing faculty name
        assertParseFailure(parser, COURSE_CODE_DESC_CS + COURSE_NAME_DESC_CS, expectedMessage);
        // missing course name
        assertParseFailure(parser, COURSE_FACULTY_DESC_SOC + COURSE_NAME_DESC_CS, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid course code
        assertParseFailure(parser, INVALID_COURSE_CODE_NUMBERS
                + COURSE_NAME_DESC_CS + COURSE_FACULTY_DESC_SOC, CourseCode.MESSAGE_COURSE_CODE_CONSTRAINTS);
    }
}
