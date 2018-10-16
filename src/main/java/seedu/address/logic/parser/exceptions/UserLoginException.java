package seedu.address.logic.parser.exceptions;

import javax.swing.*;

/**
 * Represents a login error encountered when the user enter incorrect login credentials.
 */
public class UserLoginException {

    public UserLoginException() {}

    public void showLoginError() {
        JOptionPane.showMessageDialog(null, "Incorrect user Id/Password. Please try again.");
    }
}
