package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final String CONFIRMATION_OF_COMMAND_MESSAGE =
            "Please confirm the command: \n %1$s \n Press y or Y to confirm";
    private static final String FAILURE_TO_CONFIRM_COMMAND_MESSAGE =
            "You did not confirm your command. Please key in command again";
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private Logic logic;
    private ListElementPointer historySnapshot;
    private String commandEntered;

    @FXML
    private TextField commandTextField;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        historySnapshot = logic.getHistorySnapshot();
        registerAsAnEventHandler(this);
        commandEntered = "";
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case UP:
            // As up and down buttons will alter the position of the caret,
            // consuming it causes the caret's position to remain unchanged
            keyEvent.consume();

            navigateToPreviousInput();
            break;
        case DOWN:
            keyEvent.consume();
            navigateToNextInput();
            break;
        default:
            // let JavaFx handle the keypress
        }
    }

    /**
     * Updates the text field with the previous input in {@code historySnapshot},
     * if there exists a previous input in {@code historySnapshot}
     */
    private void navigateToPreviousInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasPrevious()) {
            return;
        }

        replaceText(historySnapshot.previous());
    }

    /**
     * Updates the text field with the next input in {@code historySnapshot},
     * if there exists a next input in {@code historySnapshot}
     */
    private void navigateToNextInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasNext()) {
            return;
        }

        replaceText(historySnapshot.next());
    }

    /**
     * Sets {@code CommandBox}'s text field with {@code text} and
     * positions the caret to the end of the {@code text}.
     */
    private void replaceText(String text) {
        commandTextField.setText(text);
        commandTextField.positionCaret(commandTextField.getText().length());
    }

    /**
     * process when command is confirmed
     */
    private void handleConfirmCommand() {
        try {

            CommandResult commandResult = logic.execute(commandEntered);
            initHistory();
            historySnapshot.next();
            // process result of the command
            commandTextField.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException e) {
            initHistory();
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }
    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        if (commandEntered.isEmpty ()) {
            commandEntered = commandTextField.getText ();
            EventsCenter.getInstance ().post (new NewResultAvailableEvent(
                                                String.format (CONFIRMATION_OF_COMMAND_MESSAGE , commandEntered)));
            commandTextField.setText ("");
        } else {
            if (checkCommandConfirmation (commandTextField.getText ())) {
                handleConfirmCommand();
                commandEntered = "";
            } else {
                EventsCenter.getInstance ().post (new NewResultAvailableEvent(FAILURE_TO_CONFIRM_COMMAND_MESSAGE));
                commandEntered = "";
                commandTextField.setText ("");
            }
        }
    }
    //=============uncomment this if u find comfirmation is annoying and c
    // comment out the above two method handleCommandEntered and handleConfirmCommand============
    //    @FXML
    //    private void handleCommandEntered() {
    //        try {
    //
    //            CommandResult commandResult = logic.execute(commandTextField.getText());
    //            initHistory();
    //            historySnapshot.next();
    //            // process result of the command
    //            commandTextField.setText("");
    //            logger.info("Result: " + commandResult.feedbackToUser);
    //            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
    //
    //        } catch (CommandException | ParseException e) {
    //            initHistory();
    //            // handle command failure
    //            setStyleToIndicateCommandFailure();
    //            logger.info("Invalid command: " + commandTextField.getText());
    //            raise(new NewResultAvailableEvent(e.getMessage()));
    //        }
    //    }

    /**
     *Returns true if test is equal to y or Y
     */
    private boolean checkCommandConfirmation(String test) {
        if (test.equals ("y") || test.equals ("Y")) {
            return true;
        }
        return false;
    }
    /**
     * Initializes the history snapshot.
     */
    private void initHistory() {
        historySnapshot = logic.getHistorySnapshot();
        // add an empty string to represent the most-recent end of historySnapshot, to be shown to
        // the user if she tries to navigate past the most-recent end of the historySnapshot.
        historySnapshot.add("");
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

}
