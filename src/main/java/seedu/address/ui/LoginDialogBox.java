package seedu.address.ui;

import javax.swing.*;

public class LoginDialogBox {

    //@@author Chocological-reused
    //Reused from https://stackoverflow.com/posts/8853170/revisions with minor modifications
    private static JFrame login;
    private static JButton loginButton;

    public LoginDialogBox(JFrame login, JButton loginButton){
        LoginDialogBox.login = login;
        LoginDialogBox.loginButton = loginButton;
    }

    static JFrame getLoginFrame() {
        return login;
    }

    static void setLoginDialogBox() {
        login.add(loginButton);
        login.pack();
        login.setVisible(true);
    }
    //@@author
}
