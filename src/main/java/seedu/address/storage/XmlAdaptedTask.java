package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskModule;
import seedu.address.model.task.TaskName;
import seedu.address.model.task.TaskPriority;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String module;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String priority;


    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}

    /**
     * Constructs an {@code XmlAdaptedTask} with the given task details.
     */
    public XmlAdaptedTask(String name, String module, String date, String priority) {
        this.name = name;
        this.module = module;
        this.date = date;
        this.priority = priority;
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(Task source) {
        name = source.getName().fullName;
        module = source.getModule().value;
        date = source.getDate().value;
        priority = source.getPriority().value;
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskName.class.getSimpleName()));
        }
        if (!TaskName.isValidName(name)) {
            throw new IllegalValueException(TaskName.MESSAGE_NAME_CONSTRAINTS);
        }
        final TaskName modelName = new TaskName(name);

        if (module == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskModule.class.getSimpleName()));
        }
        if (!TaskModule.isValidModule(module)) {
            throw new IllegalValueException(TaskModule.MESSAGE_MODULE_CONSTRAINTS);
        }
        final TaskModule modelPhone = new TaskModule(module);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskDate.class.getSimpleName()));
        }
        if (!TaskDate.isValidDate(date)) {
            throw new IllegalValueException(TaskDate.MESSAGE_DATE_CONSTRAINTS);
        }
        final TaskDate modelEmail = new TaskDate(date);

        if (priority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskPriority.class.getSimpleName()));
        }
        if (!TaskPriority.isValidPriority(priority)) {
            throw new IllegalValueException(TaskPriority.MESSAGE_PRIORITY_CONSTRAINTS);
        }
        final TaskPriority modelAddress = new TaskPriority(priority);

        return new Task(modelName, modelPhone, modelEmail, modelAddress);
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
        return Objects.equals(name, otherTask.name)
                && Objects.equals(module, otherTask.module)
                && Objects.equals(date, otherTask.date)
                && Objects.equals(priority, otherTask.priority);
    }
}
