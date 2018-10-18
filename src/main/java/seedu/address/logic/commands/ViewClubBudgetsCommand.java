package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLUB_BUDGETS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ViewClubBudgetsCommand extends Command {

    public static final String COMMAND_WORD = "viewbudget";

    public static final String MESSAGE_SUCCESS = "Listed all club budgets";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredClubBudgetsList(PREDICATE_SHOW_ALL_CLUB_BUDGETS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
