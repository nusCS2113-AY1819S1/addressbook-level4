package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalAccounts.LOGINDETAIL5;
import static seedu.address.testutil.TypicalAccounts.getTypicalLoginBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.login.LoginDetails;
import seedu.address.model.login.exceptions.DuplicateAccountException;
import seedu.address.testutil.AccountBuilder;

public class LoginBookTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final LoginBook loginBook = new LoginBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), loginBook.getLoginDetailsList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        loginBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyLoginBook_replacesData() {
        LoginBook newData = getTypicalLoginBook();
        loginBook.resetData(newData);
        assertEquals(newData, loginBook);
    }
    @Test
    public void resetData_withDuplicateAccounts_throwsDuplicateAccountException() {
        // Two persons with the same identity fields
        LoginDetails editedLoginDetails = new AccountBuilder(LOGINDETAIL5).withUserPassword("1qaz2wsx3edc").build();
        List<LoginDetails> newAccounts = Arrays.asList(LOGINDETAIL5, editedLoginDetails);
        LoginBookStub newData = new LoginBookStub(newAccounts);

        thrown.expect(DuplicateAccountException.class);
        loginBook.resetData(newData);
    }

    @Test
    public void hasAccount_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        loginBook.hasAccount(null);
    }

    @Test
    public void hasAccount_accountNotInLoginBook_returnsFalse() {
        assertFalse(loginBook.hasAccount(LOGINDETAIL5));
    }

    @Test
    public void hasAccount_accountInLoginBook_returnsTrue() {
        loginBook.createAccount(LOGINDETAIL5);
        assertTrue(loginBook.hasAccount(LOGINDETAIL5));
    }

    @Test
    public void hasAccount_accountWithSameUserIdFieldInLoginBook_returnsTrue() {
        loginBook.createAccount(LOGINDETAIL5);
        LoginDetails editedLoginDetails = new AccountBuilder(LOGINDETAIL5).withUserPassword("1qaz2wsx3edc").build();
        assertTrue(loginBook.hasAccount(editedLoginDetails));
    }

    @Test
    public void getAccountList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        loginBook.getLoginDetailsList().remove(0);
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class LoginBookStub implements ReadOnlyLoginBook {
        private final ObservableList<LoginDetails> accounts = FXCollections.observableArrayList();

        LoginBookStub(Collection<LoginDetails> loginDetails) {
            this.accounts.setAll(loginDetails);
        }

        @Override
        public ObservableList<LoginDetails> getLoginDetailsList() {
            return accounts;
        }
    }
}
