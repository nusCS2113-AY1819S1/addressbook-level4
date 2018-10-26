package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists notes. "
            + "Parameters: "
            + "[" + PREFIX_MODULE_CODE + "MODULE_CODE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS2113";

    public static final String MESSAGE_SUCCESS = "Listed %1$s note(s).";

    public static final String MESSAGE_NOT_FOUND = "No notes were found.";

    private final String moduleCode;

    public NoteListCommand(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        NoteManager noteManager = NoteManager.getInstance();

        noteManager.setFilteredNotes(moduleCode);

        if (noteManager.getFilteredNotes().size() == 0) {
            noteManager.setFilteredNotes(noteManager.getCurrentFilter());
            return new CommandResult(String.format(MESSAGE_NOT_FOUND));
        }

        StringBuilder sb = new StringBuilder();

        int listId = 1;
        int size = noteManager.getFilteredNotes().size();

        for (Note n: noteManager.getFilteredNotes()) {
            sb.append(listId + ":\n");

            sb.append("Module Code: ");
            if (!n.getModuleCode().isEmpty()) {
                sb.append(n.getModuleCode() + "\n");
            } else {
                sb.append("Not assigned\n");
            }

            sb.append("Title: ");
            if (!n.getTitle().isEmpty()) {
                sb.append(n.getTitle() + "\n");
            } else {
                sb.append("Not specified\n");
            }

            sb.append("Start Date: ");
            if (!n.getStartDate().isEmpty()) {
                sb.append(n.getStartDate() + "\n");
            } else {
                sb.append("Not specified\n");
            }

            sb.append("Start Time: ");
            if (!n.getStartTime().isEmpty()) {
                sb.append(n.getStartTime() + "\n");
            } else {
                sb.append("Not specified\n");
            }

            sb.append("End Date: ");
            if (!n.getEndDate().isEmpty()) {
                sb.append(n.getEndDate() + "\n");
            } else {
                sb.append("Not specified\n");
            }

            sb.append("End Time: ");
            if (!n.getEndTime().isEmpty()) {
                sb.append(n.getEndTime() + "\n");
            } else {
                sb.append("Not specified\n");
            }

            sb.append("Location: ");
            if (!n.getLocation().isEmpty()) {
                sb.append(n.getLocation() + "\n");
            } else {
                sb.append("Not specified\n");
            }

            sb.append("Note:\n");
            sb.append(n.getNoteText() + ((listId < size) ? "\n\n" : "\n"));

            listId++;
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, size)
                        + "\n" + sb.toString());
    }
}
