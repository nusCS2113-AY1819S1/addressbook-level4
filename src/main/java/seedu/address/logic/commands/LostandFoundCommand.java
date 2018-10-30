package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;


import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.Quantity;
import seedu.address.model.item.SimpleItem;

//@@author HeHaowei

/**
 * Lists all lost items with the lost number in the stock list to the user.
 */
public class LostandFoundCommand extends Command {

    public static final String COMMAND_WORD = "lost&found";
    public static final String MESSAGE_SUCCESS = "Lost items listed";
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        ArrayList<SimpleItem> lostItems = new ArrayList<>();
        List<Item> lastShownList = model.getFilteredItemList();
        sortSimpleItems(lastShownList, lostItems);
        String messageOutput = getMessageOutput(lostItems);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        return new CommandResult(messageOutput);
    }

    /**
     * Sorts the lost item from the Stock List.
     */
    void sortSimpleItems (List<Item> lastShownList, List<SimpleItem> lostItems
                          ) {
        for (Item item : lastShownList) {
            if (item.getLoststatus().getLoststatusLost() > 0) {
                lostItems.add(new SimpleItem(item.getName(),
                        new Quantity(Integer.toString(item.getLoststatus().getLoststatusLost()))));
            }

        }
    }
    String getMessageOutput (List<SimpleItem> lostItems) {
        String messageOutput = "";
        messageOutput += MESSAGE_SUCCESS + "\n";

        messageOutput += "Lost: ";
        int counter = 0;
        for (SimpleItem simpleItem : lostItems) {
            counter++;
            messageOutput += counter + ". "
                    +
                    simpleItem.getName()
                    +
                    ": "
                    + simpleItem.getQuantity().toString()
                    + "\n"
                    + "        ";

        }

        return messageOutput;
    }

}
