package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.commons.core.index.Index;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddTagCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;

//@@author ChanChunCheong
/**
 * Adds a tag to a task in the taskbook
 */
public class AddTagCommand extends Command implements CommandParser {
    public static final String COMMAND_WORD = "add_tag";
    public static final String MESSAGE_SUCCESS = "New tag added to task [%1$s]: %2$s";
    public static final String MESSAGE_TASK_NOT_FOUND = "This task does not exist in the task book";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds tag(s) to selected task. "
            + "Parameters: "
            + PREFIX_INDEX + "INDEX "
            + PREFIX_TAG + "TAG\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_TAG + "Sport";

    private final Index index;
    private final Tag tag;

    /**
     * Creates a AddTagCommand to serve the purpose of the LogicManager
     */
    public AddTagCommand() {
        index = null;
        tag = null;
    }

    /**
     * Creates a AddTagCommand to add the specified {@code Tag}
     */
    public AddTagCommand(Index index, Tag tag) {
        requireNonNull(index);
        requireNonNull(tag);
        this.index = index;
        this.tag = tag;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_TASK_NOT_FOUND);
        }

        Task taskToAdd = lastShownList.get(index.getZeroBased());
        model.addTag(taskToAdd, tag);
        model.commitTaskBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, taskToAdd.getTitle(), tag.toString()));
    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new AddTagCommandParser().parse(arguments);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
