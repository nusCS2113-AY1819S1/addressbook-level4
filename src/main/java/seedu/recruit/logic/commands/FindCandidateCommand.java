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
import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.events.logic.ChangeLogicStateEvent;
import seedu.recruit.commons.events.ui.ShowCandidateBookRequestEvent;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.parser.FindCandidateCommandParser;

import seedu.recruit.model.Model;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.candidate.CandidateContainsFindKeywordsPredicate;


/**
 * Finds and lists all persons in recruit book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCandidateCommand extends Command {

    public static final String COMMAND_WORD = "findc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_AGE + "AGE "
            + PREFIX_EDUCATION + "EDUCATION "
            + PREFIX_GENDER + "GENDER "
            + PREFIX_JOB + "JOB "
            + PREFIX_SALARY + "SALARY "
            + PREFIX_TAG + "TAG " + "\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + " alice" + PREFIX_SALARY + " 2500";

    private final CandidateContainsFindKeywordsPredicate candidatePredicate;
    private final String userInput;

    public FindCandidateCommand(CandidateContainsFindKeywordsPredicate candidatePredicate) {
        this.candidatePredicate = candidatePredicate;
        this.userInput = FindCandidateCommandParser.getUserInput();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs) {
        requireNonNull(model);
        model.updateFilteredCandidateList(candidatePredicate);

        if (ShortlistCandidateInitializationCommand.isShortlisting()) {
            EventsCenter.getInstance().post(new ChangeLogicStateEvent(SelectCandidateCommand.COMMAND_LOGIC_STATE));

            return new CommandResult(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                    model.getFilteredCandidateList().size())
                    + SelectJobCommand.MESSAGE_SELECT_JOB_SUCCESS_NEXT_STEP_IN_SHORTLIST
                    + SelectCandidateCommand.MESSAGE_USAGE);
        }

        EventsCenter.getInstance().post(new ShowCandidateBookRequestEvent());
        return new CommandResult("Candidate Book showing: " + userInput + "\n"
                + (String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredCandidateList().size())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCandidateCommand // instanceof handles nulls
                && candidatePredicate.equals(((FindCandidateCommand) other).candidatePredicate)); // state check
    }
}
