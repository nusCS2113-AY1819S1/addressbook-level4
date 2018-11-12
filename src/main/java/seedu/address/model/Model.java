package seedu.address.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.tag.Tag;
import seedu.address.model.todo.Todo;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Todo> PREDICATE_SHOW_ALL_TODOS = unused -> true;
    Predicate<Reminder> PREDICATE_SHOW_ALL_REMINDERS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

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

    /**
     * Deletes the given tag {@code tag} from all persons {@code person}.
     * {@code tag} may or may not exist in the address book.
     */
    void deleteTag(Tag tag);

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

    //=========== Import/ Export ==============================================================================
    /**
     * Imports the persons from a xml at {@code importFilePath}.
     */
    void importPersonsFromAddressBook(Path importFilePath) throws IOException, DataConversionException;

    /**
     * Adds all the persons in {@code addressBookImported} to the current address book.
     * @return hasChanged is true if the addressBook is modified, returns false otherwise.
     */
    boolean addPersonsToAddressBook(ReadOnlyAddressBook addressBookToImported);

    /**
     * Exports the current filtered person list to a xml file at {@code exportFilePath}.
     */
    void exportFilteredAddressBook(Path exportFilePath) throws IOException, IllegalValueException;

    /**
     * Exports the current address book state to a .csv file.
     */
    void exportAddressBook() throws IOException;

    /**
     * Exports the given person to a .csv file.
     */
    void exportPerson(Person person) throws IOException;

    /** Returns an unmodifiable view of the filtered todo task list */
    ObservableList<Todo> getFilteredTodoList();

    /**
     * Returns true if a todo task with the same fields as {@code todo} exists in the address book.
     */
    boolean hasTodo(Todo todo);

    /**
     * Adds the given todo task.
     * {@code todo} must not already exist in the address book.
     */
    void addTodo(Todo todo);

    /**
     * Complete the given todo task.
     * {@code todo} must already exist in the address book.
     */
    void finishTodo(Todo todo);

    /**
     * Updates the filter of the filtered todo task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTodoList(Predicate<Todo> predicate);

    /**
     * Returns true if a reminder with the same fields as {@code reminder} exists in the address book.
     */

    /** Returns an unmodifiable view of the filtered reminder list */
    ObservableList<Reminder> getFilteredReminderList();

    boolean hasReminder(Reminder reminder);

    /**
     * Adds the reminder.
     * {@code reminder} must not already exist in the address book.
     */
    void addReminder(Reminder reminder);

    /**
     * Updates the filter of the filtered reminder list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredReminderList(Predicate<Reminder> predicate);

}
