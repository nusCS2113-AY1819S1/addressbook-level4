package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ClubBudgetElementsBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;

import seedu.address.model.ReadOnlyClubBudgetElementsBook;
import seedu.address.model.ReadOnlyFinalBudgetBook;
import seedu.address.model.ReadOnlyLoginBook;
import seedu.address.model.budgetelements.ClubBudgetElements;
import seedu.address.model.clubbudget.FinalClubBudget;
import seedu.address.model.login.LoginDetails;

import seedu.address.model.person.Person;

import seedu.address.model.searchhistory.KeywordType;
import seedu.address.model.searchhistory.ReadOnlyKeywordsRecord;
import seedu.address.model.searchhistory.exceptions.EmptyHistoryException;
import seedu.address.testutil.ClubBudgetElementsBuilder;

public class BudgetCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullClubBudgetElements_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new BudgetCommand(null);
    }

    @Test
    public void execute_clubBudgetElementsAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingClubBudgetElementsAdded modelStub = new ModelStubAcceptingClubBudgetElementsAdded();
        ClubBudgetElements validClubBudgetElements = new ClubBudgetElementsBuilder().build();

        CommandResult commandResult = new BudgetCommand(validClubBudgetElements).execute(modelStub, commandHistory);

        assertEquals(String.format(BudgetCommand.MESSAGE_SUCCESS, validClubBudgetElements),
                commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validClubBudgetElements), modelStub.clubBudgetElementsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateClubBudgetElements_throwsCommandException() throws Exception {
        ClubBudgetElements validClubBudgetElements = new ClubBudgetElementsBuilder().build();
        BudgetCommand budgetCommand = new BudgetCommand(validClubBudgetElements);
        ModelStub modelStub = new ModelStubWithClubBudgetElements(validClubBudgetElements);

        thrown.expect(CommandException.class);
        thrown.expectMessage(BudgetCommand.MESSAGE_DUPLICATE_CLUB);
        budgetCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        ClubBudgetElements computingClub = new ClubBudgetElementsBuilder().withClubName("Computing Club").build();
        ClubBudgetElements eceClub = new ClubBudgetElementsBuilder().withClubName("ECE Club").build();
        BudgetCommand budgetComputingClubCommand = new BudgetCommand(computingClub);
        BudgetCommand budgetEceClubCommand = new BudgetCommand(eceClub);

        // same object -> returns true
        assertTrue(budgetComputingClubCommand.equals(budgetComputingClubCommand));

        // same values -> returns true
        BudgetCommand budgetComputingClubCommandCopy = new BudgetCommand(computingClub);
        assertTrue(budgetComputingClubCommand.equals(budgetComputingClubCommandCopy));

        // different types -> returns false
        assertFalse(budgetComputingClubCommand.equals(1));

        // null -> returns false
        assertFalse(budgetComputingClubCommand.equals(null));

        // different person -> returns false
        assertFalse(budgetComputingClubCommand.equals(budgetEceClubCommand));
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
        public ReadOnlyClubBudgetElementsBook getClubBudgetElementsBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyFinalBudgetBook getFinalBudgetsBook() {
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
            throw new AssertionError("This method should not be called.");
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
        public boolean canUndoClubBudgetElementsBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoClubBudgetElementsBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoClubBudgetElementsBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoClubBudgetElementsBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitClubBudgetElementsBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoFinalBudgetsBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoFinalBudgetsBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoFinalBudgetsBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoFinalBudgetsBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitFinalBudgetsBook() {
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
        public void revertLastSearch() throws EmptyHistoryException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void executeSearch(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetSearchHistoryToInitialState() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void recordKeywords(KeywordType type, List<String> keywords) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyKeywordsRecord getReadOnlyKeywordsRecord() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a club's budget elements.
     */
    private class ModelStubWithClubBudgetElements extends ModelStub {
        private final ClubBudgetElements club;

        ModelStubWithClubBudgetElements(ClubBudgetElements club) {
            requireNonNull(club);
            this.club = club;
        }

        @Override
        public boolean hasClub(ClubBudgetElements club) {
            requireNonNull(club);
            return this.club.isSameClub(club);
        }
    }

    /**
     * A Model stub that always accepts the club budget elements being added.
     */
    private class ModelStubAcceptingClubBudgetElementsAdded extends ModelStub {
        final ArrayList<ClubBudgetElements> clubBudgetElementsAdded = new ArrayList<>();

        @Override
        public boolean hasClub(ClubBudgetElements club) {
            requireNonNull(club);
            return clubBudgetElementsAdded.stream().anyMatch(club::isSameClub);
        }

        @Override
        public void addClub(ClubBudgetElements club) {
            requireNonNull(club);
            clubBudgetElementsAdded.add(club);
        }

        @Override
        public void commitClubBudgetElementsBook() {
            // called by {@code BudgetCommand#execute()}
        }

        @Override
        public ReadOnlyClubBudgetElementsBook getClubBudgetElementsBook() {
            return new ClubBudgetElementsBook();
        }
    }

}
