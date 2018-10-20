package seedu.address.logic.parser;

import static org.junit.Assert.assertNotNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.NoteAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains tests for NoteAddCommandParser.
 */
public class NoteAddCommandParserTest {

    private NoteAddCommandParser parser = new NoteAddCommandParser();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parse_invalidArgs_throwsParseException() throws ParseException {
        String expectedMessage = String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, NoteAddCommand.MESSAGE_USAGE);

        // invalid arguments
        String args = " this is an invalid input";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);

        parser.parse(args);
    }

    @Test
    public void parse_validArgs_success() throws ParseException {
        String expectedMessage = NoteAddCommand.MESSAGE_SUCCESS;

        // valid arguments with optional arguments
        String args = " m/CS2113 d/1/2/2020";
        NoteAddCommand noteAddCommand = parser.parse(args);

        assertNotNull(noteAddCommand);

        // valid arguments
        args = " m/CS2040C";
        noteAddCommand = parser.parse(args);

        assertNotNull(noteAddCommand);
    }

    @Test
    public void parse_missingMandatoryFields_throwsParseException() throws ParseException {
        String expectedMessage = String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, NoteAddCommand.MESSAGE_USAGE);

        // missing mandatory field argument
        String args = " d/1/2/2020";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);

        parser.parse(args);
    }
}
