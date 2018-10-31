package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.ui.ShowLastViewedBookRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * Last stage of Shortlist Command.
 * Shortlists selected candidates for a job offer
 */
public class ShortlistCandidateCommand extends Command {
    public static final String COMMAND_WORD = "confirm";

    public static final String COMMAND_LOGIC_STATE = "ShortlistCandidate";

    public static final String MESSAGE_USAGE = "Enter confirm to confirm shortlist.\n"
            + "Enter cancel to cancel shortlist.\n";

    public static final String MESSAGE_SUCCESS = "Successfully shortlisted candidate: %1$s\n"
            + "for job offer: %2$s, for company: %3$s.\n"
            + "Shortlisting Process Done. You may carry out other commands.\n";

    public static final String MESSAGE_DUPLICATE_CANDIDATE_SHORTLISTED =
            "This candidate is already shortlisted for job offer: %1$s, for company: %2$s.\n"
            + "Please cancel the current shortlisting process.\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Company selectedCompany = SelectCompanyCommand.getSelectedCompany();
        JobOffer selectedJobOffer = SelectJobCommand.getSelectedJobOffer();
        Candidate selectedCandidate = SelectCandidateCommand.getSelectedCandidate();

        if (selectedJobOffer.getUniqueCandidateList().contains(selectedCandidate)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_CANDIDATE_SHORTLISTED,
                    selectedJobOffer.getJob().value, selectedCompany.getCompanyName().value)
                    + MESSAGE_USAGE);
        }

        model.shortlistCandidateToJobOffer(selectedCandidate, selectedJobOffer);
        model.commitCompanyBook();

        LogicManager.setLogicState("primary");
        EventsCenter.getInstance().post(new ShowLastViewedBookRequestEvent());
        if (ShortlistCandidateInitializationCommand.isShortlisting()) {
            ShortlistCandidateInitializationCommand.isDoneShortlisting();
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, selectedCandidate.getName().fullName,
                selectedJobOffer.getJob().value, selectedCompany.getCompanyName().value));
    }
}
