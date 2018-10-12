package seedu.address.ui;

import javax.swing.JOptionPane;

import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Provides the UI for the login process.
 */
class LoginWindow {

    LoginWindow() {}

    /**
     * method to kick-start the login process.
     */
    void initializeLogin(Logic logic) {
        String loginInput = JOptionPane.showInputDialog("Please login first by entering login credentials:");
        try {
            logic.execute(loginInput);
        } catch (CommandException | ParseException e) {
            e.printStackTrace();
        }
    }
}
