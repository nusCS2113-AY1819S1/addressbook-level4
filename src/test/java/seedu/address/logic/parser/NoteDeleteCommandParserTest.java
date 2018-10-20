package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.NoteDeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelManager;
import seedu.address.model.note.NoteManager;
import seedu.address.testutil.NoteBuilder;

/**
 * Contains tests for NoteDeleteCommandParser.
 */
public class NoteDeleteCommandParserTest {

    private static NoteManager noteManager = new NoteManager();

    private NoteDeleteCommandParser parser = new NoteDeleteCommandParser();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        noteManager.clearNotes();
        noteManager.saveNoteList();
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws ParseException {
        String expectedMessage = String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, NoteDeleteCommand.MESSAGE_USAGE);

        // invalid args
        String args = " 15 this is an 2invalid input";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);

        parser.parse(args);
    }

    @Test
    public void parse_argsIsNumeric_success() throws ParseException, CommandException {
        noteManager.addNote(new NoteBuilder().build());
        noteManager.saveNoteList();
        String expectedMessage = NoteDeleteCommand.MESSAGE_SUCCESS;

        // valid args
        String args = "  1  ";

        NoteDeleteCommand noteDeleteCommand = parser.parse(args);
        CommandResult result = noteDeleteCommand.execute(new ModelManager(), new CommandHistory());

        assertNotNull(result);
        assertEquals(expectedMessage, NoteDeleteCommand.MESSAGE_SUCCESS);
    }

    @AfterClass
    public static void tearDown() {
        noteManager.clearNotes();
        noteManager.saveNoteList();
    }
}
