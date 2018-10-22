package seedu.address.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Predicate;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.autocomplete.TextPrediction;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Activity;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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

    //@@author lekoook
    /**
     * Get the TextPrediction instance.
     * @return the TextPrediction instance used for text prediction.
     */
    TextPrediction getTextPrediction();

    /**
     * Initialises the list of selected Persons in address book.
     * @param selectedPersons the list to initialise with.
     */
    void setSelectedPersons(List<Person> selectedPersons);

    /**
     * Returns the list of selected Persons in address book.
     * @return the list of selected Persons.
     */
    List<Person> getSelectedPersons();

    //@@author lws803
    /**
     * Reinitialises the address book
     */
    void reinitAddressbook ();

    //@@author LowGinWee
    void addActivity(Activity activity);

    void deleteActivity(Activity activity);

    ObservableList<Activity> getActivityList();

    TreeMap<Date, ArrayList<Activity>> getSchedule();

}
