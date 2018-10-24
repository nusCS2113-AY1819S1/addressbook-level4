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
    private static final String DEFAULT_WINDOW_TITLE = "Add/Edit note";
    private static final double DEFAULT_MINIMUM_WIDTH = 380.0;
    private static final double DEFAULT_MINIMUM_HEIGHT = 350.0;


    private Stage notePromptStage;
    private NoteEntryPrompt controller;
    private Note note;

    public NoteTextEditWindow(Note note) {
        this.note = note;
        setUpWindow(DEFAULT_MINIMUM_WIDTH, DEFAULT_MINIMUM_HEIGHT, DEFAULT_WINDOW_TITLE);
    }

    /**
     * This method sets up the window to be loaded and
     * handles its configuration and behaviour.
     */
    private void setUpWindow(double minWidth, double minHeight, String title) {
        try {
            controller = new NoteEntryPrompt();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH));
            loader.setController(controller);
            BorderPane layout = loader.load();

            Scene scene = new Scene(layout);

            notePromptStage = new Stage();
            notePromptStage.setMinWidth(minWidth);
            notePromptStage.setMinHeight(minHeight);
            notePromptStage.setTitle(title);
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
