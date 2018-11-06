package seedu.address.commons.util;

import java.util.Comparator;

import seedu.address.model.task.Task;

/**
 * Comparators to sort {@code Task}
 */
public class SortComparator {

    /*
     *Compare two tasks based on their module code
     */
    public static Comparator<Task>compareModule () {
        return Comparator.comparing(a -> a.getModule().toString());
    }

    /*
     *Compare two tasks based on their priority
     */
    public static Comparator<Task>comparePriority() {
        return Comparator.comparing(a -> a.getPriority().toString());
    }

    /*
     *Compare two tasks based on their task name
     */
    public static Comparator<Task>compareName() {
        return Comparator.comparing(Task::getNameString);
    }
}
