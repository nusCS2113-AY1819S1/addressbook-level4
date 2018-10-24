package seedu.address.logic.parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.commands.GradebookDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.GradebookDeleteCommandParser.MESSAGE_EMPTY_INPUTS;

public class GradebookDeleteCommandParserTest {
    private GradebookDeleteCommandParser parser = new GradebookDeleteCommandParser();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parse_invalidFormat_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GradebookDeleteCommand.MESSAGE_USAGE);

        //invalid arguments found
        String arg = "this is an invalid format";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(arg);
    }

    @Test
    public void parse_validArgs_success() throws ParseException {
        //valid arguments without optional arguments
        String args = " mc/CS2113 i/Finals";
        GradebookDeleteCommand gradebookDeleteCommand = parser.parse(args);
        assertNotNull(gradebookDeleteCommand);
    }

    @Test
    public void parse_emptyModuleCodeArgs_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_EMPTY_INPUTS);
        //module code empty
        String args = " mc/ i/Finals";
        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(args);
    }

    @Test
    public void parse_emptyComponentNameArgs_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_EMPTY_INPUTS);
        //component name empty
        String arg = " mc/CS2113 i/";
        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(arg);
    }
}
