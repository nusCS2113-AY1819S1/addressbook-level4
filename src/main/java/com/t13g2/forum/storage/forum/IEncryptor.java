package com.t13g2.forum.storage.forum;

public interface IEncryptor {
    String encryptToString(String text);

    String decryptToString(String text);

    byte[] encryptToBytes(String text);

    byte[] decryptToBytes(String text);


}
