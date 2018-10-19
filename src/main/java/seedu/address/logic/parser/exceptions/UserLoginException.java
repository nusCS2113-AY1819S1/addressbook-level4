package seedu.address.logic.parser.exceptions;

import javax.swing.JOptionPane;

/**
 * Represents a login error encountered when the user enter incorrect login credentials.
 */
public class UserLoginException {

    public UserLoginException() {}

    /**
     * Shows an error message to the user in the event that the login fails.
     */
    public void showLoginError() {
        JOptionPane.showMessageDialog(null, "Incorrect user Id/Password/Role. "
                + "Please try again.");
    }

    /**
     * Shows an error message to the user in the event that the user inputs invalid login parameters.
     */
    public void showInvalidLoginError() {
        JOptionPane.showMessageDialog(null, "Invalid login parameters!");
    }

    /**
     * Shows an error message to the user in the event that the user presses enter key without any input.
     */
    public void showBlankLoginError() {
        JOptionPane.showMessageDialog(null, "Please type in your login credentials!");
    }
}
