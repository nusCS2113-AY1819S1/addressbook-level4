package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.export.CsvWriter;
import seedu.address.export.Export;
import seedu.address.export.ExportManager;
import seedu.address.export.Import;
import seedu.address.export.ImportManager;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.tag.Tag;
import seedu.address.model.todo.Todo;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Todo> filteredTodos;
    private final FilteredList<Reminder> filteredReminders;

    private final UserPrefs userPrefs;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredTodos = new FilteredList<>(versionedAddressBook.getTodoList());
        filteredReminders = new FilteredList<>(versionedAddressBook.getReminderList());

        this.userPrefs = userPrefs;
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Todo tasks List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Todo} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Todo> getFilteredTodoList() {
        return FXCollections.unmodifiableObservableList(filteredTodos);
    }

    @Override
    public void updateFilteredTodoList(Predicate<Todo> predicate) {
        requireNonNull(predicate);
        filteredTodos.setPredicate(predicate);
    }

    //=========== Filtered Reminder List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Reminder> getFilteredReminderList() {
        return FXCollections.unmodifiableObservableList(filteredReminders);
    }

    @Override
    public void updateFilteredReminderList(Predicate<Reminder> predicate) {
        requireNonNull(predicate);
        filteredReminders.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
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
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredPersons.equals(other.filteredPersons);
    }

    //=========== Remove a particular tag from all persons ====================================================
    @Override
    public void deleteTag(Tag tag) {
        versionedAddressBook.removeTag(tag);
    }

    //=========== Import/ Export ==============================================================================
    @Override
    public void importPersonsFromAddressBook(Path importFilePath) throws IOException, DataConversionException {
        Import importManager = new ImportManager(importFilePath);
        ReadOnlyAddressBook addressBookImported = importManager.readAddressBook().orElseThrow(IOException::new);
        boolean hasChanged = addPersonsToAddressBook(addressBookImported);

        if (hasChanged) {
            updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            indicateAddressBookChanged();
        }
    }

    @Override
    public boolean addPersonsToAddressBook(ReadOnlyAddressBook addressBookImported) {
        ObservableList<Person> persons = addressBookImported.getPersonList();
        AtomicBoolean hasChanged = new AtomicBoolean(false);
        persons.forEach((person) -> {
            // TODO: explain why this instead of addPerson() above in developer guide (indicate ab changed at the end)
            if (!hasPerson(person)) {
                hasChanged.set(true);
                versionedAddressBook.addPerson(person);
            }
        });
        return hasChanged.get();
    }

    @Override
    public void exportFilteredAddressBook(Path exportFilePath) throws IOException, IllegalValueException {
        Export export = new ExportManager(getFilteredPersonList(), exportFilePath);
        export.saveFilteredPersons();
    }

    @Override
    public void exportAddressBook() throws IOException {
        CsvWriter csvWriter = new CsvWriter(versionedAddressBook.getPersonList(), userPrefs.getExportCsvFilePath());
        csvWriter.write();
    }

    @Override
    public void exportPerson(Person person) throws IOException {
        requireNonNull(person);
        CsvWriter csvWriter = new CsvWriter(person, userPrefs.getExportCsvFilePath());
        csvWriter.write();
    }

    //=========== Todo ========================================================================================
    @Override
    public boolean hasTodo(Todo todo) {
        requireNonNull(todo);
        return versionedAddressBook.hasTodo(todo);
    }

    @Override
    public void addTodo(Todo todo) {
        versionedAddressBook.addTodo(todo);
        updateFilteredTodoList(PREDICATE_SHOW_ALL_TODOS);
        indicateAddressBookChanged();
    }

    @Override
    public void finishTodo(Todo target) {
        versionedAddressBook.removeTodo(target);
        indicateAddressBookChanged();
    }

    //=========== Reminder ====================================================================================
    @Override
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return versionedAddressBook.hasReminder(reminder);
    }

    @Override
    public void addReminder(Reminder reminder) {
        versionedAddressBook.addReminder(reminder);
        updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
        indicateAddressBookChanged();
    }
}
