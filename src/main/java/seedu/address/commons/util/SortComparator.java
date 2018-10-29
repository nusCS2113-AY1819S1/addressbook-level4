package seedu.address.commons.util;

import seedu.address.model.task.Task;

import java.util.Comparator;

public class SortComparator {
    /*
     *Compare two tasks based on their deadlines
     */
    public static Comparator<Task> compareDeadlines = new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            int a = o1.getDate().value.substring(3, 5).compareTo(o2.getDate().value.substring(3, 5));
            if (a == 0) {
                return o1.getDate().value.substring(0, 2).compareTo(o2.getDate().value.substring(0, 2));
            }
            else {
                return a;
            }
        }
    };
    /*
     *Compare two tasks based on their module codes
     */
    public static Comparator<Task>compareModule = (Task a, Task b) -> a.getModule().value.compareTo(b.getModule().value);
    /*
     *Compare two tasks based on their priority
     */
    public static Comparator<Task>comparePriority = (Task a, Task b) -> a.getPriority().value.compareTo(b.getPriority().value);

}
