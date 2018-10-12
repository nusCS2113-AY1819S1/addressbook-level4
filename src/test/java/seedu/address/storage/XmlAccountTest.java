package seedu.address.storage;

import static seedu.address.storage.XmlAccount.MISSING_ACCOUNT_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalAccounts.LOGINDETAIL2;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.login.UserId;
import seedu.address.model.login.UserPassword;
import seedu.address.testutil.Assert;

public class XmlAccountTest {

    private static final String INVALID_USERID = "AA234567M";
    private static final String INVALID_USERPASSWORD = "zaq1 xsw2 cde3";

    private static final String VALID_USERID = LOGINDETAIL2.getUserId().toString();
    private static final String VALID_USERPASSWORD = LOGINDETAIL2.getUserPassword().toString();

    @Test
    public void toModelType_invalidUserId_throwsIllegalValueException() {
        XmlAccount account =
                new XmlAccount(INVALID_USERID, VALID_USERPASSWORD);
        String expectedMessage = UserId.MESSAGE_USERID_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }

    @Test
    public void toModelType_nullUserId_throwsIllegalValueException() {
        XmlAccount account = new XmlAccount(null, VALID_USERPASSWORD);
        String expectedMessage = String.format(MISSING_ACCOUNT_FIELD_MESSAGE_FORMAT, UserId.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }

    @Test
    public void toModelType_invalidUserPassword_throwsIllegalValueException() {
        XmlAccount account =
                new XmlAccount("A1234567M", INVALID_USERPASSWORD);
        String expectedMessage = UserPassword.MESSAGE_USERPASSWORD_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }

    @Test
    public void toModelType_nullUserPassword_throwsIllegalValueException() {
        XmlAccount account = new XmlAccount("A1234567M", null);
        String expectedMessage = String.format(MISSING_ACCOUNT_FIELD_MESSAGE_FORMAT,
                UserPassword.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }

}
