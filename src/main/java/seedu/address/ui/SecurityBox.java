package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ShowLoginEvent;
import seedu.address.commons.events.ui.SuccessfulRegisterEvent;
import seedu.address.security.Security;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class SecurityBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "SecurityBox.fxml";

    private final Logger logger = LogsCenter.getLogger(SecurityBox.class);
    private final Security security;

    @FXML
    private TextField commandTextField;

    public SecurityBox(Security security) {
        super(FXML);
        this.security = security;
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String[] command = commandTextField.getText().trim().split("\\s+");
        if (command[0].equals("login") && command.length > 2) {
            security.login(command[1], command[2]);
        } else if (command[0].equals("register") && command.length == 6) {
            switch(security.register(command[1], command[2], command[3], command[4], command[5])) {
                case 1:
                    System.out.println("Success");
                    raise(new SuccessfulRegisterEvent());
                    break;
                case 2:
                    System.out.println("Failure: Username already used");
                default:
                    break;
            }
        } else if (command[0].equals("ui")) {
            raise(new ShowLoginEvent());
        }
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
