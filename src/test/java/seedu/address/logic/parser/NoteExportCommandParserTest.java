package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_FILE_NAME;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.NoteExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains tests for NoteExportCommandParser.
 */
public class NoteExportCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private NoteExportCommandParser parser = new NoteExportCommandParser();

    @Test
    public void parse_invalidFileName_throwsParseException() throws ParseException {
        String expectedMessage = NoteExportCommand.MESSAGE_INVALID_FILE_NAME;

        String args = " " + PREFIX_NOTE_FILE_NAME + "invalidFileName!";
        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(args);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws ParseException {
        String expectedMessageInvalidFormat = String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, NoteExportCommand.MESSAGE_USAGE);
        String expectedMessageInvalidFileName = String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, NoteExportCommand.MESSAGE_INVALID_FILE_NAME);

        String args;
        try {
            // no paraemeter
            args = "";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertEquals(expectedMessageInvalidFormat, e.getMessage());
        }

        try {
            // with correct prefix but empty field
            args = " " + PREFIX_NOTE_FILE_NAME;
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertEquals(expectedMessageInvalidFileName, e.getMessage());
        }
    }

    @Test
    public void parse_validFileName_success() throws ParseException {
        String args = " " + PREFIX_NOTE_FILE_NAME + "valid_filename";

        NoteExportCommand noteExportCommand = parser.parse(args);
        assertNotNull(noteExportCommand);
    }
}
