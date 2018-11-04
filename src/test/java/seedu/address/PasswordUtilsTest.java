package seedu.address;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.authentication.PasswordUtils;


public class PasswordUtilsTest {
    @Test
    public void generateSecurePasswordTest() {
        String expected = "Gcf70h4aWQ1T9NMxE03XM3nq3nCmFGihnO4xMzHMgP0=";
        String actual = PasswordUtils.generateSecurePassword("123");
        assertEquals(expected, actual);

        expected = "HhaNvzTsVYwS/x/zbYXlLOE3ETMXQgllqrDaJY9PD/U=";
        actual = PasswordUtils.generateSecurePassword ("myPassword123");
        assertEquals(expected, actual);

        expected = "Xk2lFYYrsGuPMF6iOp/AwvLyvR17BWMcpaNcyo6ziGo=";
        actual = PasswordUtils.generateSecurePassword ("ePYHc~dS*)8$+V-");
        assertEquals(expected, actual);
    }

    @Test
    public void verifyUserPasswordTest() {
        //expect to pass
        boolean result = PasswordUtils.verifyUserPassword ("myPassword123",
                                                "HhaNvzTsVYwS/x/zbYXlLOE3ETMXQgllqrDaJY9PD/U=");
        assertTrue (result);

        result = PasswordUtils.verifyUserPassword ("ePYHc~dS*)8$+V-", "Xk2lFYYrsGuPMF6iOp/AwvLyvR17BWMcpaNcyo6ziGo=");
        assertTrue (result);

        //expected to fail
        result = PasswordUtils.verifyUserPassword ("", "Xk2lFYYrsGuPMF6iOp/AwvLyvR17BWMcpaNcyo6ziGo=");
        assertFalse(result);

        result = PasswordUtils.verifyUserPassword ("ePYHc~dS*)8$+V-", "");
        assertFalse(result);

        result = PasswordUtils.verifyUserPassword ("ePYHc~dS*)8$+V-", "Xk2lFYYrsGuPMF6iOp/AwvLyvR17BWMcpaNcyo6ziGo");
        assertFalse(result);
    }

}
