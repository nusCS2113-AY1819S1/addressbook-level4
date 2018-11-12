package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SCHEDULES;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons, schedules and expenses.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all employees, schedules, recruitment posts"
            + " and expenses.";

    private static final String BY_ASCENDING = "asc";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredExpensesList(PREDICATE_SHOW_ALL_EXPENSES);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
