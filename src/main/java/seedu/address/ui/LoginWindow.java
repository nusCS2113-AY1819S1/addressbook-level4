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

    public static boolean isLoginSuccessful = false;
    public static boolean isUserIdExists = false;

    public static void initializeLoginProcess() {
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

        final JFrame userIdentity = new JFrame();
        final JFrame userPassword = new JFrame();
        JButton button = new JButton();

        userIdentity.add(button);
        userIdentity.pack();
        userIdentity.setVisible(true);

        userPassword.add(button);
        userPassword.pack();
        userPassword.setVisible(true);

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

        if(!isUserIdExists) {
            final JFrame wrongUserId = new JFrame();
            wrongUserId.add(button);
            wrongUserId.pack();
            wrongUserId.setVisible(true);
            JOptionPane.showMessageDialog(null, "User ID does not exist!");
            System.exit(0);
        }

        String password = JOptionPane.showInputDialog(userPassword,
                "Enter your password:", null);

        Enumeration<String> loginPassword = (Enumeration<String>) loadLoginCredentials.propertyNames();
        // Loops through all key:value pairs in loginData until there are no more elements
        while (loginPassword.hasMoreElements()) {
            String key = loginPassword.nextElement();
            String value = loadLoginCredentials.getProperty(key);
            // If password input is correct.
            if (password.equals(value)) {
                System.out.println("Correct password! Login successful!");
                isLoginSuccessful = true;
                break;
            }
        }

        if (!isLoginSuccessful) {
            final JFrame wrongPassword = new JFrame();
            wrongPassword.add(button);
            wrongPassword.pack();
            wrongPassword.setVisible(true);
            JOptionPane.showMessageDialog(null, "Wrong password!");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        initializeLoginProcess();
    }
}
