package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIST_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIST_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIST_PROD;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.distributor.Distributor;

/**
 * Adds a product to the address book.
 */
public class AddDistributorCommand extends Command {

    public static final String COMMAND_WORD = "adddistributor";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a distributor to the inventarie. "
            + "Parameters: "
            + PREFIX_DIST_NAME + "DISTRIBUTOR NAME "
            + PREFIX_DIST_PHONE + "DISTRIBUTOR PHONE "
            + PREFIX_DIST_PROD + "DISTRIBUTOR PRODUCTS"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DIST_NAME + "Ah Bee Distributors"
            + PREFIX_DIST_PHONE + "60123456 "
            + PREFIX_DIST_PROD + "Apple"
            + PREFIX_DIST_PROD + "Banana";

    public static final String MESSAGE_SUCCESS = "New distributor added: %1$s";
    public static final String MESSAGE_DUPLICATE_DISTRIBUTOR =
            "This distributor already exists in the distributor book";
    public static final String MESSAGE_DUPLICATE_DISTRIBUTOR_NAME =
            "A distirbutor with this name already exists in the distributor book.";
    public static final String MESSAGE_DUPLICATE_DISTRIBUTOR_PHONE =
            "A distirbutor with this phone already exists in the distributor book.";

    private final Distributor toAdd;

    /**
     * Creates an AddDistributorCommand to add the specified {@code Distributor}
     */
    public AddDistributorCommand(Distributor distributor) {
        requireNonNull(distributor);
        toAdd = distributor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.hasDistributorName(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DISTRIBUTOR_NAME);
        }

        if (model.hasDistributorPhone(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DISTRIBUTOR_PHONE);
        }

        model.addDistributor(toAdd);
        model.commitDistributorBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDistributorCommand // instanceof handles nulls
                && toAdd.equals(((AddDistributorCommand) other).toAdd));
    }
}
