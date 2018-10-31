package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Body;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    @XmlElement(required = true)
    private String taskName;
    @XmlElement(required = true)
    private String body;
    @XmlElement(required = true)
    private String startDateTime;
    @XmlElement(required = true)
    private String endDateTime;
    @XmlElement(required = true)
    private String priority;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}

    /**
     * Constructs an {@code XmlAdaptedTask} with the given task details.
     */
    public XmlAdaptedTask(String taskName, String body, String startDateTime, String endDateTime, String priority,
                          List<XmlAdaptedTag> tagged) {
        this.taskName = taskName;
        this.body = body;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.priority = priority;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(Task source) {
        taskName = source.getTaskName().fullName;
        body = source.getBody().bodyString;
        startDateTime = source.getStartDateTime().dateTimeString;
        endDateTime = source.getEndDateTime().dateTimeString;
        priority = source.getPriority().priorityString;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }

        if (taskName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TaskName.class.getSimpleName()));
        }
        if (!TaskName.isValidName(taskName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final TaskName modelTaskName = new TaskName(taskName);

        if (body == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Body.class.getSimpleName()));
        }
        if (!Body.isValidBody(body)) {
            throw new IllegalValueException(Body.MESSAGE_BODY_CONSTRAINTS);
        }
        final Body modelBody = new Body(body);

        if (startDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(startDateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
        }
        final DateTime modelStartDateTime = new DateTime(startDateTime);

        if (endDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(endDateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
        }
        final DateTime modelEndDateTime = new DateTime(endDateTime);

        if (priority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Priority.class.getSimpleName()));
        }
        if (!Priority.isValidPriority(priority)) {
            throw new IllegalValueException(Priority.MESSAGE_PRIORITY_CONSTRAINTS);
        }
        final Priority modelPriority = new Priority(priority);

        final Set<Tag> modelTags = new HashSet<>(taskTags);
        return new Task(modelTaskName, modelBody, modelStartDateTime, modelEndDateTime, modelPriority, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTask)) {
            return false;
        }

        XmlAdaptedTask otherTask = (XmlAdaptedTask) other;
        return Objects.equals(taskName, otherTask.taskName)
                && Objects.equals(body, otherTask.body)
                && Objects.equals(startDateTime, otherTask.startDateTime)
                && Objects.equals(endDateTime, otherTask.endDateTime)
                && Objects.equals(priority, otherTask.priority)
                && tagged.equals(otherTask.tagged);
    }
}
