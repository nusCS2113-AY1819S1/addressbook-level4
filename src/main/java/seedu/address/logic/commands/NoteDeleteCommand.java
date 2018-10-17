package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.NoteManager;

/**
 * Deletes a note from Trajectory.
 */
public class NoteDeleteCommand extends Command {

    public static final String COMMAND_WORD = "note delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a note. "
            + "Parameters: "
            + "INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + "2";

    public static final String MESSAGE_SUCCESS = "Note has been deleted.";
    public static final String MESSAGE_INVALID_INDEX = "The index provided is out of bounds.";

    private final int index;

    public NoteDeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        NoteManager noteManager = new NoteManager();

        System.out.println("ArrayList size: " + noteManager.getNotes().size());

        if (index > noteManager.getNotes().size() || index < 1) {
            return new CommandResult(MESSAGE_INVALID_INDEX);
        } else {
            noteManager.deleteNote(index - 1);
            noteManager.saveNoteList();

            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
}
