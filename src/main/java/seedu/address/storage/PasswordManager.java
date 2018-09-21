package seedu.address.storage;

import seedu.address.commons.util.Passwords;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

public class PasswordManager {
    private PasswordManager () {}

    public static String authenticate (String password, String salt) {
        try {
            byte[] hash = Files.readAllBytes(new File("password.txt").toPath());
            boolean isAuthenticated = Passwords.isExpectedPassword(password.toCharArray(),
                    salt.getBytes(),
                    hash);

            if (isAuthenticated) {
                return "Authenticated";
            } else {
                return "Incorrect password or username";
            }

        } catch (IOException e) {
            return e.getMessage();
        }

    }


    public static String storePassword (String password, String salt) {
        byte hash[] = Passwords.hash(password.toCharArray(), salt.getBytes());

        try {
            FileOutputStream fos = new FileOutputStream("password.txt");
            fos.write(hash);
            fos.close();
            return hash.toString();
        } catch (IOException e) {
            return e.getMessage();
        }

    }


}
