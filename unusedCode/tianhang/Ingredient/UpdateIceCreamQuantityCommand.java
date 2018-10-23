package seedu.address.logic.commands.Ingredient;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.user.UserCommand;
import seedu.address.model.IngredientModel;
import seedu.address.model.ingredient.IceCream;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;

public class UpdateIceCreamQuantityCommand extends IngredientCommand {
    private IngredientName ingredientName;
    private IngredientQuantity ingredientQuantity;
    public UpdateIceCreamQuantityCommand(IngredientName ingredientName,
                                         IngredientQuantity ingredientQuantity){
        this.ingredientName = ingredientName;
        this.ingredientQuantity = ingredientQuantity;
    }

    @Override
    public CommandResult execute (IngredientModel ingredientModel, CommandHistory history) throws CommandException {
          Ingredient ingredient = new IceCream (ingredientName);
         ingredientModel.updateIngredientQuantity (ingredient, ingredientQuantity);
         return new CommandResult("changed");
    }

}
