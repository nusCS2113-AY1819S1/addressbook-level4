package seedu.address.ui;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The login window. Provides the basic application layout containing
 * a simple text box for providing additional commands, a text field for
 * providing user ID, and a text field for providing password.
 */
public class LoginWindow {

    private static boolean isLoginSuccessful = false;
    private static boolean isUserIdExists = false;

    public static boolean getIsLoginSuccessful() {
        return isLoginSuccessful;
    }

    /**
     * Kick starts the log in process with pop-up login windows.
     */
    public static void initializeLoginProcess() {
        //@@author Chocological-reused
        //Reused from https://www.mkyong.com/java/java-properties-file-examples/ with minor modifications
        Properties loadLoginCredentials = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("loginCredentials.properties");
            loadLoginCredentials.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //@@author

        //@@author Chocological-reused
        //Reused from https://stackoverflow.com/posts/8853170/revisions with minor modifications
        final JFrame userIdentity = new JFrame();
        final JFrame userPassword = new JFrame();
        JButton button = new JButton();

        userIdentity.add(button);
        userIdentity.pack();
        userIdentity.setVisible(true);

        userPassword.add(button);
        userPassword.pack();
        userPassword.setVisible(true);
        //@@author

        //@@author Chocological-reused
        //Reused from https://www.boraji.com/how-to-iterate-properites-in-java with minor modifications
        Enumeration<String> loginUserId = (Enumeration<String>) loadLoginCredentials.propertyNames();

        String userId = JOptionPane.showInputDialog(userIdentity,
                "Please enter student matriculation ID as user ID:", null);

        // Loops through all key:value pairs in loginData until there are no more elements
        while (loginUserId.hasMoreElements()) {
            String key = loginUserId.nextElement();
            if (userId.equals(key)) {
                isUserIdExists = true;
            }
        }
        //@@author

        //@@author Chocological-reused
        //Reused from https://stackoverflow.com/posts/8853170/revisions with minor modifications
        if (!isUserIdExists) {
            final JFrame wrongUserId = new JFrame();
            wrongUserId.add(button);
            wrongUserId.pack();
            wrongUserId.setVisible(true);
            JOptionPane.showMessageDialog(null, "User ID does not exist!");
            System.exit(0);
        }

        String password = JOptionPane.showInputDialog(userPassword,
                "Enter your password:", null);
        //@@author

        //@@author Chocological-reused
        //Reused from https://www.boraji.com/how-to-iterate-properites-in-java with minor modifications
        Enumeration<String> loginPassword = (Enumeration<String>) loadLoginCredentials.propertyNames();
        // Loops through all key:value pairs in loginData until there are no more elements
        while (loginPassword.hasMoreElements()) {
            String key = loginPassword.nextElement();
            String value = loadLoginCredentials.getProperty(key);
            // If password input is correct.
            if (password.equals(value)) {
                isLoginSuccessful = true;
                break;
            }
        }
        //@@author

        //@@author Chocological-reused
        //Reused from https://stackoverflow.com/posts/8853170/revisions with minor modifications
        if (!isLoginSuccessful) {
            final JFrame wrongPassword = new JFrame();
            wrongPassword.add(button);
            wrongPassword.pack();
            wrongPassword.setVisible(true);
            JOptionPane.showMessageDialog(null, "Wrong password!");
            System.exit(0);
        }
        //@@author
    }

    public static void main(String[] args) {
        initializeLoginProcess();
    }
}
