package seedu.address.logic.parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.commands.GradebookEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static org.junit.Assert.assertNotNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.GradebookEditCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.GradebookEditCommandParser.*;

public class GradebookEditCommandParserTest {
    private GradebookEditCommandParser parser = new GradebookEditCommandParser();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parse_invalidFormat_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        //invalid arguments found
        String arg = "this is an invalid format";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(arg);
    }

    @Test
    public void parse_validArgs_success() throws ParseException {
        //valid arguments
        String args = " mc/CS2113 i/Finals m/50 w/50";
        GradebookEditCommand gradebookEditCommand = parser.parse(args);
        assertNotNull(gradebookEditCommand);
    }

    @Test
    public void parse_validArgsDifferentPositions_success() throws ParseException {
        //valid arguments
        String args = " mc/CS2113 m/50 i/Finals w/50";
        GradebookEditCommand gradebookEditCommand = parser.parse(args);
        assertNotNull(gradebookEditCommand);
    }

    @Test
    public void parse_validArgsWithMaxMarks_success() throws ParseException {
        //valid arguments with maximum marks
        String argsWithMaxMarks = " mc/CS2113 i/Finals mm/50";
        GradebookEditCommand gradebookEditCommand = parser.parse(argsWithMaxMarks);
        assertNotNull(gradebookEditCommand);
    }

    @Test
    public void parse_validArgsWithWeightage_success() throws ParseException {
        //valid arguments with maximum marks
        String argsWithWeightage = " mc/CS2113 i/Finals w/50";
        GradebookEditCommand gradebookEditCommand = parser.parse(argsWithWeightage);
        assertNotNull(gradebookEditCommand);
    }

    @Test
    public void parse_emptyModuleArgs_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ERROR_EMPTY);
        //component name empty
        String argWithoutModule = " mc/ i/Test";
        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(argWithoutModule);
    }


    @Test
    public void parse_emptyComponentNameArgs_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ERROR_EMPTY);
        //component name empty
        String argWithoutComponentName = " mc/ST2334 i/";
        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(argWithoutComponentName);
    }

    @Test
    public void parse_invalidMaxMarksArgs_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_MAX_MARKS_INVALID);
        String argWithInvalidMaxMarks = " mc/CG2271 i/Finals mm/101";
        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(argWithInvalidMaxMarks);
    }

    @Test
    public void parse_invalidWeightageArgs_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_WEIGHTAGE_INVALID);
        String argWithInvalidWeightage = " mc/CG2271 i/Finals w/101";
        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(argWithInvalidWeightage);
    }

    @Test
    public void parse_invalidMaxMarksTypeArgs_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_MAX_MARKS_ERROR);
        String argWithInvalidMaxMarksType = " mc/CG2271 i/Finals mm/10a";
        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(argWithInvalidMaxMarksType);
    }

    @Test
    public void parse_invalidWeightageTypeArgs_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_WEIGHTAGE_ERROR);
        String argWithInvalidWeightageType = " mc/CG2271 i/Finals w/10a";
        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(argWithInvalidWeightageType);
    }
}
