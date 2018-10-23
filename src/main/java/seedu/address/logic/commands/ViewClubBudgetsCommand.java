package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLUB_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLUB_BUDGETS;

import java.util.List;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.budgetelements.ClubName;
import seedu.address.model.clubbudget.FinalClubBudget;

/**
 * Lists all persons in the address book to the user.
 */
public class ViewClubBudgetsCommand extends Command {

    public static final String COMMAND_WORD = "viewbudget";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the club's budget. "
            + "Parameters: "
            + PREFIX_CLUB_NAME + "CLUB NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLUB_NAME + "Computing Club ";

    public static final String MESSAGE_SUCCESS = "Club budget is: %1$s";
    public static final String MESSAGE_INVALID_CLUB = "This club's budget does not exist in the address book";

    private final ClubName toShow;

    /**
     * Creates a ViewClubBudgetCommand to view the specified {@code FinalClubBudget}
     */
    public ViewClubBudgetsCommand(ClubName club) {
        requireNonNull(club);
        toShow = club;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        /**if (!(model.hasClubBudget(toShow))) {
            throw new CommandException(MESSAGE_INVALID_CLUB);
        }*/


        List<FinalClubBudget> listOfBudgets = model.getFilteredClubBudgetsList();

        int i;

        String budgetToShow = null;

        for (i = 0; i < listOfBudgets.size(); i++) {

            FinalClubBudget currentBudget = listOfBudgets.get(i);

            if (currentBudget.getClubName().equals(toShow)) {
                budgetToShow = Integer.toString(currentBudget.getAllocatedBudget());
                System.out.println("the budget is " + budgetToShow);
                return new CommandResult(String.format(MESSAGE_SUCCESS, budgetToShow));
            }
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, budgetToShow));
    }
}
