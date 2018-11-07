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
import seedu.address.model.EventBook;
import seedu.address.model.ExpenseBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.ReadOnlyExpenseBook;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.expense.Expense;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import seedu.address.testutil.ExpenseBuilder;

public class AddExpenseCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullExpense_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddExpenseCommand(null);
    }

    @Test
    public void execute_expenseAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingExpenseAdded modelStub = new ModelStubAcceptingExpenseAdded();
        Expense validExpense = new ExpenseBuilder().build();

        CommandResult commandResult = new AddExpenseCommand(validExpense).execute(modelStub, commandHistory);

        assertEquals(String.format(AddExpenseCommand.MESSAGE_SUCCESS, validExpense), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validExpense), modelStub.expensesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        Expense shopping = new ExpenseBuilder().withExpenseCategory("Shopping").build();
        Expense mrt = new ExpenseBuilder().withExpenseCategory("MRT").build();
        AddExpenseCommand addShoppingCommand = new AddExpenseCommand(shopping);
        AddExpenseCommand addMrtCommand = new AddExpenseCommand(mrt);

        // same object -> returns true
        assertTrue(addShoppingCommand.equals(addShoppingCommand));

        // same values -> returns true
        AddExpenseCommand addShoppingCommandCopy = new AddExpenseCommand(shopping);
        assertTrue(addShoppingCommand.equals(addShoppingCommandCopy));

        // different types -> returns false
        assertFalse(addShoppingCommand.equals(1));

        // null -> returns false
        assertFalse(addShoppingCommand.equals(null));

        // different person -> returns false
        assertFalse(addShoppingCommand.equals(addMrtCommand));
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
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyExpenseBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyEventBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyTaskBook newData) {

        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTaskBook getTaskBook() {
            return null;
        }

        @Override
        public UserPrefs getUserPrefs() {
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
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
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
        public void restoreAddressBook(ReadOnlyAddressBook restoredAddressBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void restoreExpenseBook(ReadOnlyExpenseBook restoredExpenseBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void restoreEventBook(ReadOnlyEventBook restoredEventBook) {
            throw new AssertionError("This method should not be called.");
        }

        public void restoreTaskBook(ReadOnlyTaskBook restoredTaskBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitTaskBook() {

        }

        @Override
        public boolean hasTask(Task task) {
            return false;
        }

        @Override
        public void deleteTask(Task target) {

        }

        @Override
        public void addTask(Task person) {

        }

        @Override
        public void updateTask(Task target, Task editedPerson) {

        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            return null;
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {

        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            return null;
        }

        @Override
        public void addEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitEventBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyEventBook getEventBook() {
            return new EventBook();
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        //======== Expense ============================================================================================

        @Override
        public ReadOnlyExpenseBook getExpenseBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteExpense(Expense target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addExpense(Expense expense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateExpense(Expense target, Expense editedExpense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Expense> getFilteredExpenseList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredExpenseList(Predicate<Expense> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoExpenseBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoExpenseBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoExpenseBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoExpenseBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitExpenseBook() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single expense.
     */
    private class ModelStubWithExpense extends ModelStub {
        private final Expense expense;

        ModelStubWithExpense(Expense expense) {
            requireNonNull(expense);
            this.expense = expense;
        }
    }

    /**
     * A Model stub that always accept the expense being added.
     */
    private class ModelStubAcceptingExpenseAdded extends ModelStub {
        final ArrayList<Expense> expensesAdded = new ArrayList<>();

        @Override
        public void addExpense(Expense expense) {
            requireNonNull(expense);
            expensesAdded.add(expense);
        }

        @Override
        public void commitExpenseBook() {
            // called by {@code AddExpenseCommand#execute()}
        }

        @Override
        public ReadOnlyExpenseBook getExpenseBook() {
            return new ExpenseBook();
        }
    }
}
