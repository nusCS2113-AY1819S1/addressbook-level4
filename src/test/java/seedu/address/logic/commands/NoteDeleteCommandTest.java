package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.note.NoteManager;
import seedu.address.testutil.NoteBuilder;

/**
 * Contains tests for NoteDeleteCommand.
 */
public class NoteDeleteCommandTest {

    private static NoteManager noteManager = new NoteManager();

    private static NoteBuilder dummyNote = new NoteBuilder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        noteManager.clearNotes();
        noteManager.saveNoteList();
    }

    @Test
    public void execute_indexOutOfBounds_throwsCommandException() throws CommandException {
        noteManager.addNote(dummyNote.build());
        noteManager.addNote(dummyNote.build());
        noteManager.saveNoteList();

        int index = 5; // arraylist size: 2, accessed index = 4 (zero-based) -> out of bounds
        NoteDeleteCommand noteDeleteCommand = new NoteDeleteCommand(index);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(NoteDeleteCommand.MESSAGE_INVALID_INDEX, index));
        noteDeleteCommand.execute(new ModelManager(), new CommandHistory());
    }

    @Test
    public void execute_validIndex_success() throws CommandException {
        noteManager.addNote(dummyNote.build());
        noteManager.addNote(dummyNote.build());
        noteManager.addNote(dummyNote.build());
        noteManager.saveNoteList();

        int index = 3; // arraylist size: 3, accessed index = 2 (zero-based) -> OK
        NoteDeleteCommand noteDeleteCommand = new NoteDeleteCommand(index);
        CommandResult result = noteDeleteCommand.execute(new ModelManager(), new CommandHistory());

        String expectedMessage = NoteDeleteCommand.MESSAGE_SUCCESS;
        assertEquals(expectedMessage, result.feedbackToUser);

        int expectedSize = 2;
        noteManager = new NoteManager();
        assertEquals(expectedSize, noteManager.getNotes().size());
    }

    @AfterClass
    public static void tearDown() {
        noteManager.clearNotes();
        noteManager.saveNoteList();
    }
}
