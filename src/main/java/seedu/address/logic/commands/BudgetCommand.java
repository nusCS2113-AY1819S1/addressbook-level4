package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLUB_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPECTED_TURNOUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_OF_EVENTS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.budgetelements.ClubBudgetElements;

public class BudgetCommand extends Command{
    public static final String COMMAND_WORD = "budget";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Submits the data for calculating the budget for a club. "
            + "Parameters: "
            + PREFIX_CLUB_NAME + "CLUB NAME "
            + PREFIX_EXPECTED_TURNOUT + "EXPECTED TURNOUT "
            + PREFIX_NUMBER_OF_EVENTS + "NUMBER OF EVENTS "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLUB_NAME + "Computing Club "
            + PREFIX_EXPECTED_TURNOUT + "200 "
            + PREFIX_NUMBER_OF_EVENTS + "5 ";

    public static final String MESSAGE_SUCCESS = "Data submitted: %1$s";
    public static final String MESSAGE_DUPLICATE_CLUB = "This club's data already exists in the address book";


    private final ClubBudgetElements toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public BudgetCommand(ClubBudgetElements club) {
        requireNonNull(club);
        toAdd = club;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasClub(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLUB);
        }

        model.addClub(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BudgetCommand // instanceof handles nulls
                && toAdd.equals(((BudgetCommand) other).toAdd));
    }
}
