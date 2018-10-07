
package seedu.address.logic.commands.Ingredient;
//@@author tianhang
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_ICECREAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_TOPPING;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.IngredientModel;
import seedu.address.model.Model;





/**
 * Adds a person to the address book.
 */
public class IngredientCommand extends Command {

    public static final String COMMAND_WORD = "addIngredient";

    public static final String MESSAGE_USAGE_ADD_INGREDIENT = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_INGREDIENT_ICECREAM + "ice cream name "
            + "or "
            + PREFIX_INGREDIENT_TOPPING + "ice cream topping ";

    public static final String MESSAGE_SUCCESS = "New IceCream added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";


    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public IngredientCommand () {

    }


    public CommandResult execute (IngredientModel ingredientModel, CommandHistory history) throws CommandException {
        return null;
    }

    @Override
    public CommandResult execute (Model model , CommandHistory history) throws CommandException {
        return null;
    }
}
