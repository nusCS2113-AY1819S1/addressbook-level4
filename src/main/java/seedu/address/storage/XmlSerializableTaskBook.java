package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.TaskBook;
import seedu.address.model.task.Task;

/**
 * An Immutable TaskBook that is serializable to XML format
 */
@XmlRootElement(name = "taskBook")
public class XmlSerializableTaskBook {

    public static final String MESSAGE_DUPLICATE_TASK = "Tasks list contains duplicate task(s).";

    @XmlElement
    private List<XmlAdaptedTask> tasks;

    /**
     * Creates an empty XmlSerializableTaskBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableTaskBook() {
        tasks = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableTaskBook(ReadOnlyTaskBook src) {
        this();
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
    }

    /**
     * Converts this taskBook into the model's {@code TaskBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     *                               {@code XmlAdaptedTask}.
     */
    public TaskBook toModelType() throws IllegalValueException {
        TaskBook taskBook = new TaskBook();
        for (XmlAdaptedTask t : tasks) {
            Task task = t.toModelType();
            if (taskBook.hasTask(task)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TASK);
            }
            taskBook.addTask(task);
        }
        return taskBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableTaskBook)) {
            return false;
        }
        return tasks.equals(((XmlSerializableTaskBook) other).tasks);
    }
}
