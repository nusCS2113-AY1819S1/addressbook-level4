package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTodoList;

/** Indicates the TodoList in the model has changed*/
public class TodoListChangedEvent extends BaseEvent {

    public final ReadOnlyTodoList data;

    public TodoListChangedEvent(ReadOnlyTodoList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size();
    }
}
