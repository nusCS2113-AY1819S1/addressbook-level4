package seedu.address.model.note;

import org.junit.Test;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.testutil.NoteBuilder;

import static org.junit.Assert.assertEquals;

public class NoteManagerTest {
    final NoteBuilder note1 = new NoteBuilder("CS2113", "12-12-2012", "Hello");
    final NoteBuilder note2 = new NoteBuilder("CS2040C", "12-21-2012", "Hello2");

    @Test
    public void updateFilteredNotesList_moduleCode_isFound() throws CommandException {
        String filterByModule = "CS2113";

        NoteManager noteManager = new NoteManager();
        noteManager.addNote(note1.build());
        noteManager.addNote(note2.build());

        noteManager.updateFilteredNotesList(p -> p.getModuleCode().equalsIgnoreCase(filterByModule));

        int size = noteManager.getFilteredNotesListSize();

        assertEquals(1, size);
    }

    @Test
    public void updateFilteredNotesList_moduleCode_notFound() throws CommandException {
        String filterByModule = "CS1010";

        NoteManager noteManager = new NoteManager();
        noteManager.addNote(note1.build());
        noteManager.addNote(note2.build());

        noteManager.updateFilteredNotesList(p -> p.getModuleCode().equalsIgnoreCase(filterByModule));

        int size = noteManager.getFilteredNotesListSize();

        assertEquals(0, size);
    }
}
