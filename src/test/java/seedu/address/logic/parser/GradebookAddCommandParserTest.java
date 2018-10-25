package seedu.address.logic.parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.commands.GradebookAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static org.junit.Assert.assertNotNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.GradebookAddCommandParser.*;

public class GradebookAddCommandParserTest {
    private GradebookAddCommandParser parser = new GradebookAddCommandParser();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parse_invalidFormat_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GradebookAddCommand.MESSAGE_USAGE);

        //invalid arguments found
        String arg = "this is an invalid format";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(arg);
    }

    @Test
    public void parse_validArgsWithOptionalParams_success() throws ParseException {
        //valid arguments without optional arguments
        String args = " mc/CS2113 i/Finals";
        GradebookAddCommand gradebookAddCommand = parser.parse(args);
        assertNotNull(gradebookAddCommand);

        //valid arguments including optional argument, Max Marks
        args = " mc/CS2113 i/Finals mm/20";
        gradebookAddCommand = parser.parse(args);
        assertNotNull(gradebookAddCommand);

        //valid arguments including optional argument, Weightage
        args = " mc/CS2113 i/Finals w/20";
        gradebookAddCommand = parser.parse(args);
        assertNotNull(gradebookAddCommand);

        //valid arguments including optional arguments, Max Marks and Weightage
        args = " mc/CS2113 i/Finals mm/20 w/20";
        gradebookAddCommand = parser.parse(args);
        assertNotNull(gradebookAddCommand);
    }

    @Test
    public void parse_invalidMaxMarksType_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_MAX_MARKS_ERROR);

        //invalid maximum marks type
        String arg = " mc/CS2113 i/Finals mm/20ps";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(arg);
    }

    @Test
    public void parse_invalidWeightageType_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_WEIGHTAGE_ERROR);

        //invalid maximum marks type
        String arg = " mc/CS2113 i/Finals w/50s";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(arg);
    }

    @Test
    public void parse_missingMandatoryParams_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_EMPTY_INPUTS);

        //missing module code arg
        String arg = " mc/ i/ ";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(arg);
    }

    @Test
    public void parse_invalidMaxMarksRange_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_MAX_MARKS_INVALID);

        //invalid maximum marks range
        String arg = " mc/CS2113 i/Finals mm/150";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(arg);
    }
}
