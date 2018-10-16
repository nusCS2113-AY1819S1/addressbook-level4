package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE;

import seedu.recruit.commons.core.Messages;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.Model;
import seedu.recruit.model.candidate.NameContainsKeywordsPredicate;

public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all persons who falls under the searched category "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_EDUCATION + "HIGHEST_EDUCATION_LEVEL "
            + PREFIX_JOB + "DESIRED_JOB "
            + PREFIX_SALARY + "EXPECTED_SALARY "
            + PREFIX_AGE + "AGE "
            + System.lineSeparator()
            + "Example: " + COMMAND_WORD + " " + PREFIX_EDUCATION + "O Level";

    private final NameContainsKeywordsPredicate educationPredicate;

    public FilterCommand(NameContainsKeywordsPredicate predicate) {
        this.educationPredicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredCandidateList(educationPredicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredCandidateList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof FilterCommand //instanceof handles nulls
                && educationPredicate.equals(((FilterCommand) other).educationPredicate));
    }
}
