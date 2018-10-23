package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import seedu.address.model.note.Note;

/**
 * This is the controller for the NoteEntryPromptWindow FXML.
 * It consists of a TextArea for the user to enter input in multiple lines.
 */
public class NoteEntryPrompt {

    private static final String FXML = "NoteEntryPrompt.fxml";
    private final KeyCombination keyCombinationSave = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
    private final KeyCombination keyCombinationCancel = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN);

    @FXML
    private TextArea noteContent;

    private Stage dialogStage;
    private Note note;

    private boolean isCancelled = false;

    public NoteEntryPrompt() {

    }

    private void initialize() {

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Populates the TextArea with the editable note.
     *
     * @param note
     */
    public void setNote(Note note) {
        this.note = note;

        noteContent.setText(note.getNoteText());
        noteContent.setPromptText("Enter note here.");
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        if (keyCombinationSave.match(keyEvent)) {
            saveAndCloseWindow();
        }

        if (keyCombinationCancel.match(keyEvent)) {
            closeWindow();
        }
    }

    /**
     * Saves the note and closes the window.
     */
    public void saveAndCloseWindow() {
        String tempNote = noteContent.getText();

        if (tempNote.trim().length() > 0) {
            note.setNoteText(tempNote);
            dialogStage.close();
        }
    }

    /**
     * Closes the window without saving the note data.
     */
    public void closeWindow() {
        isCancelled = true;
        dialogStage.close();
    }

    /**
     * Returns true if the user pressed the key combination to cancel.
     * Otherwise, return false.
     *
     * @return value of isCancelled.
     */
    public boolean isCancelled() {
        return isCancelled;
    }
}

