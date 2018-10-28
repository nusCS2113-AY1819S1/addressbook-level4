package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;


import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.Quantity;

import java.util.List;
public class FoundCommand extends Command{
    public static final String COMMAND_WORD = "found";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Found a item from the stock list identified  "
            +"by the index number used in the displayed item list"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_QUANTITY + "QUANTITY\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_QUANTITY + "25";


    public static final String MESSAGE_FOUND_ITEM_SUCCESS = "Found Item: %1$s";
    public static final String MESSAGE_INVALID_QUANTITY = "The found quantity input is invalid";
    public static final String MESSAGE_INVALID_FOUND_FIELD = "The found description is invalid";

    private final Index targetIndex;
    private final FoundDescriptor foundDescriptor;

    public FoundCommand(Index targetIndex, FoundDescriptor foundDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(foundDescriptor);
        this.targetIndex = targetIndex;
        this.foundDescriptor = new FoundDescriptor(foundDescriptor);

    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Item> lastShownList = model.getFilteredItemList();


        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item itemToFound = lastShownList.get(targetIndex.getZeroBased());
        Item foundItem = createFoundItem(itemToFound,foundDescriptor);

        if (!itemToFound.isSameItem(foundItem) && model.hasItem(foundItem)) {
            throw new CommandException(MESSAGE_INVALID_QUANTITY);
        }
        model.updateItem(itemToFound,foundItem);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitStockList();
        return new CommandResult(String.format(MESSAGE_FOUND_ITEM_SUCCESS, foundItem));
    }

    /**
     * Creates and returns a {@code Item} with the details of {@code itemToFound}
     * edited with {@code FoundDescriptor}.
     */
    private static Item createFoundItem(Item itemToFound, FoundDescriptor foundDescriptor) {
        assert itemToFound != null;

        //Integer updatedValue = foundDescriptor.getFinalQuantity();
        Integer updatedValue = foundDescriptor.getFoundQuantity();
        Integer initialValue=itemToFound.getQuantity().toInteger();

        Quantity updatedQuantity= new Quantity(Integer.toString(initialValue+updatedValue));



        return new Item(itemToFound.getName(), updatedQuantity, itemToFound.getMinQuantity(), itemToFound.getTags());
    }

    public static class FoundDescriptor {
        private Integer foundQuantity;
        //private Integer initialQuantity;
        //private Integer finalQuantity;
        public FoundDescriptor(){}

        public FoundDescriptor(FoundDescriptor toCopy) {
            setFoundQuantity(toCopy.foundQuantity);
            // setInitialQuantity(toCopy.initialQuantity);
            //setFinalQuantity(toCopy.foundQuantity,toCopy.initialQuantity);


        }
        public void setFoundQuantity(Integer foundQuantity) {this.foundQuantity=foundQuantity;}
        //public void setInitialQuantity(Integer initialQuantity){this.initialQuantity=initialQuantity;}
        //public void setFinalQuantity(Integer foundQuantity,Integer initialQuantity){this.finalQuantity=initialQuantity-foundQuantity;}
        //public Integer getinitialQuantity(){return initialQuantity;}
        public Integer getFoundQuantity(){return foundQuantity;}
        //public Integer getFinalQuantity(){return finalQuantity;}
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FoundCommand // instanceof handles nulls
                && targetIndex.equals(((FoundCommand) other).targetIndex)); // state check
    }
}