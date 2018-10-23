package seedu.address.model.todo.exceptions;

//@@author linnnruoo
/**
 * Signals that the operation will result in duplicate Todo (Todos are considered duplicates if they have the same
 * identity).
 */
public class DuplicateTodoException extends RuntimeException {
    public DuplicateTodoException() {
        super("Operation would result in duplicate todo tasks");
    }
}
