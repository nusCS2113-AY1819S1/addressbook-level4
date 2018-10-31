package com.t13g2.forum.storage;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Assert;
import org.junit.Test;

import com.t13g2.forum.storage.forum.SimpleEncryptor;

public class SimpleEncryptorTest {

    @Test
    public void testString() {

        String original = "hahahaha I AM A TEST MESSAGE";
        SimpleEncryptor encryptor = new SimpleEncryptor();
        String encrypted = encryptor.encryptToString(original);

        Assert.assertNotEquals(original, encrypted);


        String decrypted = encryptor.decryptToString(encrypted);
        Assert.assertEquals(original, decrypted);

    }

    @Test
    public void testBytes() {
        String original = "hahahaha I AM A TEST MESSAGE";
        SimpleEncryptor encryptor = new SimpleEncryptor();
        byte[] encrypted = encryptor.encryptToBytes(original);

        Assert.assertThat(original, IsNot.not(IsEqual.equalTo(encrypted)));

        SimpleEncryptor decryptor = new SimpleEncryptor();
        byte[] decrypted = decryptor.decryptToBytes(new String(encrypted));
        Assert.assertArrayEquals(original.getBytes(), decrypted);

    }
}
