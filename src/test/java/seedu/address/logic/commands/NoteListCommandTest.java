package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.note.NoteManager;
import seedu.address.testutil.NoteBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Contains tests for NoteListCommand.
 */
public class NoteListCommandTest {

    private static NoteBuilder note1 = new NoteBuilder("CS1010", "10/10/2018", "C");
    private static NoteBuilder note2 = new NoteBuilder("CS2040C", "20/4/2018", "C++");
    private static NoteBuilder note3 = new NoteBuilder("CS2113", "31/12/2018", "Java");

    @Test
    public void execute_emptyList_displaysMessageNotFound() throws CommandException {
        NoteManager noteManager = new NoteManager();
        noteManager.saveNoteList();

        NoteListCommand noteListCommand = new NoteListCommand("");
        CommandResult result = noteListCommand.execute(new ModelManager(), new CommandHistory());

        assertEquals(NoteListCommand.MESSAGE_NOT_FOUND, result.feedbackToUser);
    }

    @Test
    public void execute_nonEmptyList_displaysList() throws CommandException {
        NoteManager noteManager = new NoteManager();
        noteManager.addNote(note1.build());
        noteManager.addNote(note2.build());
        noteManager.saveNoteList();

        NoteListCommand noteListCommand = new NoteListCommand("");
        CommandResult result = noteListCommand.execute(new ModelManager(), new CommandHistory());

        assertFalse(result.feedbackToUser.equals(NoteListCommand.MESSAGE_NOT_FOUND));
    }

    @Test
    public void execute_nonEmptyListWithFilter_displaysListOrMessageNotFound() throws CommandException {
        NoteManager noteManager = new NoteManager();
        noteManager.addNote(note1.build());
        noteManager.addNote(note2.build());
        noteManager.addNote(note3.build());
        noteManager.saveNoteList();

        NoteListCommand noteListCommand = new NoteListCommand("CS2113"); // exists
        CommandResult result = noteListCommand.execute(new ModelManager(), new CommandHistory());

        assertFalse(result.feedbackToUser.equals(NoteListCommand.MESSAGE_NOT_FOUND));

        noteListCommand = new NoteListCommand("CS5000"); // does not exist
        result = noteListCommand.execute(new ModelManager(), new CommandHistory());

        assertTrue(result.feedbackToUser.equals(NoteListCommand.MESSAGE_NOT_FOUND));
    }
}
