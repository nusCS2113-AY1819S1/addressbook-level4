package com.t13g2.forum.storage.forum;

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

    @Override
    public String encryptToString(String text) {
        return new String(this.encryptToBytes(text));
    }

    @Override
    public String decryptToString(String text) {
        return new String(this.decryptToBytes(text));
    }

    @Override
    public byte[] encryptToBytes(String text) {
        return this.orAlgorithm(text);
    }

    @Override
    public byte[] decryptToBytes(String text) {
        return this.orAlgorithm(text);
    }

    private byte[] orAlgorithm(String text) {
        byte[] bytes = text.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (bytes[i] ^ this.keyBytes[this.getKeyBytesIndex(i)]);
        }
        return bytes;
    }

    private int getKeyBytesIndex(int index) {
        return index % this.keyBytes.length;
    }
}
