package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.PriorityLevel;
import seedu.address.model.task.Task;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    @XmlElement(required = true)
    private String deadline;
    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    private String expectedNumOfHours;

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}

    /**
     * Constructs an {@code XmlAdaptedTask} with the given task details.
     */
    public XmlAdaptedTask(String deadline, String title, String description, String priority, String expectedNumOfHours) {
        this.deadline = deadline;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.expectedNumOfHours = expectedNumOfHours;
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedTask(Task source) {
        deadline = source.getDeadline();
        title = source.getTitle();
        description = source.getDescription();
        priority = source.getPriorityLevel().toString();
        expectedNumOfHours = Integer.toString(source.getExpectedNumOfHours());
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        if (deadline == null) {
            //TODO: Deadline.class.getSimpleName()
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Deadline"));
        }
        //        if (!Deadline.isValidDeadline(deadline)) {
        //            throw new IllegalValueException(Deadline.MESSAGE_DEADLINE_CONSTRAINTS);
        //        }
        //TODO: Replace deadline
        final String modelDeadline = deadline;

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Title"));
        }
        final String modelTitle = title;

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Description"));
        }
        final String modelDescription = description;

        if (priority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PriorityLevel.class.getSimpleName()));
        }
        if (!PriorityLevel.isValidPriorityLevel(priority)) {
            throw new IllegalValueException(PriorityLevel.MESSAGE_PRIORITY_CONSTRAINTS);
        }
        final PriorityLevel modelPriority = new PriorityLevel(priority);

        if (expectedNumOfHours == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "Expected number of expectedNumOfHours"));
        }
        final int modelExpectedNumOfHours = Integer.parseInt(expectedNumOfHours);

        return new Task(modelDeadline, modelTitle, modelDescription, modelPriority, modelExpectedNumOfHours);
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
        return Objects.equals(deadline, otherTask.deadline)
                && Objects.equals(title, otherTask.title)
                && Objects.equals(description, otherTask.description)
                && Objects.equals(priority, otherTask.priority);

    }
}
