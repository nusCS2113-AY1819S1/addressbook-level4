package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.StorageController;
import seedu.address.model.Model;
import seedu.address.model.note.Note;


/**
 * Lists all notes from a module to the user.
 */
public class ListNoteCommand extends Command {

    public static final String COMMAND_WORD = "note list";

    public static final String MESSAGE_SUCCESS = "Listed all notes from %1$s.";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        StorageController.retrieveData();

        StringBuilder sb = new StringBuilder();
        int index = 1;

        for (Note note: StorageController.getNoteStorage()) {
            sb.append("Index: " + index + "\n");
            sb.append("Note:" + "\n");
            sb.append(note.getNote() + "\n");

            index++;
        }
        return new CommandResult(MESSAGE_SUCCESS + "\n" + sb.toString());
    }
}
