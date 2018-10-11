package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.Model;

/**
 * Sorts all the candidates in the CandidateBook by name
 */
public class SortByNameCommand extends Command {

    public static final String COMMAND_WORD = "sortc";

    public static final String MESSAGE_SUCCESS = "Sorted all candidates by name";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.sortByName();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
