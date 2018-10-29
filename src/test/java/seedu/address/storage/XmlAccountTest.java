package seedu.address.storage;

import static seedu.address.storage.XmlAccount.MISSING_ACCOUNT_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalAccounts.LOGINDETAIL_2;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.LoginManager;
import seedu.address.model.login.UserId;
import seedu.address.model.login.UserPassword;
import seedu.address.model.login.UserRole;
import seedu.address.testutil.Assert;

public class XmlAccountTest {

    private static final String INVALID_USERID = "AA234567M";
    private static final String INVALID_USERPASSWORD = "zaq1 xsw2 cde3";
    private static final String INVALID_USERROLE = "janitor";

    private static final String VALID_USERID = LOGINDETAIL_2.getUserId().toString();
    private static final String VALID_USERPASSWORD = LOGINDETAIL_2.getUserPassword().toString();
    private static final String VALID_USERROLE = LOGINDETAIL_2.getUserRole().toString();

    @Test
    public void toModelType_nullUserId_throwsIllegalValueException() {
        LoginManager.setIsCurrentlyTesting(true);
        XmlAccount account = new XmlAccount(null, VALID_USERPASSWORD, VALID_USERROLE);
        String expectedMessage = String.format(MISSING_ACCOUNT_FIELD_MESSAGE_FORMAT, UserId.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
        LoginManager.setIsCurrentlyTesting(false);
    }

    @Test
    public void toModelType_nullUserPassword_throwsIllegalValueException() {
        LoginManager.setIsCurrentlyTesting(true);
        XmlAccount account = new XmlAccount("A1234567M", null, VALID_USERROLE);
        String expectedMessage = String.format(MISSING_ACCOUNT_FIELD_MESSAGE_FORMAT,
                UserPassword.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
        LoginManager.setIsCurrentlyTesting(false);
    }

    @Test
    public void toModelType_nullUserRole_throwsIllegalValueException() {
        LoginManager.setIsCurrentlyTesting(true);
        XmlAccount account = new XmlAccount("A1234567M", VALID_USERPASSWORD, null);
        String expectedMessage = String.format(MISSING_ACCOUNT_FIELD_MESSAGE_FORMAT,
                UserRole.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
        LoginManager.setIsCurrentlyTesting(false);
    }

}
