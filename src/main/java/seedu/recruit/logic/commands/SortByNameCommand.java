package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.parser.Prefix;
import seedu.recruit.model.Model;

/**
 * Sorts all the candidates in the CandidateBook by name
 */
public class SortByNameCommand extends Command {

    public static final String COMMAND_WORD = "sortc";

    public static final String MESSAGE_SUCCESS = "Sorted all candidates by name";

    private static Prefix PREFIX_TO_SORT;

    public SortByNameCommand(Prefix prefix) {
        PREFIX_TO_SORT = prefix;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.sortByName(PREFIX_TO_SORT);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
