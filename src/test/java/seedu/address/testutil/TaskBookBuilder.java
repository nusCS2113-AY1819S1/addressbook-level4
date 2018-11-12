package seedu.address.testutil;

import seedu.address.model.TaskBook;
import seedu.address.model.task.Task;

/**
 * A utility class to help with building Taskbook objects.
 * Example usage: <br>
 *     {@code TaskBook tb = new TaskBookBuilder().withTask("John", "Doe").build();}
 */
public class TaskBookBuilder {
    private TaskBook taskBook;

    public TaskBookBuilder() {
        taskBook = new TaskBook();
    }

    public TaskBookBuilder(TaskBook taskBook) {
        this.taskBook = taskBook;
    }

    /**
     * Adds a new {@code Task} to the {@code TaskBook} that we are building.
     */
    public TaskBookBuilder withTask(Task task) {
        taskBook.addTask(task);
        return this;
    }

    public TaskBook build() {
        return taskBook;
    }
}
