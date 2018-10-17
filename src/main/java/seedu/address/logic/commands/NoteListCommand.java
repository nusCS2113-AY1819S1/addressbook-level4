package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.StorageController;
import seedu.address.model.note.Note;
import seedu.address.model.note.NoteManager;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Lists notes based on given predicate.
 */
public class NoteListCommand extends Command {

    public static final String COMMAND_WORD = "note list";

    public static final String MESSAGE_SUCCESS = "Listed %1$s note(s).\n";

    public static final String MESSAGE_NOT_FOUND = "No notes were found.";

    private NoteManager noteManager = new NoteManager();

    // private final Predicate<Note> predicate;
    private final String moduleCode;

    public NoteListCommand(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Note> filteredNotes;

        if (moduleCode.trim().length() > 0) {
            filteredNotes = noteManager.getNotes().stream()
                    .filter(p -> p.getModuleCode().equalsIgnoreCase(moduleCode)).collect(Collectors.toList());
        } else {
            filteredNotes = noteManager.getNotes();
        }

        StringBuilder sb = new StringBuilder();

        for (Note n: filteredNotes) {
            sb.append("Module Code: ");
            sb.append(n.getModuleCode() + "\n");
            sb.append("Date: ");
            sb.append(n.getDate() + "\n");
            sb.append("Note:\n");
            sb.append(n.getNoteText() + "\n\n");
        }

        if (sb.length() > 0) {
            return new CommandResult(
                    String.format(MESSAGE_SUCCESS, filteredNotes.size(), "s")
                            + "\n" + sb.toString());
        } else {
            return new CommandResult(String.format(MESSAGE_NOT_FOUND));
        }
    }
}
