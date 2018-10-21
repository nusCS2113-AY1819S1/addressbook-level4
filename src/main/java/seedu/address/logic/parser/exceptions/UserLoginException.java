package seedu.address.logic.parser.exceptions;

import javax.swing.JOptionPane;

/**
 * Represents a login error encountered when the user enter incorrect login credentials.
 */
public class UserLoginException {

    public UserLoginException() {}

    /**
     * Shows an error message to the user in the event that the login fails due to incorrect user id, password or role.
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
     * Shows an error message to the user in the event that the user forgets to input either user id, password or role.
     */
    public void showMissingLoginError() {
        JOptionPane.showMessageDialog(null, "Missing user Id/Password/Role input!");
    }

    /**
     * Shows an error message to the user in the event that the user presses enter key without any input.
     */
    public void showBlankLoginError() {
        JOptionPane.showMessageDialog(null, "Please type in your login credentials!");
    }

    /**
     * Shows a message to user containing help in the login process.
     */
    public void showLoginUsage() {
        JOptionPane.showMessageDialog(null, "Login into NUSSU-Connect with input "
                + "user ID, password and role."
                + "Parameters: USERID PASSWORD ROLE\n"
                + "Example: login A3583758X 1qaxcdwd2w member");
    }
}
