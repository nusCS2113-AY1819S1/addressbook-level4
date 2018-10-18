package seedu.address.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import seedu.address.model.note.Note;

/**
 * Handles the displaying of a TextArea window for adding/editing a note.
 */
public class NoteTextEditWindow {

    private static final String PATH = "/view/NoteEntryPromptWindow.fxml";

    private BorderPane layout;
    private FXMLLoader loader;
    private Scene scene;
    private Stage notePromptStage;

    private NoteEntryPrompt controller;

    private Note note;

    public NoteTextEditWindow(Note note) {
        this.note = note;
        setUpWindow();
    }

    /**
     * This method sets up the window to be loaded and
     * handles its configuration and behaviour.
     */
    private void setUpWindow() {
        try {
            controller = new NoteEntryPrompt();

            loader = new FXMLLoader(getClass().getResource(PATH));
            loader.setController(controller);
            layout = loader.load();

            scene = new Scene(layout);

            notePromptStage = new Stage();
            notePromptStage.setMinHeight(380);
            notePromptStage.setMinWidth(350);
            notePromptStage.setTitle("Add/Edit note");
            notePromptStage.initModality(Modality.APPLICATION_MODAL);
            notePromptStage.setScene(scene);
            notePromptStage.setOnCloseRequest(e -> {
                e.consume();
                controller.closeWindow();
            });

            controller.setDialogStage(notePromptStage);
            controller.setNote(note);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAndWait() {
        notePromptStage.showAndWait();
    }

    public boolean isCancelled() {
        return controller.isCancelled();
    }
}
