package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.TaskBookChangedEvent;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the task book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedTaskBook versionedTaskBook;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given taskBook and userPrefs.
     */
    public ModelManager(ReadOnlyTaskBook taskBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(taskBook, userPrefs);

        logger.fine("Initializing with task book: " + taskBook + " and user prefs " + userPrefs);

        versionedTaskBook = new VersionedTaskBook(taskBook);
        filteredPersons = new FilteredList<>(versionedTaskBook.getTaskList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }
    //initialise the a new task book with new user prefs

    @Override
    public void resetData(ReadOnlyTaskBook newData) {
        versionedTaskBook.resetData(newData);
        indicateTaskBookChanged();
    }

    @Override
    public ReadOnlyTaskBook getAddressBook() {
        return versionedTaskBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskBookChanged() {
        raise(new TaskBookChangedEvent(versionedTaskBook));
    }

    @Override
    public boolean hasTask(Person person) {
        requireNonNull(person);
        return versionedTaskBook.hasPerson(person);
    }

    @Override
    public void deleteTask(Person target) {
        versionedTaskBook.removePerson(target);
        indicateTaskBookChanged();
    }

    @Override
    public void addTask(Person person) {
        versionedTaskBook.addPerson(person);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateTaskBookChanged();
    }

    @Override
    public void updateTask(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedTaskBook.updatePerson(target, editedPerson);
        indicateTaskBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedTaskBook}
     */
    @Override
    public ObservableList<Person> getFilteredTaskList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredTaskList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoTaskBook() {
        return versionedTaskBook.canUndo();
    }

    @Override
    public boolean canRedoTaskBook() {
        return versionedTaskBook.canRedo();
    }

    @Override
    public void undoTaskBook() {
        versionedTaskBook.undo();
        indicateTaskBookChanged();
    }

    @Override
    public void redoTaskBook() {
        versionedTaskBook.redo();
        indicateTaskBookChanged();
    }

    @Override
    public void commitTaskBook() {
        versionedTaskBook.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedTaskBook.equals(other.versionedTaskBook)
                && filteredPersons.equals(other.filteredPersons);
    }

}
