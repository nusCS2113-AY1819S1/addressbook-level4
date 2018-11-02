package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Item;

/**
 * Adds a item to the stock list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a item to the stock list.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_MIN_QUANTITY + "MINIMUM QUANTITY "
            + "[Optional: " + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Arduino "
            + PREFIX_QUANTITY + "20 "
            + PREFIX_MIN_QUANTITY + "5 "
            + PREFIX_TAG + "Lab1 "
            + PREFIX_TAG + "Lab2";


    public static final String MESSAGE_SUCCESS = "New item added: %1$s";
    public static final String MESSAGE_DUPLICATE_ITEM = "This item already exists in the stock list";

    private final Item toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Item}
     */
    public AddCommand(Item item) {
        requireNonNull(item);
        toAdd = item;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasItem(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ITEM);
        }

        model.addItem(toAdd);
        model.commitStockList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
