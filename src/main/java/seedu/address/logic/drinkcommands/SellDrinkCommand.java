package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRINK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds a person to the address book.
 */
public class SellCommand extends Command {

    public static final String COMMAND_WORD = "sell";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sell an item is recorded at drink I/O. "
            + "Parameters: "
            + PREFIX_DRINK_ITEM + "DRINK NAME "
            + PREFIX_DATE + "DATE SOLD "
            + PREFIX_QUANTITY + "QUANTITY SOLD "
            + PREFIX_PRICE + "TOTAL REVENUE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DRINK_ITEM + "coca cola "
            + PREFIX_DATE + "10/06/18 "
            + PREFIX_QUANTITY + "12 "
            + PREFIX_PRICE + "345.68 ";

    public static final String MESSAGE_SUCCESS = "%1$s sold on %2$s with quantity: %3$s and total revenue is : %4$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_FAILURE = "The quantity entered exceed the stock";

    private final String itemName;
    private final String date;
    private final String quantity;
    private final String price;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public SellCommand(String itemName, String date, String quantity, String price) {
        requireAllNonNull(itemName, date, quantity, price);
        this.itemName = itemName;
        this.date = date;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        //        requireNonNull(model);
        //
        //        if (model.hasPerson(toAdd)) {
        //            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        //        }
        //
        //        model.addPerson(toAdd);
        //        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, itemName, date, quantity, price));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SellCommand); // instanceof handles nulls;
    }
}
