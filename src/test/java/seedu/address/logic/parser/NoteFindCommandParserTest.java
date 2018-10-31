package seedu.address.logic.parser;

import static org.junit.Assert.assertNotNull;
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

    private static NoteManager noteManager = NoteManager.getInstance();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private NoteFindCommandParser parser = new NoteFindCommandParser();

    @Before
    public void setUp() {
        StorageController.enterTestMode();
        noteManager.clearNotes();
        noteManager.saveNoteList();
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws ParseException {
        String expectedMessageInvalidCommand = String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, NoteFindCommand.MESSAGE_USAGE);

        String expectedMessageInvalidKeyword = NoteFindCommand.MESSAGE_INVALID_KEYWORD;

        // invalid args, empty
        String args = "";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessageInvalidCommand);
        parser.parse(args);

        // invalid args with prefix but blank param
        args = " " + PREFIX_NOTE_KEY_WORD;

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessageInvalidKeyword);
        parser.parse(args);

        // invalid args with prefix but contains a space in between
        args = " " + PREFIX_NOTE_KEY_WORD + "hello world";
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
