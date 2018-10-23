package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEFAULT_SELLING_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRINK_ITEM;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.user.admin.AdminModel;
import seedu.address.model.user.admin.AdminModelManager;

/**
 * Adds a person to the address book.
 */
public class AddItemCommand extends Command {

    public static final String COMMAND_WORD = "addItem";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new item to the drink I/0. "
            + "Parameters: "
            + PREFIX_DRINK_ITEM + "Drink item "
            + PREFIX_DEFAULT_SELLING_PRICE + "Default salling price \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DRINK_ITEM + "coca cola "
            + PREFIX_DEFAULT_SELLING_PRICE + "3.12 ";

    public static final String MESSAGE_SUCCESS = "New drinks added: %1$s with default price of %2$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This drink already exists in the drink I/O";

    private final String drinkName;
    private final String defaultSellingPrice;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddItemCommand(String drinkName, String defaultSellingPrice) {
        requireAllNonNull(drinkName, defaultSellingPrice);
        this.drinkName = drinkName;
        this.defaultSellingPrice = defaultSellingPrice;
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
        if (model instanceof AdminModel){
            System.out.println (( (AdminModelManager) model).isValid ());
        }else{
            System.out.println ("not rights");
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, drinkName, defaultSellingPrice));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddItemCommand // instanceof handles nulls
                && drinkName.equals(((AddItemCommand) other).drinkName));
    }
}
