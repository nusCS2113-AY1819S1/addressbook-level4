package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRINK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.drink.Name;
import seedu.address.model.drink.Quantity;

/**
 * Adds a person to the address book.
 * // TODO: STUB
 */
public class SellDrinkCommand extends Command {

    public static final String COMMAND_WORD = "sell";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sell an item is recorded at drink I/O. "
            + "Parameters: "
            + PREFIX_DRINK_NAME + "DRINK NAME "
            + PREFIX_DATE + "DATE SOLD "
            + PREFIX_QUANTITY + "QUANTITY SOLD "
            + PREFIX_PRICE + "TOTAL REVENUE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DRINK_NAME + "coca cola "
            + PREFIX_DATE + "10/06/18 "
            + PREFIX_QUANTITY + "12 "
            + PREFIX_PRICE + "345.68 ";

    public static final String MESSAGE_SUCCESS = "%1$s sold on %2$s with quantity: %3$s and total revenue is : %4$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_FAILURE = "The quantity entered exceed the stock";

    private final Name drinkName;
    // private final Date date; // TODO: add date support
    private final Quantity quantity;

    /**
     * Creates a SellDrinkCommand to sell the specified drink {@code Name}
     */
    public SellDrinkCommand(Name drinkName, Quantity quantity) {
        requireAllNonNull(drinkName, quantity);
        this.drinkName = drinkName;
        this.quantity = quantity;
        // date =
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireAllNonNull(model);

        //        requireNonNull(model);
        //
        //        if (model.hasPerson(toAdd)) {
        //            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        //        }
        //
        //        model.addPerson(toAdd);
        //        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, drinkName, quantity));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SellDrinkCommand); // instanceof handles nulls;
    }
}
