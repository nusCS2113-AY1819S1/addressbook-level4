package com.t13g2.forum.storage.forum;
//@@author Meowzz95

/**
 * Provides encryption algorithm
 */
public interface IEncryptor {
    /**
     * Encrypts given string to an encrypted string
     *
     * @param text
     * @return encrypted string
     */
    String encryptToString(String text);

    /**
     * Decrypts given string to original string
     * @param text
     * @return original string
     */
    String decryptToString(String text);

    /**
     * Encrypts given string to an encrypted byte array
     * @param text
     * @return byte array
     */
    byte[] encryptToBytes(String text);

    /**
     * Decrypts given string to original byte array
     * @param text
     * @return original byte array
     */
    byte[] decryptToBytes(String text);


}
