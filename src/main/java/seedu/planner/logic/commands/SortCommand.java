package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;

public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final int ONLY_CATEGORY_SPECIFIED = 1;
    public static final int CATEGORY_AND_ORDER_SPECIFIED = 2;
    public static final String DESCENDING_CONDITION = "desc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all records in the currently displayed list"
            + "by the specified category.\n"
            + "Parameters: [KEYWORD]\n"
            + "Example: " + COMMAND_WORD + " name";

    public static final String MESSAGE_SUCCESS = "Records sorted!";

    private final String category;
    private final Boolean ascending;

    public SortCommand(String category, Boolean ascending) {
            this.category = category;
            this.ascending = ascending;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.sortFilteredRecordList(category, ascending);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && category.equals(((SortCommand) other).category)
                    && ascending.equals(((SortCommand) other).ascending)); // state check
    }
}
