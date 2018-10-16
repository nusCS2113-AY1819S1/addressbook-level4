package seedu.address.model.login;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_ACCOUNT2;
import static seedu.address.testutil.TypicalAccounts.LOGINDETAIL1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.login.exceptions.DuplicateAccountException;
import seedu.address.testutil.AccountBuilder;

public class UniqueAccountListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueAccountList uniqueAccountList = new UniqueAccountList();

    @Test
    public void contains_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAccountList.contains(null);
    }

    @Test
    public void contains_accountNotInList_returnsFalse() {
        assertFalse(uniqueAccountList.contains(LOGINDETAIL1));
    }

    @Test
    public void contains_accountInList_returnsTrue() {
        uniqueAccountList.add(LOGINDETAIL1);
        assertTrue(uniqueAccountList.contains(LOGINDETAIL1));
    }

    @Test
    public void contains_accountWithSameUserIdInList_returnsTrue() {
        uniqueAccountList.add(LOGINDETAIL1);
        LoginDetails editedLoginDetail1 = new AccountBuilder(LOGINDETAIL1).withUserPassword(VALID_PASSWORD_ACCOUNT2)
                .build();
        assertTrue(uniqueAccountList.contains(editedLoginDetail1));
    }

    @Test
    public void add_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAccountList.add(null);
    }

    @Test
    public void add_duplicateAccount_throwsDuplicateAccountException() {
        uniqueAccountList.add(LOGINDETAIL1);
        thrown.expect(DuplicateAccountException.class);
        uniqueAccountList.add(LOGINDETAIL1);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueAccountList.asUnmodifiableObservableList().remove(0);
    }
}
