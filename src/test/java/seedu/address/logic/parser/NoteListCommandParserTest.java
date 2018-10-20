package seedu.address.logic.parser;

import static org.junit.Assert.assertNotEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.NoteListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelManager;
import seedu.address.model.note.NoteManager;
import seedu.address.testutil.NoteBuilder;

/**
 * Contains tests for NoteListCommandParser.
 */
public class NoteListCommandParserTest {

    private static NoteManager noteManager = new NoteManager();

    private NoteListCommandParser parser = new NoteListCommandParser();

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
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, NoteListCommand.MESSAGE_USAGE);

        // invalid args, missing prefix
        String args = " CS2113";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);

        parser.parse(args);
    }

    @Test
    public void parse_validArgs_success() throws ParseException, CommandException {
        String unwantedMessage = NoteListCommand.MESSAGE_NOT_FOUND;

        NoteBuilder note1 = new NoteBuilder("CS2113", "30/03/2030", "Java");
        NoteBuilder note2 = new NoteBuilder("CS2040C", "03/03/2040", "C++");
        noteManager.addNote(note1.build());
        noteManager.addNote(note2.build());
        noteManager.saveNoteList();

        // valid empty args
        String args1 = "";
        NoteListCommand noteListCommand = parser.parse(args1);
        CommandResult result = noteListCommand.execute(new ModelManager(), new CommandHistory());
        assertNotEquals(unwantedMessage, result.feedbackToUser);

        // valid args with MODULE_CODE
        String args2 = " m/CS2113";
        noteListCommand = parser.parse(args2);
        result = noteListCommand.execute(new ModelManager(), new CommandHistory());
        assertNotEquals(unwantedMessage, result.feedbackToUser);
    }

    @AfterClass
    public static void tearDown() {
        noteManager.clearNotes();
        noteManager.saveNoteList();
    }
}
