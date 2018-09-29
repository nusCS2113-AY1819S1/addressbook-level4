package seedu.address.ui;

import javax.swing.*;

public class LoginDialogBox {

    //@@author Chocological-reused
    //Reused from https://stackoverflow.com/posts/8853170/revisions with minor modifications
    private static JFrame login = new JFrame();
    private static JButton loginButton = new JButton();

    public LoginDialogBox(){}

    public static JFrame getLoginFrame() {
        return login;
    }

    public static void setLoginDialogBox() {
        login.add(loginButton);
        login.pack();
        login.setVisible(true);
    }
    //@@author
}
