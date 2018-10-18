package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;

/**
 * Sorts all records in the current displayed list by a specified category and/or order.
 * Keyword matching is case insensitive and regardless of order of entry.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final int ONLY_CATEGORY_OR_ORDER_SPECIFIED = 1;
    public static final int CATEGORY_AND_ORDER_SPECIFIED = 2;
    public static final String DESCENDING_CONDITION = "desc";
    public static final String ASCENDING_CONDITION = "asc";

    public static final String MESSAGE_SUCCESS = "Records sorted by ";

    public static final String CATEGORY_NAME = "name";
    public static final String CATEGORY_MONEYFLOW = "moneyflow";
    public static final String CATEGORY_MONEY = "money";
    public static final String CATEGORY_DATE = "date";

    public static final String ORDER_ASCENDING = "in ascending order";
    public static final String ORDER_DESCENDING = "in descending order";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all records in the currently displayed list "
            + "by the specified category and order.\n"
            + "Parameters: [CATEGORY] [ORDER]\n"
            + "Example: " + COMMAND_WORD +  " " + CATEGORY_NAME + " " + DESCENDING_CONDITION;

    private final String category;
    private final Boolean ascending;

    public static final Set<String> CATEGORY_SET = new HashSet<>(Arrays.asList(CATEGORY_NAME, CATEGORY_MONEYFLOW,
            CATEGORY_MONEY, CATEGORY_DATE));
    public static final Set<String> ORDER_SET = new HashSet<>(Arrays.asList(DESCENDING_CONDITION, ASCENDING_CONDITION));

    public SortCommand(String category, Boolean ascending) {
            this.category = category;
            this.ascending = ascending;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.sortFilteredRecordList(category, ascending);
        String returnMessageCategory = category;
        String returnMessageOrder = ascending ? ORDER_ASCENDING : ORDER_DESCENDING;
        return new CommandResult(MESSAGE_SUCCESS + returnMessageCategory + " " +
                returnMessageOrder + ".\n");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && category.equals(((SortCommand) other).category)
                    && ascending.equals(((SortCommand) other).ascending)); // state check
    }
}
