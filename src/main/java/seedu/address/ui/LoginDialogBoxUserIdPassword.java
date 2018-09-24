package seedu.address.ui;

import javax.swing.*;

public class LoginDialogBoxUserIdPassword {

    //@@author Chocological-reused
    //Reused from https://stackoverflow.com/posts/6555051/revisions with minor modifications
    private static JTextField userId = new JTextField(5);
    private static JTextField userPassword = new JTextField(5);

    public  LoginDialogBoxUserIdPassword() {

        JPanel loginDetails = new JPanel();
        loginDetails.add(new JLabel("user ID:"));
        loginDetails.add(userId);
        loginDetails.add(Box.createHorizontalStrut(15)); // a spacer
        loginDetails.add(new JLabel("user Password:"));
        loginDetails.add(userPassword);

        JOptionPane.showInputDialog(loginDetails, "Please enter user ID and Password: ", null);
    }
    //@@author

    public static String getUserId() {
        return userId.getText();
    }

    public static String getUserPassword() {
        return userPassword.getText();
    }
}
