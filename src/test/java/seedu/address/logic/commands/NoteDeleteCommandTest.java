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
import seedu.address.model.StorageController;
import seedu.address.model.note.NoteManager;
import seedu.address.testutil.NoteBuilder;
import seedu.address.ui.BrowserPanel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        StorageController.enterTestMode();
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
        noteManager.addNote(dummyNote.build());
        noteManager.saveNoteList(); // contains two notes in list
        noteManager.setFilteredNotesNoFilter();

        // delete index 0 and 1 (zero-based) in list
        List<Integer> indexList = new ArrayList<>(Arrays.asList(1));

        NoteDeleteCommand noteDeleteCommand = new NoteDeleteCommand(indexList);

        CommandResult result = noteDeleteCommand.execute(new ModelManager(), new CommandHistory());
        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @Test
    public void execute_indexOutOfBounds_throwsCommandException() throws CommandException {
        noteManager.addNote(dummyNote.build());
        noteManager.addNote(dummyNote.build());
        noteManager.saveNoteList();
        noteManager.setFilteredNotesNoFilter();

        // arraylist size: 2, delete at index = 2 (zero-based) -> out of bounds
        List<Integer> indexList = new ArrayList<>(Arrays.asList(3));

        NoteDeleteCommand noteDeleteCommand = new NoteDeleteCommand(indexList);

        thrown.expect(CommandException.class);
        thrown.expectMessage(NoteDeleteCommand.MESSAGE_INVALID_INDEX);
        noteDeleteCommand.execute(new ModelManager(), new CommandHistory());
    }

    @Test
    public void execute_validIndex_success() throws CommandException {
        String expectedMessage = NoteDeleteCommand.MESSAGE_SUCCESS;

        noteManager.addNote(dummyNote.build());
        noteManager.addNote(dummyNote.build());
        noteManager.addNote(dummyNote.build());
        noteManager.saveNoteList();
        noteManager.setFilteredNotesNoFilter();

        // arraylist size: 3, delete at index = 2 (zero-based) -> OK
        List<Integer> indexList = new ArrayList<>(Arrays.asList(3));

        NoteDeleteCommand noteDeleteCommand = new NoteDeleteCommand(indexList);
        CommandResult result = noteDeleteCommand.execute(new ModelManager(), new CommandHistory());

        int size = indexList.size();
        assertEquals(String.format(expectedMessage, size), result.feedbackToUser);

        int expectedSize = 2;
        assertEquals(expectedSize, noteManager.getNotes().size());

        noteManager.setFilteredNotesNoFilter();

        // multiple deletion by index, must be sorted by descending order
        indexList = new ArrayList<>(Arrays.asList(2, 1));


        noteDeleteCommand = new NoteDeleteCommand(indexList);
        result = noteDeleteCommand.execute(new ModelManager(), new CommandHistory());

        size = indexList.size();
        assertEquals(String.format(expectedMessage, size), result.feedbackToUser);

        expectedSize = 0;
        assertEquals(expectedSize, noteManager.getNotes().size());
    }

    @AfterClass
    public static void tearDown() {
        noteManager.clearNotes();
        noteManager.saveNoteList();
    }
}
