package seedu.address.logic.commands;

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
 * Contains tests for NoteEditCommand.
 */
public class NoteEditCommandTest {

    private static NoteManager noteManager = NoteManager.getInstance();

    private NoteBuilder note1 = new NoteBuilder(
            "CS1010",
            "First note",
            "",
            "",
            "",
            "",
            "",
            "C");

    private NoteBuilder note2 = new NoteBuilder(
            "CS2040C",
            "Second note",
            "",
            "",
            "",
            "",
            "",
            "C++");

    private NoteBuilder note3 = new NoteBuilder(
            "CS2113",
            "Third note",
            "",
            "",
            "",
            "",
            "",
            "Java");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        noteManager.clearNotes();
        noteManager.saveNoteList();
    }

    @Test
    public void execute_indexOutOfBounds_throwsCommandException() throws CommandException {
        noteManager.addNote(note1.build());
        noteManager.addNote(note2.build());
        noteManager.addNote(note3.build());
        noteManager.saveNoteList();

        int index = 5; // arraylist size: 3, accessed index = 4 (zero-based) -> out of bounds
        String newModuleCode = "CS5000";
        String newTitle = "My new title";
        String newStartDate = "1/1/2019";
        String newStartTime = "11:00 AM";
        String newEndDate = "1/1/2019";
        String newEndTime = "1:00 PM";
        String newLocation = "National University of Singapore";

        NoteEditCommand noteEditCommand =
                new NoteEditCommand(
                        index,
                        newModuleCode,
                        newTitle,
                        newStartDate,
                        newStartTime,
                        newEndDate,
                        newEndTime,
                        newLocation
                );

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(NoteEditCommand.MESSAGE_INVALID_INDEX, index));
        noteEditCommand.execute(new ModelManager(), new CommandHistory());
    }

    @AfterClass
    public static void tearDown() {
        noteManager.clearNotes();
        noteManager.saveNoteList();
    }
}
