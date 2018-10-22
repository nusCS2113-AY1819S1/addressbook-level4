package seedu.address;

import seedu.address.model.task.PriorityLevel;
import seedu.address.model.task.Task;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_DEADLINE = "1/1";
    public static final String DEFAULT_TITLE = "Complete code refactoring";
    public static final String DEFAULT_DESCRIPTION = "refer to notes";
    public static final String DEFAULT_PRIORITY = "high";
    public static final boolean DEFAULT_COMPLETE = false;

    private String deadline;
    private String title;
    private String description;
    private PriorityLevel priority;
    private boolean isCompleted;

    public TaskBuilder() {
        this.deadline = DEFAULT_DEADLINE;
        this.title = DEFAULT_TITLE;
        this.description = DEFAULT_DESCRIPTION;
        this.priority = new PriorityLevel(DEFAULT_PRIORITY);
        this.isCompleted = DEFAULT_COMPLETE;
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        this.deadline = taskToCopy.getDeadline();
        this.title = taskToCopy.getTitle();
        this.description = taskToCopy.getDescription();
        this.priority = taskToCopy.getPriorityLevel();
        this.isCompleted = taskToCopy.isCompleted();
    }
    /**
     * Sets the {@code Deadline} of the {@code Task} that we are building.
     */
    public TaskBuilder withDeadline(String deadline) {
        this.deadline = deadline;
        return this;
    }

    /**
     * Sets the Title of the {@code Task} that we are building.
     */
    public TaskBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Task} that we are building.
     */
    public TaskBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Task} that we are building.
     */
    public TaskBuilder withPriority(String priority) {
        this.priority = new PriorityLevel(priority);
        return this;
    }

    /**
     * Sets the completed status of the {@code Task} that we are building.
     */
    public TaskBuilder withCompleted(boolean completed) {
        this.isCompleted = completed;
        return this;
    }

    public Task build() {
        return new Task(deadline, title, description, priority, isCompleted);
    }
}
