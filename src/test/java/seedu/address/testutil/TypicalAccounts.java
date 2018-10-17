package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_ACCOUNT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_ACCOUNT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_ACCOUNT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_ACCOUNT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_ACCOUNT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_ACCOUNT2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.LoginBook;
import seedu.address.model.login.LoginDetails;

/**
 * A utility class containing a list of {@code LoginDetails} objects to be used in tests.
 */
public class TypicalAccounts {
    public static final LoginDetails LOGINDETAIL1 = new AccountBuilder().withUserId("A1234565M")
            .withUserPassword("zaq1xsw2cde3").withUserRole("member").build();
    public static final LoginDetails LOGINDETAIL2 = new AccountBuilder().withUserId("A1234566M")
            .withUserPassword("1qaz2wsx3edc").withUserRole("member").build();
    public static final LoginDetails LOGINDETAIL3 = new AccountBuilder().withUserId("A1234565M")
            .withUserPassword("1qaz2wsx3edc").withUserRole("member").build();
    public static final LoginDetails LOGINDETAIL4 = new AccountBuilder().withUserId("A1234566M")
            .withUserPassword("zaq1xsw2cde3").withUserRole("member").build();
    public static final LoginDetails LOGINDETAIL5 = new AccountBuilder().withUserId("A1234567M")
            .withUserPassword("zaq1xsw2cde3").withUserRole("member").build();

    public static final LoginDetails ACCOUNT1 = new AccountBuilder().withUserId(VALID_ID_ACCOUNT1)
            .withUserPassword(VALID_PASSWORD_ACCOUNT1).withUserRole(VALID_ROLE_ACCOUNT1).build();
    public static final LoginDetails ACCOUNT2 = new AccountBuilder().withUserId(VALID_ID_ACCOUNT2)
            .withUserPassword(VALID_PASSWORD_ACCOUNT2).withUserRole(VALID_ROLE_ACCOUNT2).build();

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
        return new ArrayList<>(Arrays.asList(LOGINDETAIL1, LOGINDETAIL2, LOGINDETAIL3, LOGINDETAIL4));
    }
}
