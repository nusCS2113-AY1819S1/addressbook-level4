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

//@@author ChewKinWhye

/**
 * Lists all items in the stock list to the user grouped by the status.
 */
public class StatusCommand extends Command {

    public static final String COMMAND_WORD = "status";
    public static final String MESSAGE_SUCCESS = "Items listed according to status";
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        ArrayList<SimpleItem> readyItems = new ArrayList<>();
        ArrayList<SimpleItem> onLoanItems = new ArrayList<>();
        ArrayList<SimpleItem> faultyItems = new ArrayList<>();
        List<Item> lastShownList = model.getFilteredItemList();
        sortSimpleItems(lastShownList, readyItems, onLoanItems, faultyItems);
        String messageOutput = getMessageOutput(readyItems, onLoanItems, faultyItems);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        return new CommandResult(messageOutput);
    }

    /**
     * Sorts the item in the item list into Ready, On_Loan, and Faulty.
     */
    void sortSimpleItems (List<Item> lastShownList, ArrayList<SimpleItem> readyItems,
                          ArrayList<SimpleItem> onLoanItems, ArrayList<SimpleItem> faultyItems) {
        for (Item item : lastShownList) {
            if (item.getStatus().getStatusReady() > 0) {
                readyItems.add(new SimpleItem(item.getName(),
                        new Quantity(Integer.toString(item.getStatus().getStatusReady()))));
            }
            if (item.getStatus().getStatusOnLoan() > 0) {
                onLoanItems.add(new SimpleItem(item.getName(),
                        new Quantity(Integer.toString(item.getStatus().getStatusOnLoan()))));
            }
            if (item.getStatus().getStatusFaulty() > 0) {
                faultyItems.add(new SimpleItem(item.getName(),
                        new Quantity(Integer.toString(item.getStatus().getStatusFaulty()))));
            }
        }
    }
    String getMessageOutput (ArrayList<SimpleItem> readyItems,
                           ArrayList<SimpleItem> onLoanItems, ArrayList<SimpleItem> faultyItems) {
        String messageOutput = "";
        messageOutput += MESSAGE_SUCCESS + "\n";

        messageOutput += "Ready: ";
        int counter = 0;
        for (SimpleItem simpleItem : readyItems) {
            counter++;
            messageOutput += simpleItem.getName() + ": " + simpleItem.getQuantity().toString();
            if (counter != readyItems.size()) {
                messageOutput += ", ";
            }
        }

        messageOutput += "\nOn Loan: ";
        counter = 0;
        for (SimpleItem simpleItem : onLoanItems) {
            counter++;
            messageOutput += simpleItem.getName() + ": " + simpleItem.getQuantity().toString();
            if (counter != onLoanItems.size()) {
                messageOutput += ", ";
            }
        }


        messageOutput += "\nFaulty: ";
        counter = 0;
        for (SimpleItem simpleItem : faultyItems) {
            counter++;
            messageOutput += simpleItem.getName() + ": " + simpleItem.getQuantity().toString();
            if (counter != onLoanItems.size()) {
                messageOutput += ", ";
            }
        }

        return messageOutput;
    }

}
