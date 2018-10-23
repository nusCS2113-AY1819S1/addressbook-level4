package seedu.address.model.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.todo.exceptions.DuplicateTodoException;
import seedu.address.model.todo.exceptions.TodoNotFoundException;

//@@author linnnruoo
/**
 * A list of todo tasks that enforces uniqueness between its elements and does not allow nulls.
 * A todo task is considered unique by comparing using {@code Todo#isSameTodo(Todo)}. As such, adding and updating of
 * todo tasks uses Todo#isSameTodo(Todo) for equality so as to ensure that the todo task being added or updated is
 * unique in terms of identity in the UniqueTodoList. However, the removal of a todo task uses Todo#equals(Object) so
 * as to ensure that the todo task with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Todo#isSameTodo(Todo)
 */
public class UniqueTodoList implements Iterable<Todo> {

    private final ObservableList<Todo> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent todo task as the given argument.
     */
    public boolean contains(Todo toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTodo);
    }

    /**
     * Adds a todo task to the list.
     * The todo task must not already exist in the list.
     */
    public void add(Todo toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTodoException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the todo task {@code target} in the list with {@code editedTodo}.
     * {@code target} must exist in the list.
     * The todo task content of {@code editedTodo} must not be the same as another existing todo task in the list.
     */
    public void setTodo(Todo target, Todo editedTodo) {
        requireAllNonNull(target, editedTodo);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TodoNotFoundException();
        }

        if (!target.isSameTodo(editedTodo) && contains(editedTodo)) {
            throw new DuplicateTodoException();
        }

        internalList.set(index, editedTodo);
    }

    /**
     * Removes the equivalent todo task from the list.
     * The todo task must exist in the list.
     */
    public void remove(Todo toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TodoNotFoundException();
        }
    }

    public void setTodos(UniqueTodoList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code todos}.
     * {@code todos} must not contain duplicate todos.
     */
    public void setTodos(List<Todo> todos) {
        requireAllNonNull(todos);
        if (!todosAreUnique(todos)) {
            throw new DuplicateTodoException();
        }

        internalList.setAll(todos);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Todo> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Todo> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTodoList // instanceof handles nulls
                        && internalList.equals(((UniqueTodoList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code todos} contains only unique todo tasks.
     */
    private boolean todosAreUnique(List<Todo> todos) {
        for (int i = 0; i < todos.size() - 1; i++) {
            for (int j = i + 1; j < todos.size(); j++) {
                if (todos.get(i).isSameTodo(todos.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
