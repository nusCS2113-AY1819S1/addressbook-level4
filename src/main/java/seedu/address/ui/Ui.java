package seedu.address.ui;

import javafx.stage.Stage;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage, Model model, CommandHistory history);

    /** Stops the UI. */
    void stop();

}
