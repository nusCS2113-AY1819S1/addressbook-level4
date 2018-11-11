package com.t13g2.forum.storage.forum;
//@@author Meowzz95

/**
 * Provides a simple OR encryption
 */
public class SimpleEncryptor implements IEncryptor {
    private static final String DEFAULT_KEY = "Iamastupidencryptor";
    private String key;
    private byte[] keyBytes;

    public SimpleEncryptor(String key) {
        this.key = key;
        this.keyBytes = key.getBytes();
    }

    public SimpleEncryptor() {
        this(DEFAULT_KEY);
    }

    /**
     * Encrypts given string to an encrypted string
     *
     * @param text
     * @return encrypted string
     */
    @Override
    public String encryptToString(String text) {
        return new String(this.encryptToBytes(text));
    }

    /**
     * Decrypts given string to original string
     * @param text
     * @return original string
     */
    @Override
    public String decryptToString(String text) {
        return new String(this.decryptToBytes(text));
    }

    /**
     * Encrypts given string to an encrypted byte array
     * @param text
     * @return byte array
     */
    @Override
    public byte[] encryptToBytes(String text) {
        return this.orAlgorithm(text);
    }

    /**
     * Decrypts given string to original byte array
     * @param text
     * @return original byte array
     */
    @Override
    public byte[] decryptToBytes(String text) {
        return this.orAlgorithm(text);
    }

    /**
     * Uses a simple OR encryption to encrypt the given text
     * @param text
     * @return encrypted byte array
     */
    private byte[] orAlgorithm(String text) {
        byte[] bytes = text.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (bytes[i] ^ this.keyBytes[this.getKeyBytesIndex(i)]);
        }
        return bytes;
    }

    /**
     * Calculates which bit to do OR operation with when the text to be
     * encrypted is longer than the key
     * @param index
     * @return index in key byte array
     */
    private int getKeyBytesIndex(int index) {
        return index % this.keyBytes.length;
    }
}
