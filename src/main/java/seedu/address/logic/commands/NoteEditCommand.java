package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_DATE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Note;
import seedu.address.model.note.NoteManager;
import seedu.address.ui.NoteTextEditWindow;

/**
 * Edits a note in Trajectory.
 */
public class NoteEditCommand extends Command {

    public static final String COMMAND_WORD = "note edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a note. "
            + "Parameters: "
            + "INDEX "
            + "[" + PREFIX_MODULECODE
            + "MODULE_CODE] "
            + "[" + PREFIX_NOTE_DATE
            + "DATE]\n"
            + "Example: " + COMMAND_WORD + " "
            + "4";

    public static final String MESSAGE_SUCCESS = "Note has been edited.";
    public static final String MESSAGE_CANCEL = "Edit note operation has been cancelled.";
    public static final String MESSAGE_INVALID_INDEX = "Invalid input!\nINDEX %1$s is out of bounds.";

    private final int index;
    private final String moduleCode;
    private final String date;

    public NoteEditCommand(int index, String moduleCode, String date) {
        this.index = index;
        this.moduleCode = moduleCode;
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        NoteManager noteManager = new NoteManager();

        if (index > noteManager.getNotes().size() || index < 1) {
            throw new CommandException(String.format(MESSAGE_INVALID_INDEX, index));
        }

        Note noteToEdit = noteManager.getNoteAt(index - 1);

        if (!moduleCode.isEmpty()) {
            noteToEdit.setModuleCode(moduleCode);
        }

        if (!date.isEmpty()) {
            noteToEdit.setDate(date);
        }

        NoteTextEditWindow noteTextEditWindow = new NoteTextEditWindow(noteToEdit);
        noteTextEditWindow.showAndWait();

        if (!noteTextEditWindow.isCancelled()) {
            noteManager.editNote(index - 1, noteToEdit);
            noteManager.saveNoteList();

            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_CANCEL);
        }
    }
}
