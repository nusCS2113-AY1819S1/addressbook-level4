package seedu.address.commons.util;

import java.io.FileNotFoundException;

import javax.crypto.SecretKey;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class SecretKeyUtilTest {
    private SecretKey secretKey = SecretKeyUtil.getSecretKey("AES");

    @Test
    public void parse_invalidAlgorithm_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> SecretKeyUtil.getSecretKey("MY ALGORITHM"));
    }

    @Test
    public void parse_invalidFileNameSaveSecretKey_throwsFileNotFoundException() {
        Assert.assertThrows(FileNotFoundException.class, () -> SecretKeyUtil.saveSecretKey(secretKey, "///"));
    }
}
