//@@author arty9
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.SortComparator;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 * A task is considered unique by comparing using {@code Task#isSameTask(Task)}. As such, adding and updating of
 * tasks uses Task#isSameTask(Task) for equality so as to ensure that the task being added or updated is
 * unique in terms of identity in the UniqueTaskList. However, the removal of a task uses Task#equals(Object) so
 * as to ensure that the task with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#isSameTask(Task)
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(Task toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTask);
    }

    /**
     * Adds a task to the list.
     * The task must not already exist in the list.
     */
    public void add(Task toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the list.
     * The task identity of {@code editedTask} must not be the same as another existing task in the list.
     */
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }

        if (!target.isSameTask(editedTask) && contains(editedTask)) {
            throw new DuplicateTaskException();
        }

        internalList.set(index, editedTask);
    }

    /**
     * Removes the equivalent task from the list.
     * The task must exist in the list.
     */
    public void remove(Task toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TaskNotFoundException();
        }
    }

    public void setTasks(UniqueTaskList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setTasks(List<Task> tasks) {
        requireAllNonNull(tasks);
        if (!tasksAreUnique(tasks)) {
            throw new DuplicateTaskException();
        }

        internalList.setAll(tasks);
    }

    //@@author SHININGGGG
    /**
     * Get the tasks record on a particular day.
     */
    public String checkTasksRecordsOnParticularDay (String particularDate) {

        String records;
        String theDate;
        int index = 0;
        StringBuilder it = new StringBuilder();
        StringBuilder builder = new StringBuilder();
        boolean hasTask = false;
        it.append("Here's all the task due on this day:\n");
        while (index < internalList.size()) {
            theDate = internalList.get(index).getDate().value + "-2018";
            if (theDate.equals(particularDate)) {
                it.append("Task name: " + internalList.get(index).getName().fullName + ", ")
                        .append("Module: " + internalList.get(index).getModule().value + ", ")
                        .append("Date: " + internalList.get(index).getDate().value + ", ")
                        .append("Priority: " + internalList.get(index).getPriority().value + ", ");
                if (internalList.get(index).getComplete()) {
                    it.append("Status: " + "Completed\n");
                } else {
                    it.append("Status: " + "Uncompleted\n");
                }
                hasTask = true;
            }
            index++;
        }
        if (hasTask) {
            builder.append("Here's all the task due on this day:\n")
                    .append(it.toString());
        } else {
            builder.append("There is no task due on this day.\n");
        }
        records = builder.toString();
        return records;
    }
    //@@author

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    //@@author XiaoYunhan
    /**
     * Sort the to-do list based on their deadline.
     */
    public void sortDate() {
        Collections.sort(internalList, Comparator.comparing(Task::getMonth).thenComparing(Task::getDay));
    }

    /**
     * Sort the to-do list based on their module code.
     */
    public void sortModule() {
        internalList.sort(SortComparator.compareModule());
    }

    /**
     * Sort the to-do list based on their priority.
     */
    public void sortPriority() {
        internalList.sort(SortComparator.comparePriority());
    }

    /**
     * Reverse the sequence of to-do list.
     */
    public void reverseTask() {
        Collections.reverse(internalList);
    }

    /**
     * Sort the to-do list based on their task name.
     */
    public void sortDefault() {
        internalList.sort(SortComparator.compareName());
    }
    //@@author

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                        && internalList.equals(((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code tasks} contains only unique tasks.
     */
    private boolean tasksAreUnique(List<Task> tasks) {
        for (int i = 0; i < tasks.size() - 1; i++) {
            for (int j = i + 1; j < tasks.size(); j++) {
                if (tasks.get(i).isSameTask(tasks.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
