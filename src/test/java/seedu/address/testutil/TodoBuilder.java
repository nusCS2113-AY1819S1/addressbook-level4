package seedu.address.testutil;

import seedu.address.model.todo.Content;
import seedu.address.model.todo.Title;
import seedu.address.model.todo.Todo;

//@@author linnnruoo
/**
 * A utility class to help with building Todo objects.
 */
public class TodoBuilder {

    public static final String DEFAULT_CONTENT = "Eat my own dog food which is not very nice. I hate it so much.";
    public static final String DEFAULT_TITLE = "The arts of debugging";

    private Content content;
    private Title title;

    public TodoBuilder() {
        content = new Content(DEFAULT_CONTENT);
        title = new Title(DEFAULT_TITLE);
    }

    /**
     * Initializes the TodoBuilder with the data of {@code todoToCopy}.
     */
    public TodoBuilder(Todo todoToCopy) {
        content = todoToCopy.getContent();
        title = todoToCopy.getTitle();
    }

    /**
     * Sets the {@code Content} of the {@code Todo} that we are building.
     */
    public TodoBuilder withContent(String content) {
        this.content = new Content(content);
        return this;
    }

    /**
     * Sets the {@code Title} of the {@code Todo} that we are building.
     */
    public TodoBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    public Todo build() {
        return new Todo(title, content);
    }

}
