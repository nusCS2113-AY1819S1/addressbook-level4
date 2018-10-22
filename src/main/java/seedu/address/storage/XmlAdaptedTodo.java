package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.todo.Content;
import seedu.address.model.todo.Title;
import seedu.address.model.todo.Todo;

//@@author: linnnruoo
/**
 * JAXB-friendly version of the Todo.
 */
public class XmlAdaptedTodo {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Todo's %s field is missing!";

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String content;

    /**
     * Constructs an XmlAdaptedTodo.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTodo() {}

    /**
     * Constructs an {@code XmlAdaptedTodo} with the given todo task details.
     */
    public XmlAdaptedTodo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * Converts a given todo task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTodo
     */
    public XmlAdaptedTodo(Todo source) {
        title = source.getTitle().value;
        content = source.getContent().value;
    }

    /**
     * Converts this jaxb-friendly adapted todo object into the model's Todo object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted todo
     */
    public Todo toModelType() throws IllegalValueException {
        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_TITLE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        if (content == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Content.class.getSimpleName()));
        }
        if (!Content.isValidContent(content)) {
            throw new IllegalValueException(Content.MESSAGE_CONTENT_CONSTRAINTS);
        }
        final Content modelContent = new Content(content);

        return new Todo(modelTitle, modelContent);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTodo)) {
            return false;
        }

        XmlAdaptedTodo otherTodo = (XmlAdaptedTodo) other;
        return Objects.equals(title, otherTodo.title)
                && Objects.equals(content, otherTodo.content);
    }
}
