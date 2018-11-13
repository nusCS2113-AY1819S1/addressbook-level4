package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.ExpenditureTrackerChangedEvent;
import seedu.address.commons.events.model.TodoListChangedEvent;
import seedu.address.logic.UndoableCommandHistory;
import seedu.address.model.expenditureinfo.Expenditure;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final VersionedTodoList versionedTodoList;
    private final VersionedExpenditureTracker versionedExpenditureTracker;
    private final FilteredList<Expenditure> filteredExpenditures;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Task> filteredTasks;
    private final UndoableCommandHistory undoableCommandHistory;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyTodoList todoList,
                        ReadOnlyExpenditureTracker expenditureTracker, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, todoList, expenditureTracker, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        versionedTodoList = new VersionedTodoList(todoList);
        versionedExpenditureTracker = new VersionedExpenditureTracker(expenditureTracker);
        filteredExpenditures = new FilteredList<>(versionedExpenditureTracker.getExpenditureList());
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredTasks = new FilteredList<>(versionedTodoList.getTaskList());
        undoableCommandHistory = new UndoableCommandHistory();
    }

    public ModelManager() {
        this(new AddressBook(), new TodoList(), new ExpenditureTracker(), new UserPrefs());
    }

    @Override
    public Predicate<Expenditure> getPredicateShowExpendituresOnDate(String date) {
        return expenditure -> (expenditure.getDate().toString().equals(date));
    }

    @Override
    public Predicate<Expenditure> getPredicateShowExpendituresOfCategory(String category) {
        return expenditure -> (expenditure.getCategory().toString().equals(category));
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

    @Override
    public ReadOnlyTodoList getTodoList() {
        return versionedTodoList;
    }

    @Override
    public ReadOnlyExpenditureTracker getExpenditureTracker() {
        return versionedExpenditureTracker;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    /** Raises an event to indicate the model has changed */
    private void indicateExpenditureTrackerChanged() {
        raise(new ExpenditureTrackerChangedEvent(versionedExpenditureTracker));
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTodoListChanged() {
        raise(new TodoListChangedEvent(versionedTodoList));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return versionedTodoList.hasTask(task);
    }

    @Override
    public boolean hasExpenditure(Expenditure expenditure) {
        requireNonNull(expenditure);
        return versionedExpenditureTracker.hasExpenditure(expenditure);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteTask(Task target) {
        versionedTodoList.removeTask(target);
        indicateTodoListChanged();
    }

    @Override
    public void deleteExpenditure(Expenditure target) {
        versionedExpenditureTracker.removeExpenditure(target);
        indicateExpenditureTrackerChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void addTask(Task task) {
        versionedTodoList.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateTodoListChanged();
    }

    @Override
    public void addExpenditure(Expenditure expenditure) {
        versionedExpenditureTracker.addExpenditure(expenditure);
        updateFilteredExpenditureList(PREDICATE_SHOW_ALL_EXPENDITURES);
        indicateExpenditureTrackerChanged();
    }

    @Override
    public Map getExpenditureRecords() {
        return versionedExpenditureTracker.getExpenditureRecords();
    }

    @Override
    public String checkRecords(String date) {
        String records;
        StringBuilder builder = new StringBuilder();
        builder.append(versionedExpenditureTracker.checkExpenditureRecordsOnParticularDay(date))
                .append("\n\n")
                .append(versionedTodoList.checkTasksRecordsOnParticularDay(date));
        records = builder.toString();
        return records;
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    @Override
    public void updateTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        versionedTodoList.updateTask(target, editedTask);
        indicateTodoListChanged();
    }

    @Override
    public void updateExpenditure(Expenditure target, Expenditure editedExpenditure) {
        requireAllNonNull(target, editedExpenditure);

        versionedExpenditureTracker.updateExpenditure(target, editedExpenditure);
        indicateExpenditureTrackerChanged();
    }

    @Override
    public void deleteTag(Tag tag) {
        versionedAddressBook.removeTag(tag);
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

    //=========== Filtered Task List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code versionedTodoList}
     */
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return FXCollections.unmodifiableObservableList(filteredTasks);
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

    //=========== Filtered Expenditure List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Expenditure} backed by the internal list of
     * {@code versionedExpenditureTracker}
     */
    @Override
    public ObservableList<Expenditure> getFilteredExpenditureList() {
        return FXCollections.unmodifiableObservableList(filteredExpenditures);
    }

    @Override
    public void updateFilteredExpenditureList(Predicate<Expenditure> predicate) {
        requireNonNull(predicate);
        filteredExpenditures.setPredicate(predicate);
    }

    //=========== Rank Filtered Task List ===================================================================

    @Override
    public void rankFilteredTaskDeadline() {
        versionedTodoList.sortTaskDate();
    }

    @Override
    public void rankFilteredTaskModule() {
        versionedTodoList.sortTaskModule();
    }

    @Override
    public void rankFilteredTaskPriority() {
        versionedTodoList.sortTaskPriority();
    }

    @Override
    public void reverseTodoList() {
        versionedTodoList.reverseTasks();
    }

    @Override
    public void rankTaskDefault() {
        versionedTodoList.sortTaskDefault();
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
    public boolean canUndoExpenditureList() {
        return versionedExpenditureTracker.canUndo();
    }

    @Override
    public boolean canRedoExpenditureList() {
        return versionedExpenditureTracker.canRedo();
    }

    @Override
    public void undoExpenditureList() {
        versionedExpenditureTracker.undo();
        indicateExpenditureTrackerChanged();
    }

    @Override
    public void redoExpenditureList() {
        versionedExpenditureTracker.redo();
        indicateExpenditureTrackerChanged();
    }

    @Override
    public void commitExpenditureList() {
        versionedExpenditureTracker.commit();
    }

    @Override
    public boolean canUndoTodoList() {
        return versionedTodoList.canUndo();
    }

    @Override
    public boolean canRedoTodoList() {
        return versionedTodoList.canRedo();
    }

    @Override
    public void undoTodoList() {
        versionedTodoList.undo();
        indicateTodoListChanged();
    }

    @Override
    public void redoTodoList() {
        versionedTodoList.redo();
        indicateTodoListChanged();
    }

    @Override
    public void commitTodoList() {
        versionedTodoList.commit();
    }

    @Override
    public String getUndoableCommand() {
        return undoableCommandHistory.getCommand();
    }

    @Override
    public void commitUndoableTodoList() {
        undoableCommandHistory.addTodoList();
    }

    @Override
    public void commitUndoableExpenditure() {
        undoableCommandHistory.addExpenditureTracker();
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
                && filteredPersons.equals(other.filteredPersons)
                && versionedExpenditureTracker.equals(other.versionedExpenditureTracker)
                && filteredExpenditures.equals(other.filteredExpenditures)
                && versionedTodoList.equals(other.versionedTodoList)
                && filteredTasks.equals(other.filteredTasks);
    }

}
