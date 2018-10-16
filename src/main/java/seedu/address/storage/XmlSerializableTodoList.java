package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTodoList;
import seedu.address.model.TodoList;
import seedu.address.model.task.Task;

/**
 * An Immutable TodoList that is serializable to XML format
 */
@XmlRootElement(name = "todolist")
public class XmlSerializableTodoList {

    public static final String MESSAGE_DUPLICATE_TASK = "Tasks list contains duplicate task(s).";

    @XmlElement
    private List<XmlAdaptedTask> tasks;

    /**
     * Creates an empty XmlSerializableTodoList.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableTodoList() {
        tasks = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableTodoList(ReadOnlyTodoList src) {
        this();
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
    }

    /**
     * Converts this todolist into the model's {@code TodoList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedTask}.
     */
    public TodoList toModelType() throws IllegalValueException {
        TodoList todoList = new TodoList();
        for (XmlAdaptedTask t : tasks) {
            Task task = t.toModelType();
            if (todoList.hasTask(task)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TASK);
            }
            todoList.addTask(task);
        }
        return todoList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableTodoList)) {
            return false;
        }
        return tasks.equals(((XmlSerializableTodoList) other).tasks);
    }
}
