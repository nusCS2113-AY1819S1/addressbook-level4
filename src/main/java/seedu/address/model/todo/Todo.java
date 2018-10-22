package seedu.address.model.todo;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

//@@author linnnruoo
/**
 * Represents a Todo task in the JitHub.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Todo {

    // Todo task description fields
    private final Title title;
    private final Content content;

    /**
     * Every field must be present and not null.
     */
    public Todo(Title title, Content content) {
        requireAllNonNull(title, content);
        this.title = title;
        this.content = content;
    }

    public Title getTitle() { return title; }
    public Content getContent() { return content; }

    /**
     * Returns true if both todo tasks of the same title have content field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameTodo(Todo otherTodo) {
        if (otherTodo == this) {
            return true;
        }

        return otherTodo != null
                && otherTodo.getTitle().equals(getTitle())
                && (otherTodo.getContent().equals(getContent()));
    }

    /**
     * Returns true if both todo tasks have the same title and content.
     * Represents a strong notion of equality
     */
    public boolean equals(Todo other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Todo)) {
            return false;
        }

        Todo otherTodo = (Todo) other;
        return otherTodo != null
                && otherTodo.getTitle().equals(getTitle())
                && (otherTodo.getContent().equals(getContent()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, content);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Title: ")
                .append(getContent())
                .append(" Content: ");
        return builder.toString();
    }
}
