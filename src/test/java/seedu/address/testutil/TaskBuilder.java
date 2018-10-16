package seedu.address.testutil;

import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskModule;
import seedu.address.model.task.TaskName;
import seedu.address.model.task.TaskPriority;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_NAME = "Milestone";
    public static final String DEFAULT_MODULE = "CS2113";
    public static final String DEFAULT_DATE = "31-12";
    public static final String DEFAULT_PRIORITY = "3";

    private TaskName name;
    private TaskModule module;
    private TaskDate date;
    private TaskPriority priority;

    public TaskBuilder() {
        name = new TaskName(DEFAULT_NAME);
        module = new TaskModule(DEFAULT_MODULE);
        date = new TaskDate(DEFAULT_DATE);
        priority = new TaskPriority(DEFAULT_PRIORITY);
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        name = taskToCopy.getName();
        module = taskToCopy.getModule();
        date = taskToCopy.getDate();
        priority = taskToCopy.getPriority();
    }

    /**
     * Sets the {@code TaskName} of the {@code Task} that we are building.
     */
    public TaskBuilder withName(String name) {
        this.name = new TaskName(name);
        return this;
    }

    /**
     * Sets the {@code TaskModule} of the {@code Task} that we are building.
     */
    public TaskBuilder withModule(String module) {
        this.module = new TaskModule(module);
        return this;
    }

    /**
     * Sets the {@code TaskDate} of the {@code Task} that we are building.
     */
    public TaskBuilder withDate(String date) {
        this.date = new TaskDate(date);
        return this;
    }

    /**
     * Sets the {@code TaskPriority} of the {@code Task} that we are building.
     */
    public TaskBuilder withPriority(String priority) {
        this.priority = new TaskPriority(priority);
        return this;
    }

    public Task build() {
        return new Task(name, module, date, priority);
    }
}
