package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Deadline;
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
    private String priorityLevel;
    @XmlElement(required = true)
    private String expectedNumOfHours;
    @XmlElement
    private String completedNumOfHours;
    @XmlElement(required = true)
    private boolean isCompleted;

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}

    /**
     * Constructs an {@code XmlAdaptedTask} with the given task details.
     */
    public XmlAdaptedTask(String deadline, String title, String description, String priorityLevel,
                          String expectedNumOfHours, String completedNumOfHours, boolean isCompleted) {
        this.deadline = deadline;
        this.title = title;
        this.description = description;
        this.priorityLevel = priorityLevel;
        this.expectedNumOfHours = expectedNumOfHours;
        //this.completedNumOfHours = completedNumOfHours;
        //this.isCompleted = isCompleted;
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedTask(Task source) {
        deadline = source.getDeadline().toString();
        title = source.getTitle();
        description = source.getDescription();
        priorityLevel = source.getPriorityLevel().toString();
        expectedNumOfHours = Integer.toString(source.getExpectedNumOfHours());
        completedNumOfHours = Integer.toString(source.getCompletedNumOfHours());
        isCompleted = source.isCompleted();
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        if (deadline == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Deadline.class.getSimpleName()));
        }
        if (!Deadline.isValidDeadline(deadline)) {
            throw new IllegalValueException(Deadline.MESSAGE_DEADLINE_CONSTRAINTS);
        }
        final Deadline modelDeadline = new Deadline(deadline);

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Title"));
        }
        final String modelTitle = title;

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Description"));
        }
        final String modelDescription = description;

        if (priorityLevel == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PriorityLevel.class.getSimpleName()));
        }
        if (!PriorityLevel.isValidPriorityLevel(priorityLevel)) {
            throw new IllegalValueException(PriorityLevel.MESSAGE_PRIORITY_CONSTRAINTS);
        }
        final PriorityLevel modelPriority = new PriorityLevel(priorityLevel);

        if (expectedNumOfHours == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "Expected number of hours expected to complete"));
        }
        final int modelExpectedNumOfHours = Integer.parseInt(expectedNumOfHours);

        final int modelCompletedNumOfHours;
        if (completedNumOfHours == null) {
            //throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
            //        "Number of hours taken to complete"));
            modelCompletedNumOfHours = 0;
        } else {
            modelCompletedNumOfHours = Integer.parseInt(completedNumOfHours);
        }

        //Boolean cannot be checked for null --> if (isCompleted == null)
        final boolean modelIsCompleted = isCompleted;

        return new Task(modelDeadline, modelTitle, modelDescription, modelPriority, modelExpectedNumOfHours,
                modelCompletedNumOfHours, modelIsCompleted);
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
                && Objects.equals(priorityLevel, otherTask.priorityLevel);

    }
}
