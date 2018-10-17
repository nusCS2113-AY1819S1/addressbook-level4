package seedu.address.logic.commands;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.note.NoteManager;
import seedu.address.testutil.NoteBuilder;

import static org.junit.Assert.assertEquals;

public class NoteListCommandTest {
    final NoteBuilder note1 = new NoteBuilder("CS2113", "12-12-2012", "Hello");
    final NoteBuilder note2 = new NoteBuilder("CS2040C", "12-21-2012", "Hello2");

    final Model modelStub = new ModelManager();
    final CommandHistory commandHistoryStub = new CommandHistory();

    @Test
    public void execute_moduleCode_notFound() throws ParseException, CommandException {
        String filterByModule = "CS1010"; // will not be in the list

        NoteManager noteManager = new NoteManager();

        // initialize the predicate
        NoteListCommand noteListCommand = new NoteListCommand(p -> p.getModuleCode()
                .equalsIgnoreCase(filterByModule));

        // populate the note list model
        noteManager.addNote(note1.build());
        noteManager.addNote(note2.build());

        // execute() method of NoteListCommand will assert the predicate to the model's FilteredList
        CommandResult commandResult = noteListCommand.execute(modelStub, commandHistoryStub);

        assertEquals(NoteListCommand.MESSAGE_NOT_FOUND, commandResult.feedbackToUser);
    }
}
