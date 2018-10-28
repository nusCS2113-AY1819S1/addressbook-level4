package seedu.address.logic.parser;

import static org.junit.Assert.assertNotNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_START_TIME;

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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private NoteAddCommandParser parser = new NoteAddCommandParser();


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
        // valid arguments with multiple fields
        String args = " " + PREFIX_MODULE_CODE + "CS2113" + PREFIX_NOTE_START_TIME + "1/2/2020";
        NoteAddCommand noteAddCommand = parser.parse(args);

        assertNotNull(noteAddCommand);

        // empty arguments
        args = "";
        noteAddCommand = parser.parse(args);

        assertNotNull(noteAddCommand);
    }
}
