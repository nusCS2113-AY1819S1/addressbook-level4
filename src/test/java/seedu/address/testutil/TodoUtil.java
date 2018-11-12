package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.commands.TodoCommand;
import seedu.address.model.todo.Todo;

//@@author linnnruoo
/**
 * A utility class for Todo.
 */
public class TodoUtil {

    /**
     * Returns a todo command string for adding the {@code todo}.
     */
    public static String getTodoCommand(Todo todo) {
        return TodoCommand.COMMAND_WORD + " " + getTodoDetails(todo);
    }

    /**
     * Returns an add command alias string for adding the {@code todo}.
     */
    public static String getAddAlias(Todo todo) {
        return TodoCommand.COMMAND_ALIAS + " " + getTodoDetails(todo);
    }

    /**
     * Returns the part of command string for the given {@code todo}'s details.
     */
    public static String getTodoDetails(Todo todo) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + todo.getTitle().value + " ");
        sb.append(PREFIX_CONTENT + todo.getContent().value + " ");
        return sb.toString();
    }
}
