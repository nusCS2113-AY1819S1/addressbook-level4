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
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Task;

/**
 * Represents the in-memory model of the task book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedTaskBook versionedTaskBook;
    private final FilteredList<Task> filteredTasks;
    private final Predicate<Task> predicateShowCompletedTasks = Task::isCompleted;

    /**
     * Initializes a ModelManager with the given taskBook and userPrefs.
     */
    public ModelManager(ReadOnlyTaskBook taskBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(taskBook, userPrefs);

        logger.fine("Initializing with task book: " + taskBook + " and user prefs " + userPrefs);

        versionedTaskBook = new VersionedTaskBook(taskBook);
        filteredTasks = new FilteredList<>(versionedTaskBook.getTaskList());
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
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return versionedTaskBook.hasTask(task);
    }

    //@@author emobeany
    @Override
    public boolean isTheExactSameTaskAs(Task otherTask) {
        requireNonNull(otherTask);
        return versionedTaskBook.isTheExactSameTaskAs(otherTask);
    }

    //@@author chelseyong
    @Override
    public void deleteTask(Task target) {
        versionedTaskBook.removeTask(target);
        indicateTaskBookChanged();
    }

    @Override
    public void completeTask(Task target, int hours) {
        versionedTaskBook.completeTask(target, hours);
        indicateTaskBookChanged();
    }

    @Override
    public void addTask(Task task) {
        versionedTaskBook.addTask(task);
        updateFilteredTaskList(predicateShowTasksWithSameDate(task.getDeadline()));
        indicateTaskBookChanged();
    }

    //@@author ChanChunCheong
    @Override
    public void addTag(Task task, Tag tag) {
        versionedTaskBook.addTag(task, tag);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateTaskBookChanged();
    }

    @Override
    public void removeTag(Task task, Tag tag) {
        versionedTaskBook.removeTag(task, tag);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateTaskBookChanged();
    }

    @Override
    public void sortTask(String method) {
        versionedTaskBook.sortTask(method);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateTaskBookChanged();
    }

    @Override
    public void selectTag(Tag tag) {
        updateFilteredTaskList(predicateShowTasksWithSameTag(tag));
        indicateTaskBookChanged();
    }
    //@@author

    //@@author emobeany
    @Override
    public void updateTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);
        versionedTaskBook.updateTask(target, editedTask);
        indicateTaskBookChanged();
    }

    @Override
    public void selectDeadline(Deadline deadline) {
        versionedTaskBook.selectDeadline(deadline);
        updateFilteredTaskList(predicateShowTasksWithSameDate(deadline));
        indicateTaskBookChanged();
    }

    /**{@code Predicate} that returns true when the date is equal*/
    private Predicate<Task> predicateShowTasksWithSameDate(Deadline deadline) {
        return task -> task.getDeadline().equals(deadline);
    }

    //@author ChanChunCheong
    /**{@code Predicate} that returns true when the date is equal*/
    private Predicate<Task> predicateShowTasksWithSameTag(Tag tag) {
        return task -> task.getTags().contains(tag);
    }
    //@author

    @Override
    public Deadline getDeadline() {
        return versionedTaskBook.getDeadline();
    }

    @Override
    public String getYear() {
        return versionedTaskBook.getYear();
    }

    //@@author
    //=========== Filtered Task List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code versionedTaskBook}
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
        return versionedTaskBook.equals(other.versionedTaskBook);
    }
    //@@author chelseyong
    /**
     * Updates the task to completed tasks only
     * So that productivity can be correctly calculated
     */
    public void trackProductivity() {
        updateFilteredTaskList(predicateShowCompletedTasks);
        indicateTaskBookChanged();
    }
}
