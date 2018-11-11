package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.distributor.DistributorProduct;
import seedu.address.model.saleshistory.SalesHistory;

/**
 * This command displays all the reminders in the {@link SalesHistory}.
 */
public class ListDistributorProductsCommand extends Command {

    public static final String COMMAND_WORD = "listdistributorproducts";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all products that the given distributor is recorded to carry.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";
    public static final String NO_DIST_PRODS_MESSAGE = "No products have been recorded for this distributor yet";

    private final Index index;

    /**
     * @param index of the product in the filtered distributor list to list products of
    **/
    public ListDistributorProductsCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Distributor> lastShownList = model.getFilteredDistributorList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DIST_DISPLAYED_INDEX);
        }

        Distributor distributorToShow = lastShownList.get(index.getZeroBased());
        Set<DistributorProduct> distProdsSet = distributorToShow.getDistProds();

        ArrayList<DistributorProduct> distprodsList = new ArrayList<>(distProdsSet);

        if (distprodsList.size() == 0) {
            return new CommandResult(NO_DIST_PRODS_MESSAGE);
        }

        StringBuilder allDistProds = new StringBuilder();

        allDistProds.append("Products that " + distributorToShow.getDistName().toString() + " carries:");

        for (DistributorProduct distributorProduct : distprodsList) {
            allDistProds.append(distributorProduct.toString());
        }

        return new CommandResult(allDistProds.toString());
    }
}
