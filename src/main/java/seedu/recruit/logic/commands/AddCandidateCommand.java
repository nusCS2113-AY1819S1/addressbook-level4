package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.recruit.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.ui.ShowCandidateBookRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.candidate.Candidate;

/**
 * Adds a candidate to the CandidateBook.
 */
public class AddCandidateCommand extends Command {

    public static final String COMMAND_WORD = "Add Candidate Interface";

    public static final String MESSAGE_USAGE = "Enter the following details of the candidate in the format:\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_GENDER + "GENDER "
            + PREFIX_AGE + "AGE "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_JOB + "DESIRED_JOB "
            + PREFIX_EDUCATION + "HIGHEST_EDUCATION_LEVEL "
            + PREFIX_SALARY + "EXPECTED_SALARY "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "(Enter 'cancel' to stop adding candidates)\n"
            + "Example: "
            + PREFIX_NAME + "John Doe "
            + PREFIX_GENDER + "M "
            + PREFIX_AGE + "21 "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_JOB + "Waiter "
            + PREFIX_EDUCATION + "OLEVELS "
            + PREFIX_SALARY + "1000 "
            + PREFIX_TAG + "diabetic "
            + PREFIX_TAG + "excuseHeavyLoad";

    public static final String MESSAGE_SUCCESS = "New added candidate: %1$s\n"
                                 + "(Enter details of another candidate to add or enter 'cancel' to stop adding.)";
    public static final String MESSAGE_DUPLICATE_PERSON = "This candidate already exists in the CandidateBook";

    private final Candidate toAdd;

    /**
     * Creates an AddCandidateCommand to add the specified {@code Candidate}
     */

    public AddCandidateCommand(Candidate candidate) {
        requireNonNull(candidate);
        toAdd = candidate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs) throws CommandException {
        requireNonNull(model);
        EventsCenter.getInstance().post(new ShowCandidateBookRequestEvent());

        if (model.hasCandidate(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addCandidate(toAdd);
        model.commitRecruitBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCandidateCommand // instanceof handles nulls
                && toAdd.equals(((AddCandidateCommand) other).toAdd));
    }
}
