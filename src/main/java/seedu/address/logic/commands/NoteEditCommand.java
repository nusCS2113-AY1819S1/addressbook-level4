package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
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
            + "[" + PREFIX_MODULE_CODE
            + "NEW_MODULE_CODE] "
            + "[" + PREFIX_NOTE_DATE
            + "NEW_DATE]\n"
            + "Example: " + COMMAND_WORD + " "
            + "4";

    public static final String MESSAGE_SUCCESS = "Note has been edited.";
    public static final String MESSAGE_CANCEL = "Edit note operation has been cancelled.";
    public static final String MESSAGE_INVALID_INDEX = "Invalid input!\nINDEX %1$s is out of bounds.";
    public static final String MESSAGE_INVALID_DATE_TIME_DIFFERENCE =
            "Invalid input! Please make sure the start date/time is earlier than the end date/time.";

    private final int index;
    private final String moduleCode;
    private final String title;
    private final String startDate;
    private final String startTime;
    private final String endDate;
    private final String endTime;
    private final String location;

    public NoteEditCommand(
            int index,
            String moduleCode,
            String title,
            String startDate,
            String startTime,
            String endDate,
            String endTime,
            String location) {
        this.index = index;
        this.moduleCode = moduleCode;
        this.title = title;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.location = location;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        NoteManager noteManager = NoteManager.getInstance();

        if (index > noteManager.getFilteredNotes().size() || index < 1) {
            throw new CommandException(String.format(MESSAGE_INVALID_INDEX, index));
        }

        Note noteToEdit = noteManager.getNoteAt(index - 1);

        // TODO: Validate date & time difference for 'start' and 'end' here

        NoteTextEditWindow noteTextEditWindow = new NoteTextEditWindow(noteToEdit);
        noteTextEditWindow.showAndWait();

        if (!noteTextEditWindow.isCancelled()) {
            if (!moduleCode.isEmpty()) {
                noteToEdit.setModuleCode(moduleCode);
            }

            if (!title.isEmpty()) {
                noteToEdit.setTitle(title);
            }

            if (!startDate.isEmpty()) {
                noteToEdit.setStartDate(startDate);
            }

            if (!startTime.isEmpty()) {
                noteToEdit.setStartTime(startTime);
            }

            if (!endDate.isEmpty()) {
                noteToEdit.setEndDate(endDate);
            }

            if (!endTime.isEmpty()) {
                noteToEdit.setEndTime(endTime);
            }

            if (!location.isEmpty()) {
                noteToEdit.setLocation(location);
            }

            noteManager.saveNoteList();

            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_CANCEL);
        }
    }
}
