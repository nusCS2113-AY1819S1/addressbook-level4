package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.SortTaskList;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyTaskBook {

    private static final Deadline PLACEHOLDER_DEADLINE = new Deadline("1/1/2018");
    //private static final Logger logger = LogsCenter.getLogger(AddressBook.class);
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
     * Returns true if there is a task in task book that has exactly the same fields as input task
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasks.contains(task);
    }

    //@@author emobeany
    /**
     * Returns true if a task with the same identity as {@code task} exists in the address book.
     */
    public boolean isTheExactSameTaskAs(Task task) {
        requireNonNull(task);
        return tasks.containsExactCopyOf(task);
    }

    /**
     * Adds a task to the address book.
     * The task must not already exist in the address book.
     */
    public void addTask(Task t) {
        tasks.add(t);
    }

    //@@author ChanChunCheong
    /**
     * Adds a tag to a tag in the address book.
     * The tag must not already exist in the task.
     */
    public void addTag(Task target, Tag tag) {
        tasks.addTag(target, tag);
    }

    //@@author ChanChunCheong
    /**
     * Adds a tag to a tag in the address book.
     * The tag must not already exist in the task.
     */
    public void removeTag(Task target, Tag tag) {
        tasks.removeTagFromTask(target, tag);
    }

    //@@author emobeany
    /**
     * Replaces the given task {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The task identity of {@code editedPerson} must not be the same as another existing task in the address book.
     */
    public void updateTask(Task target, Task editedTask) {
        requireNonNull(editedTask);

        tasks.setTask(target, editedTask);
    }

    //@@author
    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void completeTask(Task key, int hours) {
        tasks.complete(key, hours);
    }

    //@@author ChanChunCheong
    /**
     * Defer {@code key} previous deadline with (@code deadline) from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void deferDeadline(Task key, int deferredDays) {
        requireNonNull(deferredDays);
        tasks.defer(key, deferredDays);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeTask(Task key) {
        tasks.remove(key);
    }

    //@@author emobeany
    /**
     * Selects the date for Task Book.
     * Update the list.
     */
    public void selectDeadline(Deadline deadline) {
        currentDate = deadline;
    }

    public String getYear() {
        if (currentDate == null) {
            currentDate = PLACEHOLDER_DEADLINE;
        }
        return currentDate.getYear();
    }

    public Deadline getDeadline() {
        if (currentDate == null) {
            currentDate = PLACEHOLDER_DEADLINE;
        }
        return currentDate;
    }

    //@@ ChanChunCheong
    /**
     * Sorts the Task Book based on the method chosen.
     * Update the list.
     */
    public void sortTask(String method) {
        requireNonNull(method);
        SortTaskList sortList = new SortTaskList();
        ObservableList<Task> copyList = sortList.sortTask(obtainModifiableObservableList(), method);
        UniqueTaskList updateList = new UniqueTaskList();
        updateList.setTasks(copyList);
        tasks.setTasks(updateList);
    }

    // util methods

    @Override
    public String toString() {
        return tasks.asUnmodifiableObservableList().size() + " tasks";
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return tasks.asUnmodifiableObservableList();
    }

    public ObservableList<Task> obtainModifiableObservableList() {
        return tasks.obtainObservableList();
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
