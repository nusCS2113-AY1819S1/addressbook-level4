package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENDITURES;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListExpenditureCommand extends Command {

    public static final String COMMAND_WORD = "ET_list";
    public static final String COMMAND_ALIAS = "l";

    public static final String MESSAGE_SUCCESS = "Listed all expenditures";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredExpenditureList(PREDICATE_SHOW_ALL_EXPENDITURES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
