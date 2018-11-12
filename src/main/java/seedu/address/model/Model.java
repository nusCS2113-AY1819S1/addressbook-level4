package seedu.address.model;

import java.util.Map;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.expenditureinfo.Expenditure;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;
    Predicate<Expenditure> PREDICATE_SHOW_ALL_EXPENDITURES = unused -> true;

    /** {@code Predicate} that evaluates based on the status of the task */
    Predicate<Task> PREDICATE_SHOW_ALL_COMPLETED_TASKS = task -> (task.getComplete());
    Predicate<Task> PREDICATE_SHOW_ALL_UNCOMPLETED_TASKS = task -> (!task.getComplete());

    /** {@code Predicate} that evaluates based on the date of the task */
    Predicate<Expenditure> getPredicateShowExpendituresOnDate(String date);
    Predicate<Expenditure> getPredicateShowExpendituresOfCategory(String category);

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Returns the TodoList */
    ReadOnlyTodoList getTodoList();

    /** Returns the ExpenditureTracker */
    ReadOnlyExpenditureTracker getExpenditureTracker();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a task with the same identity as {@code task} exists in the to-do list.
     */
    boolean hasTask(Task task);

    /**
     * Returns true if an expenditure with the same identity as {@code expenditure} exists in the expenditure tracker.
     */
    boolean hasExpenditure(Expenditure expenditure);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Deletes the given task.
     * The task must exist in the to-do list.
     */
    void deleteTask(Task target);

    /**
     * Deletes the given expenditure.
     * The expenditure must exist in the expenditure tracker.
     */
    void deleteExpenditure(Expenditure target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Adds the given task.
     * {@code task} must not already exist in the to-do list.
     */
    void addTask(Task task);

    /**
     * Adds the given expenditure.
     */
    void addExpenditure(Expenditure expenditure);

    /**
     * Get the expenditure records.
     */
    Map getExpenditureRecords();

    /**
     * Check records on particular day.
     */
    String checkRecords(String date);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void updatePerson(Person target, Person editedPerson);

    /**
     * Replaces the given task {@code target} with {@code editedTask}.
     * {@code target} must exist in the to-do list.
     * The task identity of {@code editedTask} must not be the same as another existing task in the to-do list.
     */
    void updateTask(Task target, Task editedTask);

    /**
     * Replaces the given expenditure {@code target} with {@code editedExpenditure}.
     * {@code target} must exist in the expenditure tracker.
     * The expenditure identity of {@code editedExpenditure} must not be the same as another existing expenditure in
     * the expenditure tracker.
     */
    void updateExpenditure(Expenditure target, Expenditure editedExpenditure);


    void deleteTag(Tag tag);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<Task> getFilteredTaskList();

    /** Returns an unmodifiable view of the filtered expenditure list */
    ObservableList<Expenditure> getFilteredExpenditureList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);


    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

    /**
     * Updates the filter of the filtered expenditure list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExpenditureList(Predicate<Expenditure> predicate);

    /**
     * Sort the task list based on their deadline date
     */
    void rankFilteredTaskDeadline();

    /**
     * Sort the task list based on their module code
     */
    void rankFilteredTaskModule();

    /**
     * Sort the task list based on their priority
     */
    void rankFilteredTaskPriority();

    /**
     * Reverse the sequence of tasks in the to-do list
     */
    void reverseTodoList();

    /**
     * Sort the task list based on their task name
     * No difference between upper case and lower case in the comparator
     */
    void rankTaskDefault();


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
     * Returns true if the model has previous to-do list states to restore.
     */
    boolean canUndoTodoList();

    /**
     * Returns true if the model has undone to-do list states to restore.
     */
    boolean canRedoTodoList();

    /**
     * Restores the model's to-do list to its previous state.
     */
    void undoTodoList();

    /**
     * Restores the model's to-do list to its previously undone state.
     */
    void redoTodoList();

    /**
     * Saves the current to-do list state for undo/redo.
     */
    void commitTodoList();

    /**
     * Saves the current expenditure tracker state for undo/redo.
     */

    /**
     * Judge whether last expenditure command can be undo
     * @return true if can undo, else return false
     */
    boolean canUndoExpenditureList();

    /**
     * Judge whether last undo can be redo/ last "undo"exist or not
     * @return true if can redo, else return false
     */
    boolean canRedoExpenditureList();

    /**
     * Restores the model's expenditure tracker to its previous state.
     */
    void undoExpenditureList();

    /**
     * Restores the model's expenditure tracker to its previously undone state.
     */
    void redoExpenditureList();

    /**
     * Saves the current expenditure tracker state for undo/redo.
     */
    void commitExpenditureList();

    /**
     * get the previous undoable command (to-do list/ expenditure tracker)
     */
    String getUndoableCommand();

    /**
     * save the previous to-do list command in the stack
     */
    void commitUndoableTodoList();

    /**
     * save the previous expenditure command in the stack
     */
    void commitUndoableExpenditure();
}
