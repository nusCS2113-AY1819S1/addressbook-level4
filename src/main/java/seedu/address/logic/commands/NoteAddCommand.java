package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_DATE;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.stage.WindowEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Note;
import seedu.address.model.note.NoteManager;
import seedu.address.ui.NoteEntryPrompt;
import seedu.address.ui.NoteTextEditWindow;

/**
 * Adds a note to Trajectory.
 */
public class NoteAddCommand extends Command {

    public static final String COMMAND_WORD = "note add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a note. "
            + "Parameters: "
            + PREFIX_MODULECODE + "MODULE CODE "
            + "[" + PREFIX_NOTE_DATE + "DATE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULECODE + "CS2113";

    public static final String MESSAGE_SUCCESS = "Note has been added to %1$s.";
    public static final String MESSAGE_CANCEL = "Note creation has been cancelled.";

    private Note noteToAdd;

    public NoteAddCommand(Note note) {
        requireNonNull(note);
        noteToAdd = note;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        NoteManager noteManager = new NoteManager();

        NoteTextEditWindow noteTextEditWindow = new NoteTextEditWindow(noteToAdd);
        noteTextEditWindow.showAndWait();

        if (!noteTextEditWindow.isCancelled()) {
            noteManager.addNote(noteToAdd);
            noteManager.saveNoteList();

            return new CommandResult(String.format(MESSAGE_SUCCESS, noteToAdd.getModuleCode()));
        } else {
            return new CommandResult(MESSAGE_CANCEL);
        }
    }
}
