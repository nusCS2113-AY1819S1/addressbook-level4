package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NATIONALITY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.distribute.Distribute;
import seedu.address.model.distribute.DistributeAlgorithm;

/**
 * Distribute will automatically split all persons into n number of groups based on user choice
 * Will be able to split into n groups base on gender or nationality or both
 */
public class DistributeCommand extends Command {

    public final static String COMMAND_WORD = "distinto";
    public final static String COMMAND_WORD_2 = "di";

    public final static String MESSAGE_SUCCESS = "Distribution Success";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Distribute students equally into N groups.\n"
            + "Parameters: NUMBER OF GROUPS "
            + PREFIX_NAME + "GROUP NAME "
            + PREFIX_GENDER + "BALANCED GENDER?"
            + PREFIX_NATIONALITY + "BALANCED NATIONALITY?\n"
            + "Example: " + COMMAND_WORD + " "
            + "5 "
            + PREFIX_NAME + "E1-06- "
            + PREFIX_GENDER + "1 "
            + PREFIX_NATIONALITY + "1 \n";

    private final Distribute distribute;


    public DistributeCommand(Distribute dist) {
        requireNonNull(dist);
        distribute = dist;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        new DistributeAlgorithm(model, distribute);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}