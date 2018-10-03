package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTETEXT;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.StorageController;
import seedu.address.model.note.Note;


/**
 * This command adds a note.
 */
public class AddNoteCommand extends Command {

    public static final String COMMAND_WORD = "note_add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a note to a module. "
            + "Parameters: "
            + PREFIX_MODULECODE + "MODULE_CODE "
            + PREFIX_NOTETEXT + "TEXT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULECODE + "CS2113 "
            + PREFIX_NOTETEXT + "Hello, this is my first note!";

    public static final String MESSAGE_SUCCESS = "This note has been added to %1$s.";
    public static final String MESSAGE_CANCEL = "Creating of note has been cancelled.";

    private Note toAdd;

    public AddNoteCommand(Note note) {
        requireNonNull(note);
        toAdd = note;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        StorageController.retrieveData();
        StorageController.getNoteStorage().add(new Note(toAdd.getNote(), toAdd.getModuleCode()));
        StorageController.storeData();

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getModuleCode()));
    }
}
