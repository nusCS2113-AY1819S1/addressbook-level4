package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.parser.Prefix;
import seedu.recruit.model.Model;

/**
 * Sorts all the candidates in the CandidateBook
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sortc";

    public static final String MESSAGE_SUCCESS = "Sorted all candidates";

    public static final String MESSAGE_TAG_USAGE = "Please sort by using one of the available tags: "
            + "Name " + PREFIX_NAME
            + ", Age " + PREFIX_AGE
            + ", Email " + PREFIX_EMAIL
            + ", Job " + PREFIX_JOB
            + ", Education " + PREFIX_EDUCATION
            + ", Salary " + PREFIX_SALARY + "\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME;

    private static Prefix prefixToSort;

    public SortCommand(Prefix prefix) {
        this.prefixToSort = prefix;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.sortCandidates(prefixToSort);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
