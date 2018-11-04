package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.StatisticCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.model.BookInventory;
import seedu.address.model.Model;

/**
 * Clears the BookInventory.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_ALIAS = "c";

    public static final String MESSAGE_SUCCESS = "BookInventory has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new BookInventory());
        StatisticCenter.getInstance().resetData();
        model.commitBookInventory();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
