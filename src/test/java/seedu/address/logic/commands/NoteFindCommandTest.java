package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.note.NoteManager;
import seedu.address.testutil.NoteBuilder;

/**
 * Contains tests for NoteFindCommand.
 */
public class NoteFindCommandTest {

    private static NoteManager noteManager = NoteManager.getInstance();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private NoteBuilder note1 = new NoteBuilder();

    @Before
    public void setUp() {
        noteManager.clearNotes();
        noteManager.saveNoteList();
    }

    @Test
    public void execute_emptyList_displaysMessageNotFound() throws CommandException {
        String expectedMessage = NoteFindCommand.MESSAGE_NOT_FOUND;

        // create the list of keywords
        List<String> keywords = new ArrayList<>(Arrays.asList(
                "hello",
                "world"
        ));

        NoteFindCommand noteFindCommand = new NoteFindCommand(keywords);
        CommandResult result = noteFindCommand.execute(new ModelManager(), new CommandHistory());

        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @Test
    public void execute_nonEmptyList_displaysMessageNotFound() throws CommandException {
        String expectedMessage = NoteFindCommand.MESSAGE_NOT_FOUND;

        NoteBuilder noteA = new NoteBuilder(note1.withNoteText("hello").build());
        NoteBuilder noteB = new NoteBuilder(note1.withNoteText("world").build());

        noteManager.addNote(noteA.build());
        noteManager.addNote(noteB.build());
        noteManager.saveNoteList();

        // create the list of keywords
        List<String> keywords = new ArrayList<>(Arrays.asList(
                "hello",
                "world"
        ));

        NoteFindCommand noteFindCommand = new NoteFindCommand(keywords);
        CommandResult result = noteFindCommand.execute(new ModelManager(), new CommandHistory());

        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @Test
    public void execute_nonEmptyList_success() throws CommandException {
        String expectedMessage = NoteFindCommand.MESSAGE_SUCCESS;

        NoteBuilder noteA = new NoteBuilder(note1.withNoteText("hello").build());
        NoteBuilder noteB = new NoteBuilder(note1.withNoteText("world\n  heLLO").build());

        noteManager.addNote(noteA.build());
        noteManager.addNote(noteB.build());
        noteManager.saveNoteList();

        // create the list of keywords
        List<String> keywords = new ArrayList<>(Arrays.asList(
                "hello",
                "world"
        ));

        NoteFindCommand noteFindCommand = new NoteFindCommand(keywords);
        CommandResult result = noteFindCommand.execute(new ModelManager(), new CommandHistory());

        assertEquals(String.format(expectedMessage, 1), result.feedbackToUser);
    }

    @AfterClass
    public static void tearDown() {
        noteManager.clearNotes();
        noteManager.saveNoteList();
    }
}
