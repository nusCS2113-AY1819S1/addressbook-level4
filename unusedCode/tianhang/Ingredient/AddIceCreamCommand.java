//author @tianhang
package seedu.address.logic.commands.Ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.IngredientModel;

import seedu.address.model.ingredient.IceCream;


/**
 * Adds a person to the address book.
 */
public class AddIceCreamCommand extends IngredientCommand {


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New IceCream added: %1$s";
    public static final String MESSAGE_DUPLICATE_ICE_CREAM = "This ice cream has already been added";
    private final IceCream toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddIceCreamCommand (IceCream iceCream) {
        requireNonNull (iceCream);
        toAdd = iceCream;
    }

    @Override
    public CommandResult execute(IngredientModel ingredientModel, CommandHistory history) throws CommandException {
        requireNonNull(ingredientModel);

        if (ingredientModel.hasIngredient(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ICE_CREAM);
        }

        ingredientModel.addIngredient (toAdd);
        //TODO: this is for version format
        //model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddIceCreamCommand // instanceof handles nulls
                && toAdd.equals((( AddIceCreamCommand ) other).toAdd));
    }




}
