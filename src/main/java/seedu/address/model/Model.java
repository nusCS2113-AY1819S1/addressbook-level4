package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Milestone;
import seedu.address.model.task.Task;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskBook newData);

    /** Returns the AddressBook */
    ReadOnlyTaskBook getAddressBook();

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
     * Completes the given task.
     * The task must exist in the task book.
     */
    void completeTask(Task target);

    /**
     * Adds the given task.
     * {@code task} must not already exist in the task book.
     */
    void addTask(Task task);

    //@@author emobeany
    /**
     * Selects the input date as deadline.
     */
    void selectDeadline(Deadline deadline);

    /**
     * Replaces the given task {@code target} with {@code editedTask}.
     * {@code target} must exist in the task book.
     * The task identity of {@code editedTask} must not be the same as another existing task in the task book.
     */
    void updateTask(Task target, Task editedTask);

    //@@author JeremyInElysium
    /**
     * Adds the given milestone.
     */
    void addMilestone(Milestone milestone);

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

    /**
     * Returns true if the model has previous task book states to restore.
     */
    boolean canUndoTaskBook();

    /**
     * Returns true if the model has undone task book states to restore.
     */
    boolean canRedoTaskBook();

    /**
     * Restores the model's task book to its previous state.
     */
    void undoTaskBook();

    /**
     * Restores the model's task book to its previously undone state.
     */
    void redoTaskBook();

    /**
     * Saves the current task book state for undo/redo.
     */
    void commitTaskBook();
}
