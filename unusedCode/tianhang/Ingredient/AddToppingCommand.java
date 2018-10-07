//author @tianhang
package seedu.address.logic.commands.Ingredient;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.IngredientModel;
import seedu.address.model.ingredient.Topping;

/**
 * Adds a person to the address book.
 */
public class AddToppingCommand extends IngredientCommand {



    public static final String MESSAGE_SUCCESS = "New Topping added: %1$s";
    public static final String MESSAGE_DUPLICATE_TOPPING = "This ingredient has already been added";

    private final Topping toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddToppingCommand (Topping topping) {
        requireNonNull (topping);
        toAdd = topping;
    }

    @Override
    public CommandResult execute(IngredientModel ingredientModel, CommandHistory history) throws CommandException {
        requireNonNull(ingredientModel);

        if (ingredientModel.hasIngredient(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TOPPING);
        }

        ingredientModel.addIngredient (toAdd);
        //TODO: this is for version format
        //model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddToppingCommand // instanceof handles nulls
                && toAdd.equals((( AddToppingCommand ) other).toAdd));
    }




}
