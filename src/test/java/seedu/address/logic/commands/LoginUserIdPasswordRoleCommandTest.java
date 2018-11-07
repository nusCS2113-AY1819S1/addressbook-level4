package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_LOGIN_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAccounts.LOGINDETAIL_1;
import static seedu.address.testutil.TypicalAccounts.getTypicalLoginBook;
import static seedu.address.testutil.TypicalClubBudgetElements.getTypicalClubBudgetElementsBook;
import static seedu.address.testutil.TypicalFinalClubBudget.getTypicalFinalBudgetsBook;
import static seedu.address.testutil.TypicalPersons.getTypicalTaggedAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.LoginManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.login.UserIdContainsKeywordsPredicate;
import seedu.address.model.login.UserPasswordContainsKeywordsPredicate;
import seedu.address.model.login.UserRoleContainsKeywordsPredicate;
import seedu.address.model.searchhistory.SearchHistoryManager;

/**
 * Contains integration tests (interaction with the Model) for {@code LoginUserIdPasswordRoleCommand}.
 */
public class LoginUserIdPasswordRoleCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalLoginBook(), getTypicalTaggedAddressBook(),
            getTypicalClubBudgetElementsBook(), getTypicalFinalBudgetsBook(), new UserPrefs());

    private Model expectedModel = new ModelManager(getTypicalLoginBook(),
            getTypicalTaggedAddressBook(), getTypicalClubBudgetElementsBook(), getTypicalFinalBudgetsBook(),
            new UserPrefs());

    private CommandHistory commandHistory = new CommandHistory();
    private SearchHistoryManager searchHistoryManager = new SearchHistoryManager();

    @Test
    public void equals() {
        UserIdContainsKeywordsPredicate firstIdPredicate =
                new UserIdContainsKeywordsPredicate(Collections.singletonList("first"));
        UserIdContainsKeywordsPredicate secondIdPredicate =
                new UserIdContainsKeywordsPredicate(Collections.singletonList("second"));

        UserPasswordContainsKeywordsPredicate thirdPasswordPredicate =
                new UserPasswordContainsKeywordsPredicate(Collections.singletonList("third"));
        UserPasswordContainsKeywordsPredicate fourthPasswordPredicate =
                new UserPasswordContainsKeywordsPredicate(Collections.singletonList("fourth"));

        UserRoleContainsKeywordsPredicate fifthRolePredicate =
                new UserRoleContainsKeywordsPredicate(Collections.singletonList("fifth"));
        UserRoleContainsKeywordsPredicate sixthRolePredicate =
                new UserRoleContainsKeywordsPredicate(Collections.singletonList("sixth"));

        LoginUserIdPasswordRoleCommand loginFirstCommand = new LoginUserIdPasswordRoleCommand(firstIdPredicate,
                thirdPasswordPredicate, fifthRolePredicate);
        LoginUserIdPasswordRoleCommand loginSecondCommand = new LoginUserIdPasswordRoleCommand(secondIdPredicate,
                fourthPasswordPredicate, sixthRolePredicate);

        // same object -> returns true
        assertTrue(loginFirstCommand.equals(loginFirstCommand));
        assertTrue(loginSecondCommand.equals(loginSecondCommand));
        // same values -> returns true
        LoginUserIdPasswordRoleCommand loginFirstCommandCopy = new LoginUserIdPasswordRoleCommand(firstIdPredicate,
                thirdPasswordPredicate, fifthRolePredicate);
        assertTrue(loginFirstCommand.equals(loginFirstCommandCopy));

        LoginUserIdPasswordRoleCommand loginSecondCommandCopy = new LoginUserIdPasswordRoleCommand(secondIdPredicate,
                fourthPasswordPredicate, sixthRolePredicate);

        assertTrue(loginSecondCommand.equals(loginSecondCommandCopy));

        // different types -> returns false
        assertFalse(loginFirstCommand.equals(1));
        assertFalse(loginSecondCommand.equals(4));

        // null -> returns false
        assertFalse(loginFirstCommand.equals(null));
        assertFalse(loginSecondCommand.equals(null));

        // different login details -> returns false
        assertFalse(loginFirstCommand.equals(loginSecondCommand));
    }

    @Test
    public void updateFilteredAccountList_emptyStringLoginInputs_loginFailed() {
        LoginUserIdPasswordRoleCommand command = updateLoginListBasedOnPredicates(" ", " ", " ");
        assertCommandSuccess(command, model, commandHistory, MESSAGE_LOGIN_LISTED_OVERVIEW, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredLoginDetailsList());
        assertFalse(LoginManager.getIsLoginSuccessful());
    }

    @Test
    public void updateFilteredAccountList_nullLoginInputs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        LoginUserIdPasswordRoleCommand command = updateLoginListBasedOnPredicates(null,
                null, null);
    }

    @Test
    public void updateFilteredAccountList_nullLoginId_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        LoginUserIdPasswordRoleCommand command = updateLoginListBasedOnPredicates(null,
                "zaq1xsw2cde3", "member");
    }

    @Test
    public void updateFilteredAccountList_nullLoginPassword_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        LoginUserIdPasswordRoleCommand command = updateLoginListBasedOnPredicates("A1234561M",
                null, "member");
    }

    @Test
    public void updateFilteredAccountList_nullLoginRole_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        LoginUserIdPasswordRoleCommand command = updateLoginListBasedOnPredicates("A1234561M",
                "zaq1xsw2cde3", null);
    }

    @Test
    public void updateFilteredAccountList_emptyStringLoginId_loginFailed() {
        LoginUserIdPasswordRoleCommand command = updateLoginListBasedOnPredicates(" ",
                "zaq1xsw2cde3", "member");
        assertCommandSuccess(command, model, commandHistory, MESSAGE_LOGIN_LISTED_OVERVIEW, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredLoginDetailsList());
        assertFalse(LoginManager.getIsLoginSuccessful());
    }

    @Test
    public void updateFilteredAccountList_emptyStringLoginPassword_loginFailed() {
        LoginUserIdPasswordRoleCommand command = updateLoginListBasedOnPredicates("A1234561M",
                " ", "member");
        assertCommandSuccess(command, model, commandHistory, MESSAGE_LOGIN_LISTED_OVERVIEW, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredLoginDetailsList());
        assertFalse(LoginManager.getIsLoginSuccessful());
    }

    @Test
    public void updateFilteredAccountList_emptyStringLoginRole_loginFailed() {
        LoginUserIdPasswordRoleCommand command = updateLoginListBasedOnPredicates("A1234561M",
                "zaq1xsw2cde3", " ");
        assertCommandSuccess(command, model, commandHistory, MESSAGE_LOGIN_LISTED_OVERVIEW, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredLoginDetailsList());
        assertFalse(LoginManager.getIsLoginSuccessful());
    }

    @Test
    public void updateFilteredAccountList_correctLoginDetails_loginSuccessful() {
        LoginUserIdPasswordRoleCommand command = updateLoginListBasedOnPredicates("A1234561M",
                "zaq1xsw2cde3", "member");
        assertCommandSuccess(command, model, commandHistory, MESSAGE_LOGIN_LISTED_OVERVIEW, expectedModel);
        assertEquals(Collections.singletonList(LOGINDETAIL_1), model.getFilteredLoginDetailsList());
        assertTrue(LoginManager.getIsLoginSuccessful());
    }

    @Test
    public void updateFilteredAccountList_correctLoginDetailsWhitespaces_loginSuccessful() {
        LoginUserIdPasswordRoleCommand command = updateLoginListBasedOnPredicates("A1234561M   ",
                "zaq1xsw2cde3   ", "member   ");
        assertCommandSuccess(command, model, commandHistory, MESSAGE_LOGIN_LISTED_OVERVIEW, expectedModel);
        assertEquals(Collections.singletonList(LOGINDETAIL_1), model.getFilteredLoginDetailsList());
        assertTrue(LoginManager.getIsLoginSuccessful());
    }

    @Test
    public void updateFilteredAccountList_wrongUserId_loginFailed() {
        LoginUserIdPasswordRoleCommand command = updateLoginListBasedOnPredicates("A1234560M",
                "zaq1xsw2cde3", "member");
        assertCommandSuccess(command, model, commandHistory, MESSAGE_LOGIN_LISTED_OVERVIEW, expectedModel);
        assertNotEquals(Collections.singletonList(LOGINDETAIL_1), model.getFilteredLoginDetailsList());
        assertFalse(LoginManager.getIsLoginSuccessful());
    }

    @Test
    public void updateFilteredAccountList_wrongUserPassword_loginFailed() {
        LoginUserIdPasswordRoleCommand command = updateLoginListBasedOnPredicates("A1234561M",
                "1qaz2wsx3edc", "member");
        assertCommandSuccess(command, model, commandHistory, MESSAGE_LOGIN_LISTED_OVERVIEW, expectedModel);
        assertNotEquals(Collections.singletonList(LOGINDETAIL_1), model.getFilteredLoginDetailsList());
        assertFalse(LoginManager.getIsLoginSuccessful());
    }

    @Test
    public void updateFilteredAccountList_wrongUserRole_loginFailed() {
        LoginUserIdPasswordRoleCommand command = updateLoginListBasedOnPredicates("A1234561M",
                "zaq1xsw2cde3", "janitor");
        assertCommandSuccess(command, model, commandHistory, MESSAGE_LOGIN_LISTED_OVERVIEW, expectedModel);
        assertNotEquals(Collections.singletonList(LOGINDETAIL_1), model.getFilteredLoginDetailsList());
        assertFalse(LoginManager.getIsLoginSuccessful());
    }

    @Test
    public void updateFilteredAccountList_multipleLoginTriesWrongLoginDetails_loginFailed() {
        LoginUserIdPasswordRoleCommand firstCommand = updateLoginListBasedOnPredicates("A1234560M",
                "zaq1xsw2cde3", "member");
        assertCommandSuccess(firstCommand, model, commandHistory, MESSAGE_LOGIN_LISTED_OVERVIEW, expectedModel);
        assertNotEquals(Collections.singletonList(LOGINDETAIL_1), model.getFilteredLoginDetailsList());
        assertFalse(LoginManager.getIsLoginSuccessful());

        LoginUserIdPasswordRoleCommand secondCommand = updateLoginListBasedOnPredicates("A1234560M",
                "zaq1xsw2cde3", "member");
        assertCommandSuccess(secondCommand, model, commandHistory, MESSAGE_LOGIN_LISTED_OVERVIEW, expectedModel);
        assertNotEquals(Collections.singletonList(LOGINDETAIL_1), model.getFilteredLoginDetailsList());
        assertFalse(LoginManager.getIsLoginSuccessful());
    }

    private Predicate getMostUpdatedIdPredicate(Predicate idPredicate) {
        searchHistoryManager.clearSearchHistory();
        return searchHistoryManager.executeNewSearch(idPredicate);
    }

    private Predicate getMostUpdatedPasswordPredicate(Predicate passwordPredicate) {
        return searchHistoryManager.executeNewSearch(passwordPredicate);
    }

    private Predicate getMostUpdatedRolePredicate(Predicate rolePredicate) {
        return searchHistoryManager.executeNewSearch(rolePredicate);
    }

    /**
     * Updates the accounts list in {@code expectedModel} based on id, password and role predicates
     * @param userId the input user id
     * @param userPassword password predicate based on user password
     * @param userRole role predicate based on user role
     * @return a new {@code LoginUserIdPasswordRoleCommand} object that contains {@code idPredicate},
     * {@code passwordPredicate} and {@code rolePredicate}
     */
    private LoginUserIdPasswordRoleCommand updateLoginListBasedOnPredicates(String userId,
                                                                            String userPassword, String userRole) {
        UserIdContainsKeywordsPredicate idPredicate = prepareUserIdContainsKeywordsPredicate(userId);
        UserPasswordContainsKeywordsPredicate passwordPredicate = prepareUserPasswordContainsKeywordsPredicate(
                userPassword);
        UserRoleContainsKeywordsPredicate rolePredicate = prepareUserRoleContainsKeywordsPredicate(userRole);
        Predicate updatedIdPredicate = getMostUpdatedIdPredicate(idPredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedIdPredicate);
        Predicate updatedPasswordPredicate = getMostUpdatedPasswordPredicate(passwordPredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedPasswordPredicate);
        Predicate updatedRolePredicate = getMostUpdatedRolePredicate(rolePredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedRolePredicate);
        return new LoginUserIdPasswordRoleCommand(idPredicate, passwordPredicate, rolePredicate);
    }

    /**
     * Parses {@code userInput} into a {@code UserIdContainsKeywordsPredicate}.
     */
    private UserIdContainsKeywordsPredicate prepareUserIdContainsKeywordsPredicate(String userInput) {
        return new UserIdContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code UserPasswordContainsKeywordsPredicate}.
     */
    private UserPasswordContainsKeywordsPredicate prepareUserPasswordContainsKeywordsPredicate(String userInput) {
        return new UserPasswordContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code UserRoleContainsKeywordsPredicate}.
     */
    private UserRoleContainsKeywordsPredicate prepareUserRoleContainsKeywordsPredicate(String userInput) {
        return new UserRoleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
