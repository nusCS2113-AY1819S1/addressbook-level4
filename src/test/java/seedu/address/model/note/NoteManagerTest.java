package seedu.address.model.note;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.testutil.NoteBuilder;

import static org.junit.Assert.assertTrue;

/**
 * Contains tests for NoteManager.
 */
public class NoteManagerTest {

    private static NoteManager noteManager;

    private static NoteBuilder note1 = new NoteBuilder("CS1010", "10/10/2018", "C");
    private static NoteBuilder note2 = new NoteBuilder("CS2040C", "20/4/2018", "C++");
    private static NoteBuilder note3 = new NoteBuilder("CS2113", "31/12/2018", "Java");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void editNote_nullNote_throwsNullPointerException() {
        noteManager = new NoteManager();
        int index = 1;

        thrown.expect(NullPointerException.class);
        noteManager.editNote(index, null);
    }

    @Test
    public void getNoteAt_outOfBoundsIndex_returnsNull() {
        noteManager = new NoteManager();
        noteManager.addNote(note1.build()); // at index 0
        noteManager.addNote(note2.build()); // at index 1
        noteManager.addNote(note3.build()); // at index 2
        noteManager.saveNoteList();

        int index = 3; // try to access index 3
        Note note = noteManager.getNoteAt(index);

        assertTrue(note == null);
    }
}
