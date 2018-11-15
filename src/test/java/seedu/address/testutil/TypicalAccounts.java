package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_ACCOUNT_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_ACCOUNT_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_ACCOUNT_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_ACCOUNT_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_ACCOUNT_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_ACCOUNT_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.LoginBook;
import seedu.address.model.login.LoginDetails;

/**
 * A utility class containing a list of {@code LoginDetails} objects to be used in tests.
 */
public class TypicalAccounts {
    public static final LoginDetails LOGINDETAIL_1 = new AccountBuilder().withUserId("A1234561M")
            .withUserPassword("zaq1xsw2cde3").withUserRole("member").build();
    public static final LoginDetails LOGINDETAIL_2 = new AccountBuilder().withUserId("A1234562M")
            .withUserPassword("1qaz2wsx3edc").withUserRole("member").build();
    public static final LoginDetails LOGINDETAIL_3 = new AccountBuilder().withUserId("A1234563M")
            .withUserPassword("1qaz2wsx3edc").withUserRole("member").build();
    public static final LoginDetails LOGINDETAIL_4 = new AccountBuilder().withUserId("A1234564M")
            .withUserPassword("zaq1xsw2cde3").withUserRole("member").build();
    public static final LoginDetails LOGINDETAIL_5 = new AccountBuilder().withUserId("A1234565M")
            .withUserPassword("zaq1xsw2cde3").withUserRole("member").build();

    public static final LoginDetails ACCOUNT_1 = new AccountBuilder().withUserId(VALID_ID_ACCOUNT_1)
            .withUserPassword(VALID_PASSWORD_ACCOUNT_1).withUserRole(VALID_ROLE_ACCOUNT_1).build();
    public static final LoginDetails ACCOUNT_2 = new AccountBuilder().withUserId(VALID_ID_ACCOUNT_2)
            .withUserPassword(VALID_PASSWORD_ACCOUNT_2).withUserRole(VALID_ROLE_ACCOUNT_2).build();

    private TypicalAccounts() {} // prevents instantiation

    /**
     * Returns an {@code LoginBook} with all the typical persons.
     */
    public static LoginBook getTypicalLoginBook() {
        LoginBook lb = new LoginBook();
        for (LoginDetails loginDetails : getTypicalAccounts()) {
            lb.createAccount(loginDetails);
        }
        return lb;
    }

    public static List<LoginDetails> getTypicalAccounts() {
        return new ArrayList<>(Arrays.asList(LOGINDETAIL_1, LOGINDETAIL_2, LOGINDETAIL_3, LOGINDETAIL_4));
    }
}
