package seedu.address.model.note;

import static org.junit.Assert.assertNull;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.NoteBuilder;

/**
 * Contains tests for NoteManager.
 */
public class NoteManagerTest {

    private static NoteManager noteManager = new NoteManager();

    private NoteBuilder note1 = new NoteBuilder("CS1010", "10/10/2018", "C");
    private NoteBuilder note2 = new NoteBuilder("CS2040C", "20/4/2018", "C++");
    private NoteBuilder note3 = new NoteBuilder("CS2113", "31/12/2018", "Java");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        noteManager.clearNotes();
        noteManager.saveNoteList();
    }

    @Test
    public void editNote_nullNote_throwsNullPointerException() throws NullPointerException {
        int index = 1;

        thrown.expect(NullPointerException.class);
        noteManager.editNote(index, null);
    }

    @Test
    public void getNoteAt_outOfBoundsIndex_returnsNull() throws NullPointerException {
        noteManager.addNote(note1.build()); // at index 0
        noteManager.addNote(note2.build()); // at index 1
        noteManager.addNote(note3.build()); // at index 2
        noteManager.saveNoteList();

        int index = 3; // try to access index 3
        Note note = noteManager.getNoteAt(index);

        assertNull(note);
    }

    @AfterClass
    public static void tearDown() {
        noteManager.clearNotes();
        noteManager.saveNoteList();
    }
}
