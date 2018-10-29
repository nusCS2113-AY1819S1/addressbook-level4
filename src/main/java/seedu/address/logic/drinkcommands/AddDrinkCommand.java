package seedu.address.logic.drinkcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.drinkparser.CliSyntax.PREFIX_DRINK_DEFAULT_SELLING_PRICE;
import static seedu.address.logic.drinkparser.CliSyntax.PREFIX_DRINK_NAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.drinkcommands.exceptions.DrinkCommandException;
import seedu.address.model.DrinkModel;
import seedu.address.model.drink.Drink;
import seedu.address.model.user.admin.AdminModel;
import seedu.address.model.user.admin.AdminModelManager;

/**
 * Adds a drink to the inventory list.
 */
public class AddDrinkCommand extends DrinkCommand {
    public static final String COMMAND_WORD = "addItem";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new item to the drink I/0. "
            + "Parameters: "
            + PREFIX_DRINK_NAME + "Drink item "
            + PREFIX_DRINK_DEFAULT_SELLING_PRICE + "Default salling price \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DRINK_NAME + "Coca Cola Original "
            + PREFIX_DRINK_DEFAULT_SELLING_PRICE + "20.00 ";

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
    public DrinkCommandResult execute(DrinkModel model, CommandHistory history) throws DrinkCommandException {
        requireNonNull(model);

        if (model.hasDrink(toAdd)) {
            throw new DrinkCommandException(MESSAGE_DUPLICATE_DRINK);
        }

        model.addDrink(toAdd);

        if (model instanceof AdminModel) {
            System.out.println(((AdminModelManager) model).isValid());
        } else {
            System.out.println("not rights");
        }
        return new DrinkCommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDrinkCommand // instanceof handles nulls
                && toAdd.equals(((AddDrinkCommand) other).toAdd));
    }
}
