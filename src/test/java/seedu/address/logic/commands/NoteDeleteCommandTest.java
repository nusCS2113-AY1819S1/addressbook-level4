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
import seedu.address.ui.BrowserPanel;

/**
 * Contains tests for NoteDeleteCommand.
 */
public class NoteDeleteCommandTest {

    private static NoteManager noteManager = NoteManager.getInstance();

    private static NoteBuilder dummyNote = new NoteBuilder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        noteManager.clearNotes();
        noteManager.saveNoteList();
        BrowserPanel.setNotePageIsLoaded(true);
    }

    @Test
    public void execute_notePageNotLoadedInWebView_displaysMessageNotePageNotLoaded() throws CommandException {
        String expectedMessage = NoteDeleteCommand.MESSAGE_NOTE_PAGE_NOT_LOADED;

        // assert that note page is currently not loaded
        BrowserPanel.setNotePageIsLoaded(false);

        noteManager.addNote(dummyNote.build());
        noteManager.saveNoteList(); // contains one note in list

        int index = 1; // arraylist size: 1, accessed index = 0 (zero-based) -> within bounds
        NoteDeleteCommand noteDeleteCommand = new NoteDeleteCommand(index);

        CommandResult result = noteDeleteCommand.execute(new ModelManager(), new CommandHistory());
        assertEquals(expectedMessage, result.feedbackToUser);
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
        String expectedMessage = NoteDeleteCommand.MESSAGE_SUCCESS;

        noteManager.addNote(dummyNote.build());
        noteManager.addNote(dummyNote.build());
        noteManager.addNote(dummyNote.build());
        noteManager.setFilteredNotesNoFilter();
        noteManager.saveNoteList();

        int index = 3; // arraylist size: 3, accessed index = 2 (zero-based) -> OK
        NoteDeleteCommand noteDeleteCommand = new NoteDeleteCommand(index);
        CommandResult result = noteDeleteCommand.execute(new ModelManager(), new CommandHistory());

        assertEquals(expectedMessage, result.feedbackToUser);

        int expectedSize = 2;
        assertEquals(expectedSize, noteManager.getNotes().size());
    }

    @AfterClass
    public static void tearDown() {
        noteManager.clearNotes();
        noteManager.saveNoteList();
    }
}
