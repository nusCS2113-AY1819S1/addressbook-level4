package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_LOGIN_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAccounts.LOGINDETAIL_1;
import static seedu.address.testutil.TypicalAccounts.getTypicalLoginBook;
import static seedu.address.testutil.TypicalPersons.getTypicalTaggedAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
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
    private Model model = new ModelManager(getTypicalLoginBook(), getTypicalTaggedAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalLoginBook(),
            getTypicalTaggedAddressBook(), new UserPrefs());

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
    public void updateFilteredAccountList_zeroParameters_loginFailed() {
        String expectedMessage = String.format(MESSAGE_LOGIN_LISTED_OVERVIEW);
        UserIdContainsKeywordsPredicate idPredicate = prepareUserIdContainsKeywordsPredicate(" ");
        UserPasswordContainsKeywordsPredicate passwordPredicate = prepareUserPasswordContainsKeywordsPredicate(" ");
        UserRoleContainsKeywordsPredicate rolePredicate = prepareUserRoleContainsKeywordsPredicate(" ");
        LoginUserIdPasswordRoleCommand command = new LoginUserIdPasswordRoleCommand(idPredicate,
                passwordPredicate, rolePredicate);
        Predicate updatedIdPredicate = getMostUpdatedIdPredicate(idPredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedIdPredicate);
        Predicate updatedPasswordPredicate = getMostUpdatedPasswordPredicate(passwordPredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedPasswordPredicate);
        Predicate updatedRolePredicate = getMostUpdatedRolePredicate(rolePredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedRolePredicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredLoginDetailsList());
    }

    @Test
    public void updateFilteredAccountList_correctLoginDetails_loginSuccessful() {
        String expectedMessage = String.format(MESSAGE_LOGIN_LISTED_OVERVIEW);
        UserIdContainsKeywordsPredicate idPredicate = prepareUserIdContainsKeywordsPredicate("A1234561M");
        UserPasswordContainsKeywordsPredicate passwordPredicate = prepareUserPasswordContainsKeywordsPredicate(
                "zaq1xsw2cde3");
        UserRoleContainsKeywordsPredicate rolePredicate = prepareUserRoleContainsKeywordsPredicate("member");
        LoginUserIdPasswordRoleCommand command = new LoginUserIdPasswordRoleCommand(idPredicate,
                passwordPredicate, rolePredicate);
        Predicate updatedIdPredicate = getMostUpdatedIdPredicate(idPredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedIdPredicate);
        Predicate updatedPasswordPredicate = getMostUpdatedPasswordPredicate(passwordPredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedPasswordPredicate);
        Predicate updatedRolePredicate = getMostUpdatedRolePredicate(rolePredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedRolePredicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(LOGINDETAIL_1), model.getFilteredLoginDetailsList());
    }

    @Test
    public void updateFilteredAccountList_wrongUserId_loginFailed() {
        String expectedMessage = String.format(MESSAGE_LOGIN_LISTED_OVERVIEW);
        UserIdContainsKeywordsPredicate idPredicate = prepareUserIdContainsKeywordsPredicate("A1234560M");
        UserPasswordContainsKeywordsPredicate passwordPredicate = prepareUserPasswordContainsKeywordsPredicate(
                "zaq1xsw2cde3");
        UserRoleContainsKeywordsPredicate rolePredicate = prepareUserRoleContainsKeywordsPredicate("member");
        LoginUserIdPasswordRoleCommand command = new LoginUserIdPasswordRoleCommand(idPredicate,
                passwordPredicate, rolePredicate);
        Predicate updatedIdPredicate = getMostUpdatedIdPredicate(idPredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedIdPredicate);
        Predicate updatedPasswordPredicate = getMostUpdatedPasswordPredicate(passwordPredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedPasswordPredicate);
        Predicate updatedRolePredicate = getMostUpdatedRolePredicate(rolePredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedRolePredicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertNotEquals(Arrays.asList(LOGINDETAIL_1), model.getFilteredLoginDetailsList());
    }

    @Test
    public void updateFilteredAccountList_wrongUserPassword_loginFailed() {
        String expectedMessage = String.format(MESSAGE_LOGIN_LISTED_OVERVIEW);
        UserIdContainsKeywordsPredicate idPredicate = prepareUserIdContainsKeywordsPredicate("A1234561M");
        UserPasswordContainsKeywordsPredicate passwordPredicate = prepareUserPasswordContainsKeywordsPredicate(
                "zwevwrvrrbre");
        UserRoleContainsKeywordsPredicate rolePredicate = prepareUserRoleContainsKeywordsPredicate("member");
        LoginUserIdPasswordRoleCommand command = new LoginUserIdPasswordRoleCommand(idPredicate,
                passwordPredicate, rolePredicate);
        Predicate updatedIdPredicate = getMostUpdatedIdPredicate(idPredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedIdPredicate);
        Predicate updatedPasswordPredicate = getMostUpdatedPasswordPredicate(passwordPredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedPasswordPredicate);
        Predicate updatedRolePredicate = getMostUpdatedRolePredicate(rolePredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedRolePredicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertNotEquals(Arrays.asList(LOGINDETAIL_1), model.getFilteredLoginDetailsList());
    }

    @Test
    public void updateFilteredAccountList_wrongUserRole_loginFailed() {
        String expectedMessage = String.format(MESSAGE_LOGIN_LISTED_OVERVIEW);
        UserIdContainsKeywordsPredicate idPredicate = prepareUserIdContainsKeywordsPredicate("A1234561M");
        UserPasswordContainsKeywordsPredicate passwordPredicate = prepareUserPasswordContainsKeywordsPredicate(
                "zaq1xsw2cde3");
        UserRoleContainsKeywordsPredicate rolePredicate = prepareUserRoleContainsKeywordsPredicate("treasurer");
        LoginUserIdPasswordRoleCommand command = new LoginUserIdPasswordRoleCommand(idPredicate,
                passwordPredicate, rolePredicate);
        Predicate updatedIdPredicate = getMostUpdatedIdPredicate(idPredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedIdPredicate);
        Predicate updatedPasswordPredicate = getMostUpdatedPasswordPredicate(passwordPredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedPasswordPredicate);
        Predicate updatedRolePredicate = getMostUpdatedRolePredicate(rolePredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedRolePredicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertNotEquals(Arrays.asList(LOGINDETAIL_1), model.getFilteredLoginDetailsList());
    }

    @Test
    public void updateFilteredAccountList_multipleLoginTriesWrongLoginDetails_loginFailed() {
        String expectedMessage = String.format(MESSAGE_LOGIN_LISTED_OVERVIEW);
        UserIdContainsKeywordsPredicate firstIdPredicate = prepareUserIdContainsKeywordsPredicate("A1234561M");
        UserPasswordContainsKeywordsPredicate firstPasswordPredicate = prepareUserPasswordContainsKeywordsPredicate(
                "zaq1xsw2cde3");
        UserRoleContainsKeywordsPredicate firstRolePredicate = prepareUserRoleContainsKeywordsPredicate("treasurer");
        LoginUserIdPasswordRoleCommand firstCommand = new LoginUserIdPasswordRoleCommand(firstIdPredicate,
                firstPasswordPredicate, firstRolePredicate);
        Predicate updatedFirstIdPredicate = getMostUpdatedIdPredicate(firstIdPredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedFirstIdPredicate);
        Predicate updatedFirstPasswordPredicate = getMostUpdatedPasswordPredicate(firstPasswordPredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedFirstPasswordPredicate);
        Predicate updatedFirstRolePredicate = getMostUpdatedRolePredicate(firstRolePredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedFirstRolePredicate);
        assertCommandSuccess(firstCommand, model, commandHistory, expectedMessage, expectedModel);
        assertNotEquals(Arrays.asList(LOGINDETAIL_1), model.getFilteredLoginDetailsList());

        UserIdContainsKeywordsPredicate secondIdPredicate = prepareUserIdContainsKeywordsPredicate("A1234561M");
        UserPasswordContainsKeywordsPredicate secondPasswordPredicate = prepareUserPasswordContainsKeywordsPredicate(
                "zaq1xsw2cde3");
        UserRoleContainsKeywordsPredicate secondRolePredicate = prepareUserRoleContainsKeywordsPredicate("treasurer");
        LoginUserIdPasswordRoleCommand secondCommand = new LoginUserIdPasswordRoleCommand(secondIdPredicate,
                secondPasswordPredicate, secondRolePredicate);
        Predicate updatedSecondIdPredicate = getMostUpdatedIdPredicate(secondIdPredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedSecondIdPredicate);
        Predicate updatedSecondPasswordPredicate = getMostUpdatedPasswordPredicate(secondPasswordPredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedSecondPasswordPredicate);
        Predicate updatedSecondRolePredicate = getMostUpdatedRolePredicate(secondRolePredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedSecondRolePredicate);
        assertCommandSuccess(secondCommand, model, commandHistory, expectedMessage, expectedModel);
        assertNotEquals(Arrays.asList(LOGINDETAIL_1), model.getFilteredLoginDetailsList());
    }

    @Test
    public void updateFilteredAccountList_multipleLoginTriesFirstWrongSecondCorrect_loginSuccessful() {
        String expectedMessage = String.format(MESSAGE_LOGIN_LISTED_OVERVIEW);
        UserIdContainsKeywordsPredicate firstIdPredicate = prepareUserIdContainsKeywordsPredicate("A1234561M");
        UserPasswordContainsKeywordsPredicate firstPasswordPredicate = prepareUserPasswordContainsKeywordsPredicate(
                "zaq1xsw2cde3");
        UserRoleContainsKeywordsPredicate firstRolePredicate = prepareUserRoleContainsKeywordsPredicate("treasurer");
        LoginUserIdPasswordRoleCommand firstCommand = new LoginUserIdPasswordRoleCommand(firstIdPredicate,
                firstPasswordPredicate, firstRolePredicate);
        Predicate updatedFirstIdPredicate = getMostUpdatedIdPredicate(firstIdPredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedFirstIdPredicate);
        Predicate updatedFirstPasswordPredicate = getMostUpdatedPasswordPredicate(firstPasswordPredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedFirstPasswordPredicate);
        Predicate updatedFirstRolePredicate = getMostUpdatedRolePredicate(firstRolePredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedFirstRolePredicate);
        assertCommandSuccess(firstCommand, model, commandHistory, expectedMessage, expectedModel);
        assertNotEquals(Arrays.asList(LOGINDETAIL_1), model.getFilteredLoginDetailsList());

        UserIdContainsKeywordsPredicate secondIdPredicate = prepareUserIdContainsKeywordsPredicate("A1234561M");
        UserPasswordContainsKeywordsPredicate secondPasswordPredicate = prepareUserPasswordContainsKeywordsPredicate(
                "zaq1xsw2cde3");
        UserRoleContainsKeywordsPredicate secondRolePredicate = prepareUserRoleContainsKeywordsPredicate("member");
        LoginUserIdPasswordRoleCommand secondCommand = new LoginUserIdPasswordRoleCommand(secondIdPredicate,
                secondPasswordPredicate, secondRolePredicate);
        Predicate updatedSecondIdPredicate = getMostUpdatedIdPredicate(secondIdPredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedSecondIdPredicate);
        Predicate updatedSecondPasswordPredicate = getMostUpdatedPasswordPredicate(secondPasswordPredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedSecondPasswordPredicate);
        Predicate updatedSecondRolePredicate = getMostUpdatedRolePredicate(secondRolePredicate);
        expectedModel.updateFilteredLoginDetailsList(updatedSecondRolePredicate);
        assertCommandSuccess(secondCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(LOGINDETAIL_1), model.getFilteredLoginDetailsList());
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
