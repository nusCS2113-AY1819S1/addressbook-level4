package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.task.Body;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {
    public static final String DEFAULT_TASKNAME = "Submit Assignment";
    public static final String DEFAULT_BODY = "CG2027 Assign2";
    public static final String DEFAULT_START_DATETIME = "09/10";
    public static final String DEFAULT_END_DATETIME = "16/10_14:00";
    public static final String DEFAULT_PRIORITY = "HIGH";

    private TaskName taskName;
    private Body body;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private Priority priority;
    private Set<Tag> tags;

    public TaskBuilder() {
        taskName = new TaskName(DEFAULT_TASKNAME);
        body = new Body(DEFAULT_BODY);
        startDateTime = new DateTime(DEFAULT_START_DATETIME);
        endDateTime = new DateTime(DEFAULT_END_DATETIME);
        priority = new Priority(DEFAULT_PRIORITY);
        tags = new HashSet<>();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        taskName = taskToCopy.getTaskName();
        body = taskToCopy.getBody();
        startDateTime = taskToCopy.getStartDateTime();
        endDateTime = taskToCopy.getEndDateTime();
        priority = taskToCopy.getPriority();
        tags = new HashSet<>(taskToCopy.getTags());
    }

    /**
     * Sets the {@code TaskName} of the {@code Task} that we are building.
     */
    public TaskBuilder withTaskName(String taskName) {
        this.taskName = new TaskName(taskName);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Task} that we are building.
     */
    public TaskBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Body} of the {@code Task} that we are building.
     */
    public TaskBuilder withBody(String body) {
        this.body = new Body(body);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withStartDateTime(String startDateTime) {
        this.startDateTime = new DateTime(startDateTime);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withEndDateTime(String endDateTime) {
        this.endDateTime = new DateTime(endDateTime);
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Task} that we are building.
     */
    public TaskBuilder withPriority(String priority) {
        this.priority = new Priority(priority);
        return this;
    }

    public Task build() {
        return new Task(taskName, body, startDateTime, endDateTime, priority, tags);
    }
}
