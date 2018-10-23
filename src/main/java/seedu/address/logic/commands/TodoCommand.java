package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.todo.Todo;

//@@author linnnruoo
/**
 * Add todo tasks
 */
public class TodoCommand extends Command {
    public static final String COMMAND_WORD = "todo";
    public static final String COMMAND_ALIAS = "td";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a todo task for JitHub users. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE" + " "
            + PREFIX_CONTENT + "CONTENT" + " "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Do Unit Tests" + " "
            + PREFIX_CONTENT + "write tests to test these shitty codes";

    public static final String MESSAGE_SUCCESS = "New todo task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TODO = "This task has already been created in your JitHub";

    private final Todo todo;

    /**
     * Creates a TodoCommand to add the specified {@code Todo}
     */
    public TodoCommand(Todo todoTask) {
        requireNonNull(todoTask);
        todo = todoTask;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasTodo(todo)) {
            throw new CommandException(MESSAGE_DUPLICATE_TODO);
        }

        model.addTodo(todo);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, todo));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TodoCommand // instanceof handles nulls
                && todo.equals(((TodoCommand) other).todo));
    }
}
