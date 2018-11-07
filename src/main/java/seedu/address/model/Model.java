package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.expense.Expense;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Expense> PREDICATE_SHOW_ALL_EXPENSES = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    //@@author luhan02
    /** {@code Predicate} that always evaluate to true */
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;

    //@@author ian-tjahjono
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;
    //@@author
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Clears existing expense model and replaces with the provided new data. */
    void resetData(ReadOnlyExpenseBook newData);

    /** Clears existing event model and replaces with the provided new data. */
    void resetData(ReadOnlyEventBook newData);

    /** Clears existing expense model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Returns the TaskBook */
    ReadOnlyTaskBook getTaskBook();

    /** Returns the UserPreferences */
    UserPrefs getUserPrefs();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void updatePerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

    /**
     * Restore address book from storage.
     * @param restoredAddressBook
     */
    void restoreAddressBook(ReadOnlyAddressBook restoredAddressBook);

    /**
     * Restore expense book from storage.
     * @param restoredExpenseBook
     */
    void restoreExpenseBook(ReadOnlyExpenseBook restoredExpenseBook);

    /**
     * Restore event book from storage.
     * @param restoredEventBook
     */
    void restoreEventBook(ReadOnlyEventBook restoredEventBook);

    /**
     * Restore task book from storage.
     * @param restoredTaskBook
     */
    void restoreTaskBook(ReadOnlyTaskBook restoredTaskBook);

    //@@author luhan02
    /**
     * Saves the current task book state.
     */
    void commitTaskBook();

    /**
     * Returns true if a task with the same identity as {@code task} exists in the task book.
     */
    boolean hasTask(Task task);

    /**
     * Deletes the given task.
     * The task must exist in the task book.
     */
    void deleteTask(Task target);

    /**
     * Adds the given task.
     * {@code task} must not already exist in the task book.
     */
    void addTask(Task task);

    /**
     * Replaces the given task {@code target} with {@code editedTask}.
     * {@code target} must exist in the task list.
     * The task identity of {@code editedTask} must not be the same as another existing task in the task list.
     */
    void updateTask(Task target, Task editedTask);

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

    //@@author ian-tjahjono
    /**
     * Returns true if an event with the same identity as {@code event} exists in the student planner.
     */
    boolean hasEvent(Event event);

    /**
     * Deletes the given event.
     * The event must exist in the student planner.
     */
    void deleteEvent(Event target);

    /**
     * Adds the given event.
     * {@code event} must not already exist in the student planner.
     */
    void addEvent(Event event);

    /** Returns an unmodifiable view of the filtered event list */
    ObservableList<Event> getFilteredEventList();

    /**
     * Saves the current expense book state for undo/redo.
     */
    void commitEventBook();

    /** Returns the EventBook */
    ReadOnlyEventBook getEventBook();

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);

    //@@author ChenSongJian

    /** Returns the ExpenseBook */
    ReadOnlyExpenseBook getExpenseBook();

    /**
     * Deletes the given expense.
     * The expense must exist in the address book.
     */
    void deleteExpense(Expense target);

    /**
     * Adds the given expense.
     */
    void addExpense(Expense expense);

    /**
     * Replaces the given expense {@code target} with {@code editedExpense}.
     * {@code target} must exist in the expense book.
     */
    void updateExpense(Expense target, Expense editedExpense);

    /** Returns an unmodifiable view of the filtered expense list */
    ObservableList<Expense> getFilteredExpenseList();

    /**
     * Updates the filter of the filtered expense list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExpenseList(Predicate<Expense> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoExpenseBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoExpenseBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoExpenseBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoExpenseBook();

    /**
     * Saves the current expense book state for undo/redo.
     */
    void commitExpenseBook();
    //@@author
}

