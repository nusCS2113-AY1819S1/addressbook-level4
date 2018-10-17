package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_MODULE_CODE;

import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Note;
import seedu.address.model.note.NoteManager;

/**
 * Lists notes based on given predicate.
 */
public class NoteListCommand extends Command {

    public static final String COMMAND_WORD = "note list";

    public static final String MESSAGE_SUCCESS = "Notes have been listed.";

    public static final String MESSAGE_NOT_FOUND = "No notes were found. Displaying all notes.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists notes. "
            + "Parameters: ["
            + PREFIX_NOTE_MODULE_CODE + "MODULE CODE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NOTE_MODULE_CODE + "CS2040C";

    private NoteManager noteManager = new NoteManager();

    private final Predicate<Note> predicate;

    public NoteListCommand(Predicate<Note> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        noteManager.updateFilteredNotesList(predicate);

        if (noteManager.getFilteredNotesListSize() > 0) {
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            noteManager.updateFilteredNotesList(null);
            return new CommandResult(MESSAGE_NOT_FOUND);
        }
    }
}
