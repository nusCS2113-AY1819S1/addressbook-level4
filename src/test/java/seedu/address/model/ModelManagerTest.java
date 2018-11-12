package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalAccounts.LOGINDETAIL_1;
import static seedu.address.testutil.TypicalAccounts.LOGINDETAIL_2;
import static seedu.address.testutil.TypicalClubBudgetElements.CLUB_1;
import static seedu.address.testutil.TypicalClubBudgetElements.CLUB_2;
import static seedu.address.testutil.TypicalClubBudgetElements.COMPUTING_CLUB;
import static seedu.address.testutil.TypicalFinalClubBudget.CLUB_BUDGET_1;
import static seedu.address.testutil.TypicalFinalClubBudget.CLUB_BUDGET_2;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.searchhistory.exceptions.EmptyHistoryException;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.ClubBudgetElementsBookBuilder;
import seedu.address.testutil.FinalBudgetsBookBuilder;
import seedu.address.testutil.LoginBookBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasAccount_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasAccount(null);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasAccount_accountInLoginBook_returnsTrue() {
        modelManager.createAccount(LOGINDETAIL_1);
        assertTrue(modelManager.hasAccount(LOGINDETAIL_1));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasClubBudgetElements_clubInClubBudgetElementsBook_returnsTrue() {
        modelManager.addClub(COMPUTING_CLUB);
        assertTrue(modelManager.hasClub(COMPUTING_CLUB));
    }

    @Test
    public void hasFinalClubBudget_budgetInFinalBudgetsBook_returnsTrue() {
        modelManager.addClubBudget(CLUB_BUDGET_1);
        assertTrue(modelManager.hasClubBudget(CLUB_BUDGET_1));
    }

    @Test
    public void getFilteredAccountList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredLoginDetailsList().remove(0);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    @Test
    public void revertLastSearch_emptySearchHistory_throwsEmptyHistoryException() {
        thrown.expect(EmptyHistoryException.class);
        modelManager.revertLastSearch();
    }

    @Test
    public void equals() {
        LoginBook loginBook = new LoginBookBuilder().withLoginDetails(LOGINDETAIL_1)
                .withLoginDetails(LOGINDETAIL_2).build();
        LoginBook differentLoginBook = new LoginBook();
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        ClubBudgetElementsBook clubBudgetElementsBook = new ClubBudgetElementsBookBuilder()
                .withClubBudgetElements(CLUB_1).withClubBudgetElements(CLUB_2).build();
        ClubBudgetElementsBook differentClubBudgetElementsBook = new ClubBudgetElementsBook();
        FinalBudgetsBook finalBudgetsBook = new FinalBudgetsBookBuilder().withFinalClubBudget(CLUB_BUDGET_1)
                .withFinalClubBudget(CLUB_BUDGET_2).build();
        FinalBudgetsBook differentFinalBudgetsBook = new FinalBudgetsBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(loginBook, addressBook, clubBudgetElementsBook, finalBudgetsBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(loginBook, addressBook, clubBudgetElementsBook,
                finalBudgetsBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentLoginBook, differentAddressBook,
                differentClubBudgetElementsBook, differentFinalBudgetsBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");

        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(loginBook, addressBook, clubBudgetElementsBook,
                finalBudgetsBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.resetSearchHistoryToInitialState();

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(loginBook, addressBook, clubBudgetElementsBook,
                finalBudgetsBook, differentUserPrefs)));
    }
}
