package seedu.address.security;

import java.io.Serializable;

/**
 * A serializable class that contains username and hashed password
 */
public class AccountCredential implements Serializable {
    private String userName;
    private String hashedPassword;



    public AccountCredential(String userName, String Password) {
        this.userName = userName;
        this.hashedPassword = hash(Password);
    }

    /**
     * Compares
     * @param password by hashing it first then comparing with stored hashedPassword
     * @return boolean
     */
    public boolean passwordIsValid(String password) {
        String inputHashed = hash(password);
        if (inputHashed.equals(hashedPassword)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Converts
     * @param password into
     * @return a hashed password
     */
    private String hash(String password) {
        //TODO FIND A GOOD HASHING LIBRARY
        return password;
    }
}
