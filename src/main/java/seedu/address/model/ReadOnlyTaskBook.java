package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.task.Task;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Task> getTaskList();

}
