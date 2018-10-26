package seedu.address.logic.parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.commands.GradebookEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static org.junit.Assert.assertNotNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.GradebookEditCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.*;
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
    public void parse_validArgsWithNewComponentName_success() throws ParseException {
        //valid arguments with optional new gradebook component name
        String moduleCode = "CS2113";
        String gradebookComponentName = "Finals";
        String newGradebookComponentName = "Test";
        String args = " "
                + PREFIX_MODULE_CODE
                + moduleCode
                + " "
                + PREFIX_GRADEBOOK_ITEM
                + gradebookComponentName
                + " "
                + PREFIX_GRADEBOOK_ITEM_EDIT
                + newGradebookComponentName;
        GradebookEditCommand gradebookEditCommand = parser.parse(args);
        assertNotNull(gradebookEditCommand);
    }

    @Test
    public void parse_validArgsWithNewMaxMarks_success() throws ParseException {
        //valid arguments with optional new max marks
        String moduleCode = "CS2113";
        String gradebookComponentName = "Finals";
        int newMaxMarks = 50;
        String args = " "
                + PREFIX_MODULE_CODE
                + moduleCode
                + " "
                + PREFIX_GRADEBOOK_ITEM
                + gradebookComponentName
                + " "
                + PREFIX_GRADEBOOK_MAXMARKS
                + newMaxMarks;
        GradebookEditCommand gradebookEditCommand = parser.parse(args);
        assertNotNull(gradebookEditCommand);
    }

    @Test
    public void parse_validArgsWithNewWeightage_success() throws ParseException {
        //valid arguments with optional new weightage
        String moduleCode = "CS2113";
        String gradebookComponentName = "Finals";
        int newWeightage = 20;
        String args = " "
                + PREFIX_MODULE_CODE
                + moduleCode
                + " "
                + PREFIX_GRADEBOOK_ITEM
                + gradebookComponentName
                + " "
                + PREFIX_GRADEBOOK_WEIGHTAGE
                + newWeightage;
        GradebookEditCommand gradebookEditCommand = parser.parse(args);
        assertNotNull(gradebookEditCommand);
    }

    @Test
    public void parse_validArgsDifferentPositions_success() throws ParseException {
        //valid arguments
        String moduleCode = "CS2113";
        String gradebookComponentName = "Finals";
        int newMaxMarks = 90;
        String args = " "
                + PREFIX_MODULE_CODE
                + moduleCode
                + " "
                + PREFIX_GRADEBOOK_MAXMARKS
                + newMaxMarks
                + " "
                + PREFIX_GRADEBOOK_ITEM
                + gradebookComponentName;

        GradebookEditCommand gradebookEditCommand = parser.parse(args);
        assertNotNull(gradebookEditCommand);
    }

    @Test
    public void parse_emptyModuleArgs_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ERROR_EMPTY);
        //component name empty
        String moduleCode = "CS2113";
        String gradebookComponentName = "";
        String argWithoutModule = " "
                + PREFIX_MODULE_CODE
                + moduleCode
                + " "
                + PREFIX_GRADEBOOK_ITEM
                + gradebookComponentName;
        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(argWithoutModule);
    }


    @Test
    public void parse_emptyComponentNameArgs_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ERROR_EMPTY);
        //component name empty
        String moduleCode = "";
        String gradebookComponentName = "Finals";
        String argWithoutComponentName = " "
                + PREFIX_MODULE_CODE
                + moduleCode
                + " "
                + PREFIX_GRADEBOOK_ITEM
                + gradebookComponentName;
        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(argWithoutComponentName);
    }

    @Test
    public void parse_invalidMaxMarksArgs_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_MAX_MARKS_INVALID);
        String moduleCode = "CS2113";
        String gradebookComponentName = "Finals";
        int newMaxMarks = 101;
        String argWithInvalidMaxMarks = " "
                + PREFIX_MODULE_CODE
                + moduleCode
                + " "
                + PREFIX_GRADEBOOK_ITEM
                + gradebookComponentName
                + " "
                + PREFIX_GRADEBOOK_MAXMARKS
                + newMaxMarks;
        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(argWithInvalidMaxMarks);
    }

    @Test
    public void parse_invalidWeightageArgs_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_WEIGHTAGE_INVALID);
        String moduleCode = "CS2113";
        String gradebookComponentName = "Finals";
        int newWeightage = 111;
        String argWithInvalidWeightage = " "
                + PREFIX_MODULE_CODE
                + moduleCode
                + " "
                + PREFIX_GRADEBOOK_ITEM
                + gradebookComponentName
                + " "
                + PREFIX_GRADEBOOK_WEIGHTAGE
                + newWeightage;
        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(argWithInvalidWeightage);
    }

    @Test
    public void parse_invalidMaxMarksTypeArgs_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_MAX_MARKS_ERROR);
        String moduleCode = "CS2113";
        String gradebookComponentName = "Finals";
        String newMaxMarks = "11a";
        String argWithInvalidMaxMarksType = " "
                + PREFIX_MODULE_CODE
                + moduleCode
                + " "
                + PREFIX_GRADEBOOK_ITEM
                + gradebookComponentName
                + " "
                + PREFIX_GRADEBOOK_MAXMARKS
                + newMaxMarks;
        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(argWithInvalidMaxMarksType);
    }

    @Test
    public void parse_invalidWeightageTypeArgs_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_WEIGHTAGE_ERROR);
        String moduleCode = "CS2113";
        String gradebookComponentName = "Finals";
        String newWeightage = "11b";
        String argWithInvalidWeightageType = " "
                + PREFIX_MODULE_CODE
                + moduleCode
                + " "
                + PREFIX_GRADEBOOK_ITEM
                + gradebookComponentName
                + " "
                + PREFIX_GRADEBOOK_WEIGHTAGE
                + newWeightage;
        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(argWithInvalidWeightageType);
    }
}
