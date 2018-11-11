package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.SelectTagCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

//@@author ChanChunCheong
/**
 * Selects a date as a deadline for tasks to be added to
 */
public class SelectTagCommand extends Command implements CommandParser {
    public static final String COMMAND_WORD = "select_tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Selects a tag. "
            + "Parameters: "
            + PREFIX_TAG + "TAG \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TAG + "friend ";

    public static final String MESSAGE_SUCCESS = "New tag selected: %1$s";

    private final Tag selectedTag;

    /**
     * Creates a SelectDeadline to select the specified {@code Deadline}
     */
    public SelectTagCommand (Tag tag) {
        requireNonNull(tag);
        selectedTag = tag;
    }

    public SelectTagCommand() {
        selectedTag = null;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.selectTag(selectedTag);
        model.commitTaskBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, selectedTag.toString()));
    }

    /*
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectDeadlineCommand // instanceof handles nulls
                && selectedTag.equals(((SelectDeadlineCommand) other).selectedTag));
    }
    */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectTagCommand // instanceof handles nulls
                && selectedTag.equals(((SelectTagCommand) other).selectedTag));
    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new SelectTagCommandParser().parse(arguments);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
