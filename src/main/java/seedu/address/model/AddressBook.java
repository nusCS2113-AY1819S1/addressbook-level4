package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Milestone;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyTaskBook {

    private final UniqueTaskList tasks;
    private Deadline currentDate;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        tasks = new UniqueTaskList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyTaskBook toBeCopied) {
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
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyTaskBook newData) {
        requireNonNull(newData);

        setTasks(newData.getTaskList());
    }

    //// task-level operations

    /**
     * Returns true if a task with the same identity as {@code task} exists in the address book.
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasks.contains(task);
    }

    /**
     * Adds a task to the address book.
     * The task must not already exist in the address book.
     */
    public void addTask(Task t) {
        tasks.add(t);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The task identity of {@code editedPerson} must not be the same as another existing task in the address book.
     */
    public void updateTask(Task target, Task editedPerson) {
        requireNonNull(editedPerson);

        tasks.setTask(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void completeTask(Task key) {
        tasks.complete(key);
    }

    //@@author ChanChunCheong
    /**
     * Defer {@code key} previous deadline with (@code deadline) from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void deferDeadline(Task key, String deadline) {
        requireNonNull(deadline);
        tasks.defer(key, deadline);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeTask(Task key) {
        tasks.remove(key);
    }

    //@@author JeremyInElysium
    /**
     * Adds a milestone to a selected task in the Task Book.
     * The task must already exist in the Task Book.
     */
    public void addMilestone(Milestone milestone) {
        requireNonNull(milestone);
        tasks.add(milestone);
    }

    /**
     * Selects the date for Task Book.
     * Update the list.
     */
    public void selectDeadline(Deadline deadline) {
        //TODO: @emobeany handle cases where year or month is not specified
        currentDate = deadline;
    }

    // util methods

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
                || (other instanceof AddressBook // instanceof handles nulls
                && tasks.equals(((AddressBook) other).tasks));
    }

    @Override
    public int hashCode() {
        return tasks.hashCode();
    }
}
