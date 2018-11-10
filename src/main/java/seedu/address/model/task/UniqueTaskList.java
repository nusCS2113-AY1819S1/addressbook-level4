package seedu.address.model.task;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.expections.DuplicateTaskException;
import seedu.address.model.task.expections.TaskNotFoundException;

//@@author luhan02
/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 * A task is considered unique by comparing using {@code Task#isSameTask(Task)}. As such, adding and updating of
 * tasks uses Task#isSameTask(Task) for equality so as to ensure that the task being added or updated is
 * unique in terms of identity in the UniqueTaskList.
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
        sortPriority(internalList);
    }

    /**
     * Replaces the task {@code target} in the list with {@code updatedTask}.
     * {@code target} must exist in the list.
     * The task identity of {@code updatedTask} must not be the same as another existing task in the list.
     */
    public void setTask(Task target, Task updatedTask) {
        requireAllNonNull(target, updatedTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }

        if (!target.isSameTask(updatedTask) && contains(updatedTask)) {
            throw new DuplicateTaskException();
        }

        internalList.set(index, updatedTask);
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

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    /**
     * List of tasks to be sorted according to its deadline(end DateTime)
     */
    public void sort() {
        internalList.sort(((o1, o2) -> {
            String o1DateStr = this.trimTime(o1.getEndDateTime().dateTimeString);
            int o1Month = this.getMonth(o1DateStr);
            int o1Day = this.getDay(o1DateStr);

            String o2DateStr = this.trimTime(o2.getEndDateTime().dateTimeString);
            int o2Month = this.getMonth(o2DateStr);
            int o2Day = this.getDay(o2DateStr);

            if (o1Month > o2Month) {
                return 1;
            }
            if (o1Month == o2Month) {
                if (o1Day == o2Day) {
                    return 0;
                } else if (o1Day > o2Day) {
                    return 1;
                } else {
                    return -1;
                }
            }
            return -1;
        }));
    }

    private String trimTime(String datetimeStr) {
        int underScoreIndex = datetimeStr.indexOf("_");
        return datetimeStr.substring(0, underScoreIndex);
    }

    private int getMonth(String dateStr) {
        return Integer.parseInt(dateStr.split("\\/")[1]);
    }

    private int getDay(String dateStr) {
        return Integer.parseInt(dateStr.split("\\/")[0]);
    }

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

    /**
     * @param internalList list of tasks to be sorted according to its priority
     */
    private void sortPriority(ObservableList<Task> internalList) {
        FXCollections.sort(internalList, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                String p1 = t1.getPriority().priorityString;
                String p2 = t2.getPriority().priorityString;

                if (p1.equals(p2)) {
                    return 0;
                }
                if ((p1.equals("HIGH") || p1.equals("high")) && ((p2.equals("MED") || p2.equals("med"))
                        || (p2.equals("LOW") || p2.equals("low")))) {
                    return -1;
                }
                if ((p1.equals("MED") || p1.equals("med")) && (p2.equals("LOW") || p2.equals("low"))) {
                    return -1;
                }
                return 2;
            }
        });
    }

}
