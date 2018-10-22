package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.LoginBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyLoginBook;
import seedu.address.model.budgetelements.ClubBudgetElements;
import seedu.address.model.clubbudget.FinalClubBudget;
import seedu.address.model.login.LoginDetails;
import seedu.address.model.person.Person;
import seedu.address.model.searchhistory.SearchHistoryManager;
import seedu.address.testutil.AccountBuilder;

public class CreateAccountCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CreateAccountCommand(null);
    }

    @Test
    public void execute_accountAcceptedByModel_addSuccessful() throws Exception {
        CreateAccountCommandTest.ModelStubAcceptingAccountAdded modelStub = new CreateAccountCommandTest
                                                                            .ModelStubAcceptingAccountAdded();
        LoginDetails validAccount = new AccountBuilder().build();

        CommandResult commandResult = new CreateAccountCommand(validAccount)
                                                               .execute(modelStub, commandHistory);

        assertEquals(String.format(CreateAccountCommand.MESSAGE_SUCCESS, validAccount), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validAccount), modelStub.accountsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateAccount_throwsCommandException() throws Exception {
        LoginDetails validAccount = new AccountBuilder().build();
        CreateAccountCommand createAccountCommand = new CreateAccountCommand(validAccount);
        ModelStub modelStub = new ModelStubWithAccount(validAccount);

        thrown.expect(CommandException.class);
        thrown.expectMessage(CreateAccountCommand.MESSAGE_DUPLICATE_ACCOUNT);
        createAccountCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        LoginDetails loginDetailOne = new AccountBuilder().withUserId("A1234567M").build();
        LoginDetails loginDetailTwo = new AccountBuilder().withUserId("A1234568M").build();
        CreateAccountCommand createAccountOneCommand = new CreateAccountCommand(loginDetailOne);
        CreateAccountCommand createAccountTwoCommand = new CreateAccountCommand(loginDetailTwo);

        // same object -> returns true
        assertTrue(createAccountOneCommand.equals(createAccountOneCommand));

        // same values -> returns true
        CreateAccountCommand createAccountOneCommandCopy = new CreateAccountCommand(loginDetailOne);
        assertTrue(createAccountOneCommand.equals(createAccountOneCommandCopy));

        // different types -> returns false
        assertFalse(createAccountOneCommand.equals(1));

        // null -> returns false
        assertFalse(createAccountOneCommand.equals(null));

        // different person -> returns false
        assertFalse(createAccountOneCommand.equals(createAccountTwoCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void createAccount(LoginDetails loginDetails) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAccount(LoginDetails credentials) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyLoginBook getLoginBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<LoginDetails> getFilteredLoginDetailsList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<ClubBudgetElements> getFilteredClubsList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<FinalClubBudget> getFilteredClubBudgetsList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredLoginDetailsList(Predicate<LoginDetails> predicate) {

        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredClubBudgetsList(Predicate<FinalClubBudget> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasClub(ClubBudgetElements club) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void addClub(ClubBudgetElements club) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasClubBudget(FinalClubBudget clubBudget) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void addClubBudget(FinalClubBudget clubBudget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public SearchHistoryManager getSearchHistoryManager() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single account.
     */
    private class ModelStubWithAccount extends CreateAccountCommandTest.ModelStub {
        private final LoginDetails loginDetails;

        ModelStubWithAccount(LoginDetails loginDetails) {
            requireNonNull(loginDetails);
            this.loginDetails = loginDetails;
        }

        @Override
        public boolean hasAccount(LoginDetails loginDetails) {
            requireNonNull(loginDetails);
            return this.loginDetails.isSameAccount(loginDetails);
        }
    }

    /**
     * A Model stub that always accept the account being added.
     */
    private class ModelStubAcceptingAccountAdded extends CreateAccountCommandTest.ModelStub {
        final ArrayList<LoginDetails> accountsAdded = new ArrayList<>();

        @Override
        public boolean hasAccount(LoginDetails loginDetails) {
            requireNonNull(loginDetails);
            return accountsAdded.stream().anyMatch(loginDetails::isSameAccount);
        }

        @Override
        public void createAccount(LoginDetails loginDetails) {
            requireNonNull(loginDetails);
            accountsAdded.add(loginDetails);
        }

        @Override
        public ReadOnlyLoginBook getLoginBook() {
            return new LoginBook();
        }
    }
}
