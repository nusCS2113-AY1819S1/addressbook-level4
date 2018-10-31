package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a note. "
            + "Parameters: "
            + "INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + "2";

    public static final String MESSAGE_SUCCESS = "Note has been deleted.";
    public static final String MESSAGE_INVALID_INDEX = "Invalid input!\nINDEX %1$s is out of bounds.";
    public static final String MESSAGE_NOTE_PAGE_NOT_LOADED = "The command has been blocked by the system.\n"
            + "Please call the command to list notes before calling this command again "
            + "to avoid accidentally deleting another note.\n"
            + "Command: " + NoteListCommand.COMMAND_WORD
            + " [" + PREFIX_MODULE_CODE + "MODULE_CODE]";

    private final int index;

    public NoteDeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (!BrowserPanel.isNotePageLoaded()) {
            return new CommandResult(MESSAGE_NOTE_PAGE_NOT_LOADED);
        }

        NoteManager noteManager = NoteManager.getInstance();

        if (index > noteManager.getFilteredNotes().size() || index < 1) {
            throw new CommandException(String.format(MESSAGE_INVALID_INDEX, index));
        } else {
            noteManager.deleteNote(index - 1);
            noteManager.saveNoteList();

            String noteList = noteManager.getHtmlNoteList();

            return new CommandResult(MESSAGE_SUCCESS, noteList);
        }
    }
}
