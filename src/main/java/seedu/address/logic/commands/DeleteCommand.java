package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import seedu.address.MainApp;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.storage.XmlAdaptedLoanList;
import seedu.address.storage.XmlAdaptedLoanerDescription;

/**
 * Deletes a item identified using it's displayed index from the stock list.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the item identified by the index number used in the displayed item list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ITEM_SUCCESS = "Deleted Item: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Item> lastShownList = model.getFilteredItemList();


        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }
        Item itemToDelete = lastShownList.get(targetIndex.getZeroBased());
        if (MainApp.getLoanListFile().exists()) {
            try {
                int counter = 0;
                JAXBContext context = JAXBContext.newInstance(XmlAdaptedLoanList.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                XmlAdaptedLoanList xmlAdaptedLoanList = (XmlAdaptedLoanList) unmarshaller
                        .unmarshal(MainApp.getLoanListFile());
                for (XmlAdaptedLoanerDescription loanerDescription : xmlAdaptedLoanList.getLoanList()) {
                    if (loanerDescription.getItemName().equals(itemToDelete.getName().toString())) {
                        DeleteLoanListCommand toDelete = new DeleteLoanListCommand(Index.fromZeroBased(counter));
                        toDelete.execute(model, history);
                    }
                    counter++;
                }
            } catch (JAXBException e) {
                System.out.println(e.toString());
            }
        }

        model.deleteItem(itemToDelete);
        model.commitStockList();
        return new CommandResult(String.format(MESSAGE_DELETE_ITEM_SUCCESS, itemToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
