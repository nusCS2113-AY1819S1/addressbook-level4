package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;

import seedu.address.commons.core.StatisticCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all books in the book inventory to the user.
 */
public class ViewStatisticCommand extends Command {

    public static final String COMMAND_WORD = "viewstatistic";

    public static final String MESSAGE_SUCCESS = StatisticCenter.getInstance().getStatistic().toString();


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
