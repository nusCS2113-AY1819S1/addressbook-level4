package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Task in the task list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    private final TaskName taskName;
    private final Body body;
    private final DateTime startDateTime;
    private final DateTime endDateTime;
    private final Priority priority;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Task(TaskName taskName, Body body, DateTime startDateTime, DateTime endDateTime,
                Priority priority, Set<Tag> tags) {
        requireAllNonNull(taskName, body, startDateTime, endDateTime, priority, tags);
        this.taskName = taskName;
        this.body = body;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.priority = priority;
        this.tags.addAll(tags);
    }

    public TaskName getTaskName() {
        return taskName;
    }

    public Body getBody() {
        return body;
    }

    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    public Priority getPriority() {
        return priority;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskName, body, startDateTime, endDateTime, priority, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTaskName())
                .append(" Body: ")
                .append(getBody())
                .append(" Start DateTime: ")
                .append(getStartDateTime())
                .append(" End DateTime: ")
                .append(getEndDateTime())
                .append(" priority: ")
                .append(getPriority())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
