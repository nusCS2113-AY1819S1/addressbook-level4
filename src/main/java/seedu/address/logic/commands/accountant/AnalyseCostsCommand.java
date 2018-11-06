package seedu.address.logic.commands.accountant;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.drink.Price;
import seedu.address.model.user.accountant.AccountantModel;

/**
 * Analyses total costs of transactions.
 */
public class AnalyseCostsCommand extends Command {
    public static final String COMMAND_WORD = "analyseCosts";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Analyse the total cost recorded in Drink I/O.";

    public static final String MESSAGE_SUCCESS = "Total costs: $%1$s";

    /**
     * Creates an AnalyseCostsCommand to compute total costs incurred.
     */
    public AnalyseCostsCommand() {
        // TODO: optional time period in the future
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireAllNonNull(model);
        assert model instanceof AccountantModel;
        AccountantModel accountantModel = (AccountantModel) model;
        Price totalCosts = accountantModel.analyseCosts();

        return new CommandResult(String.format(MESSAGE_SUCCESS, totalCosts));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AnalyseCostsCommand); // instanceof handles nulls;
    }
}
