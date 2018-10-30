package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRINK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.Price;
import seedu.address.model.drink.Quantity;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionType;

/**
 * Sell a drink from inventory.
 * // TODO: STUB
 */
public class SellDrinkCommand extends Command {

    public static final String COMMAND_WORD = "sell";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sell an drink that is recorded in Drink I/O. "
            + "Parameters: "
            + PREFIX_DRINK_NAME + "DRINK NAME "
            //+ PREFIX_DATE + "DATE SOLD "
            + PREFIX_QUANTITY + "QUANTITY SOLD "
            //+ PREFIX_PRICE + "TOTAL REVENUE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DRINK_NAME + "coca cola "
            //+ PREFIX_DATE + "10/06/18 "
            + PREFIX_QUANTITY + "12 ";
    //+ PREFIX_PRICE + "345.68 ";

    public static final String MESSAGE_SUCCESS = "%1$s sold on %2$s with quantity: %3$s";
    public static final String MESSAGE_DRINK_NOT_FOUND = "The drink entered does not exist in the inventory list";
    public static final String MESSAGE_FAILURE = "The quantity entered exceed the stock";

    private final Drink drink;
    // private final Date date; // TODO: add date support
    private final Quantity quantity;
    private final Transaction transaction;

    /**
     * Creates a SellDrinkCommand to sell the specified drink {@code Name}
     */
    public SellDrinkCommand(Drink drink, Quantity quantity) {
        requireAllNonNull(drink, quantity);
        this.drink = drink;
        this.quantity = quantity;
        transaction = new Transaction(TransactionType.SALE, drink, quantity, new Price("3.00"));
        // TODO: pull the actual price from the list of drinks
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireAllNonNull(model);

        if (!model.hasDrink(drink)) {
            throw new CommandException(MESSAGE_DRINK_NOT_FOUND);
        }

        model.sellDrink(transaction);

        return new CommandResult(String.format(MESSAGE_SUCCESS, drink.getName(), transaction.getTransactionDate(),
                quantity));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SellDrinkCommand); // instanceof handles nulls;
    }
}
