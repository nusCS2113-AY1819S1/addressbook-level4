package seedu.address.ui;

import javax.swing.*;

public class LoginDialogBoxUserIdPassword {

    //@@author Chocological-reused
    //Reused from https://stackoverflow.com/posts/6555051/revisions with minor modifications
    private static JTextField userId = new JTextField(5);
    private static JTextField userPassword = new JTextField(5);

    LoginDialogBoxUserIdPassword() {}

    String getUserId() {
        return userId.getText();
    }

    String getUserPassword() {
        return userPassword.getText();
    }

    void loginDialogBoxUserIdPassword() {
        JPanel loginDetails = new JPanel();
        loginDetails.add(new JLabel("user ID:"));
        loginDetails.add(userId);
        loginDetails.add(Box.createHorizontalStrut(15)); // a spacer
        loginDetails.add(new JLabel("user Password:"));
        loginDetails.add(userPassword);

        JOptionPane.showInputDialog(loginDetails, "Please enter user ID and Password: ", null);
    }
    //@@author

    void showMessageDeleteAccount() {
        JOptionPane.showMessageDialog(null, "Please enter user ID and password of existing " +
                                                                   "account to be deleted");
    }

    void showMessageChangePassword() {
        JOptionPane.showMessageDialog(null, "Please enter user ID, current password and new " +
                                                                   "password of desired account to change password");
    }
}
