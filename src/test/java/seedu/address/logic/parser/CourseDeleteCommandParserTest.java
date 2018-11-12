package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COURSE_CODE_DESC_CS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.CourseDeleteCommand;
import seedu.address.model.StorageController;
import seedu.address.model.course.Course;
import seedu.address.testutil.CourseBuilder;



/**
 * This is a test class for CourseDeleteCommandParser.
 */
public class CourseDeleteCommandParserTest {

    private CourseDeleteCommandParser parser = new CourseDeleteCommandParser();

    @Before
    public void setUp() {
        StorageController.enterTestMode();
    }

    @Test
    public void parse_allFieldsPresent_success() {
        CourseBuilder courseBuilder = new CourseBuilder();
        Course course = courseBuilder.build();
        assertParseSuccess(parser,
                COURSE_CODE_DESC_CS , new CourseDeleteCommand(course.getCourseCode().toString()));

    }
    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CourseDeleteCommand.MESSAGE_USAGE);
        assertParseFailure(parser, CourseDeleteCommand.COMMAND_WORD, expectedMessage);

    }
}
