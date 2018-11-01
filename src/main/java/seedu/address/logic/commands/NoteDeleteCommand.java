package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.NoteManager;
import seedu.address.ui.BrowserPanel;

/**
 * Deletes a note from Trajectory.
 */
public class NoteDeleteCommand extends Command {

    public static final String COMMAND_WORD = "note delete";

    public static final String MESSAGE_DUPLICATE_INDEX_FOUND = "Invalid input!\n"
            + "Duplicate indexes are found.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete notes. "
            + "Parameters: "
            + "INDEX [MORE_INDEXES]..\n"
            + "Example: " + COMMAND_WORD + " "
            + "2 4 3";

    public static final String MESSAGE_SUCCESS = "(%s) note(s) successfully deleted.";
    public static final String MESSAGE_INVALID_INDEX = "Invalid input! INDEX is out of bounds.";
    public static final String MESSAGE_PARSE_INDEX_ERROR = "You entered an invalid index. "
            + "Please enter positive integers only.";

    public static final String MESSAGE_NOTE_PAGE_NOT_LOADED = "The command has been blocked by the system.\n"
            + "Please call the command to list notes before calling this command again "
            + "to avoid accidentally deleting a different note.\n"
            + "Command: " + NoteListCommand.COMMAND_WORD
            + " [" + PREFIX_MODULE_CODE + "MODULE_CODE]";

    private final List<Integer> indexList;


    public NoteDeleteCommand(List<Integer> indexList) {
        requireNonNull(indexList);
        this.indexList = indexList;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (!BrowserPanel.isNotePageLoaded()) {
            return new CommandResult(MESSAGE_NOTE_PAGE_NOT_LOADED);
        }

        NoteManager noteManager = NoteManager.getInstance();

        if (indexList.get(0) > noteManager.getFilteredNotes().size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        for (Integer index : indexList) {
            noteManager.deleteNote(index - 1);
            noteManager.saveNoteList();
        }

        String noteList = noteManager.getHtmlNoteList();
        int size = indexList.size();

        return new CommandResult(String.format(MESSAGE_SUCCESS, Integer.toString(size)), noteList);
    }
}
