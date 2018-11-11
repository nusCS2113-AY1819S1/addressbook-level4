package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_KEY_WORD;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.NoteFindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.StorageController;
import seedu.address.model.note.NoteManager;

/**
 * Contains tests for NoteFindCommandParser.
 */
public class NoteFindCommandParserTest {

    private static NoteManager noteManager;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private NoteFindCommandParser parser = new NoteFindCommandParser();

    @Before
    public void setUp() {
        StorageController.enterTestMode();
        NoteManager.initNoteManager();
        noteManager = NoteManager.getInstance();
        noteManager.clearNotes();
        noteManager.saveNoteList();
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws ParseException {
        String expectedMessageInvalidCommand = String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, NoteFindCommand.MESSAGE_USAGE);

        String expectedMessageInvalidKeyword = NoteFindCommand.MESSAGE_INVALID_KEYWORD;

        String args;

        try {
            // invalid args, empty
            args = "";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertEquals(expectedMessageInvalidCommand, e.getMessage());
        }

        try {
            // invalid args with prefix but blank param
            args = " " + PREFIX_NOTE_KEY_WORD;
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertEquals(expectedMessageInvalidKeyword, e.getMessage());
        }

        try {
            // invalid args with prefix but contain multiple words separated by space
            args = " " + PREFIX_NOTE_KEY_WORD + "hello world";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertEquals(expectedMessageInvalidKeyword, e.getMessage());
        }
    }

    @Test
    public void parse_validArgs_success() throws ParseException {
        NoteFindCommand noteFindCommand = null;

        // valid args with two non-empty keywords and no spaces in between
        String args = " " + PREFIX_NOTE_KEY_WORD + "hello " + PREFIX_NOTE_KEY_WORD + "world";

        noteFindCommand = parser.parse(args);
        assertNotNull(noteFindCommand);
    }

    @AfterClass
    public static void tearDown() {
        noteManager.clearNotes();
        noteManager.saveNoteList();
    }
}
