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
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.todo.Todo;
import seedu.address.testutil.TodoBuilder;

//@@author linnnruoo
public class TodoCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullTodo_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new TodoCommand(null);
    }

    @Test
    public void execute_todoAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTodoAdded modelStub = new ModelStubAcceptingTodoAdded();
        Todo validTodo = new TodoBuilder().build();

        CommandResult commandResult = new TodoCommand(validTodo).execute(modelStub, commandHistory);

        assertEquals(String.format(TodoCommand.MESSAGE_SUCCESS, validTodo), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTodo), modelStub.todosAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateTodo_throwsCommandException() throws Exception {
        Todo validTodo = new TodoBuilder().build();
        TodoCommand todoCommand = new TodoCommand(validTodo);
        ModelStub modelStub = new ModelStubWithTodo(validTodo);

        thrown.expect(CommandException.class);
        thrown.expectMessage(todoCommand.MESSAGE_DUPLICATE_TODO);
        todoCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Todo task1 = new TodoBuilder().withTitle("Task1").build();
        Todo task2 = new TodoBuilder().withTitle("Task2").build();
        TodoCommand addTask1Command = new TodoCommand(task1);
        TodoCommand addTask2Command = new TodoCommand(task2);

        // same object -> returns true
        assertTrue(addTask1Command.equals(addTask1Command));

        // same values -> returns true
        TodoCommand addTask1CommandCopy = new TodoCommand(task1);
        assertTrue(addTask1Command.equals(addTask1CommandCopy));

        // different types -> returns false
        assertFalse(addTask1Command.equals(1));

        // null -> returns false
        assertFalse(addTask1Command.equals(null));

        // different todo -> returns false
        assertFalse(addTask1Command.equals(addTask2Command));
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
        public void deleteTag(Tag tag) {
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
        public void exportAddressBook() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ObservableList<Todo> getFilteredTodoList() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean hasTodo(Todo todo) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void addTodo(Todo todo) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void updateFilteredTodoList(Predicate<Todo> predicate) {
            throw new AssertionError("This method should not be called");
        }
    }

    /**
     * A Model stub that contains a single todo task.
     */
    private class ModelStubWithTodo extends ModelStub {
        private final Todo todo;

        ModelStubWithTodo(Todo todo) {
            requireNonNull(todo);
            this.todo = todo;
        }

        @Override
        public boolean hasTodo(Todo todo) {
            requireNonNull(todo);
            return this.todo.isSameTodo(todo);
        }
    }

    /**
     * A Model stub that always accept the todo being added.
     */
    private class ModelStubAcceptingTodoAdded extends ModelStub {
        final ArrayList<Todo> todosAdded = new ArrayList<>();

        @Override
        public boolean hasTodo(Todo todo) {
            requireNonNull(todo);
            return todosAdded.stream().anyMatch(todo::isSameTodo);
        }

        @Override
        public void addTodo(Todo todo) {
            requireNonNull(todo);
            todosAdded.add(todo);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code TodoCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
