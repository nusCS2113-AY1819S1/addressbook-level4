package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * Wraps all data at the to-do-list level
 * Duplicates are not allowed (by .isSameTask comparison)
 */
public class TodoList implements ReadOnlyTodoList {

    private final UniqueTaskList tasks = new UniqueTaskList();

    public TodoList() {}

    /**
     * Creates an TodoList using the Tasks in the {@code toBeCopied}
     */
    public TodoList(ReadOnlyTodoList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the task list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks.setTasks(tasks);
    }

    /**
     * Resets the existing data of this {@code TodoList} with {@code newData}.
     */
    public void resetData(ReadOnlyTodoList newData) {
        requireNonNull(newData);

        setTasks(newData.getTaskList());
    }

    //// task-level operations

    /**
     * Returns true if a task with the same identity as {@code task} exists in the to-do list.
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasks.contains(task);
    }

    /**
     * Adds a task to the to-do list.
     * The task must not already exist in the to-do list.
     */
    public void addTask(Task t) {
        tasks.add(t);
    }

    /**
     * Get the records of tasks on a particular day
     */
    public String checkTasksRecordsOnParticularDay(String date) {
        return tasks.checkTasksRecordsOnParticularDay(date);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the to-do list.
     * The task identity of {@code editedTask} must not be the same as another existing task in the to-do list.
     */
    public void updateTask(Task target, Task editedTask) {
        requireNonNull(editedTask);

        tasks.setTask(target, editedTask);
    }

    /**
     * Removes {@code key} from this {@code TodoList}.
     * {@code key} must exist in the to-do list.
     */
    public void removeTask(Task key) {
        tasks.remove(key);
    }

    public void sortTaskDate() {
        tasks.sortDate();
    }

    public void sortTaskPriority() {
        tasks.sortPriority();
    }

    public void sortTaskModule() {
        tasks.sortModule();
    }

    public void reverseTasks () {
        tasks.reverseTask();
    }

    public void sortTaskDefault () {
        tasks.sortDefault();
    }

    //// util methods

    @Override
    public String toString() {
        return tasks.asUnmodifiableObservableList().size() + " tasks";
        // TODO: refine later
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return tasks.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TodoList // instanceof handles nulls
                && tasks.equals(((TodoList) other).tasks));
    }

    @Override
    public int hashCode() {
        return tasks.hashCode();
    }
}
