package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRINK_COST_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRINK_DEFAULT_SELLING_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRINK_NAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.drink.Drink;
import seedu.address.model.user.admin.AdminModel;
import seedu.address.model.user.admin.AdminModelManager;

/**
 * Adds a drink to the inventory list.
 */
public class AddDrinkCommand extends Command {
    public static final String COMMAND_WORD = "addDrink";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new drink to Drink I/0. "
            + "Parameters: "
            + PREFIX_DRINK_NAME + "Drink item "
            + PREFIX_DRINK_DEFAULT_SELLING_PRICE + "Default salling price "
            + PREFIX_DRINK_COST_PRICE + "Cost price \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DRINK_NAME + "Coca Cola Original "
            + PREFIX_DRINK_DEFAULT_SELLING_PRICE + "20.00 "
            + PREFIX_DRINK_COST_PRICE + "20";

    public static final String MESSAGE_SUCCESS = "New drink added: %1$s with default price of %2$s";
    public static final String MESSAGE_DUPLICATE_DRINK = "This drink already exists in the inventory list";

    private final Drink toAdd;

    /**
     * Creates an AddDrinkCommand to add the specified {@code Drink}
     */
    public AddDrinkCommand(Drink drink) {
        requireAllNonNull(drink);
        toAdd = drink;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasDrink(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DRINK);
        }

        model.addDrink(toAdd);

        if (model instanceof AdminModel) {
            System.out.println ("add drinkCommand");
            System.out.println(((AdminModelManager) model).isValid());
        } else {
            System.out.println("not rights");
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                toAdd.getName ().toString (), toAdd.getRetailPrice ().toString ()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDrinkCommand // instanceof handles nulls
                && toAdd.equals(((AddDrinkCommand) other).toAdd));
    }
}
