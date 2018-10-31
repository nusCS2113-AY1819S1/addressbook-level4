package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * Deletes a candidate identified using its displayed index
 * from the list of shortlisted candidates of a selected job offer.
 */
public class DeleteShortlistedCandidateCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String COMMAND_LOGIC_STATE = "DeleteShortlistedCandidate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the candidate(s) identified by the index number(s) used in the shortlisted candidate list.\n"
            + "Parameters: INDEX,INDEX-INDEX ... (INDEX must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CANDIDATE_SUCCESS =
            "Deleted Candidate: %1$s from shortlisted list for Job: %2$s of Company: %3$s.\n"
            + "Delete Process for shortlisted candidates done. You may carry out other commands.\n";

    public static final String MESSAGE_EMPTY_LIST =
            "ERROR: There are no shortlisted candidates for this job offer.\n"
            + "Exited from delete process.\n";

    private final Index targetIndex;

    public DeleteShortlistedCandidateCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Company selectedCompany = SelectCompanyCommand.getSelectedCompany();
        JobOffer selectedJob = SelectJobCommand.getSelectedJobOffer();

        // There are no candidates inside the list of shortlisted candidates.
        if (selectedJob.getObservableCandidateList().isEmpty()) {
            LogicManager.setLogicState("primary");
            return new CommandResult(MESSAGE_EMPTY_LIST);
        }

        // targetIndex exceeds the size of the shortlisted candidate list
        if (targetIndex.getZeroBased() >= selectedJob.getObservableCandidateList().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Candidate selectedCandidate = selectedJob.getObservableCandidateList().get(targetIndex.getZeroBased());
        model.deleteShortlistedCandidateFromJobOffer(selectedCandidate, selectedJob);
        model.commitCompanyBook();
        if (DeleteShortlistedCandidateInitializationCommand.isDeleting()) {
            DeleteShortlistedCandidateInitializationCommand.isDoneDeleting();
        }
        LogicManager.setLogicState("primary");
        return new CommandResult(String.format(MESSAGE_DELETE_CANDIDATE_SUCCESS,
                selectedCandidate.getName().fullName, selectedJob.getJob().value,
                selectedCompany.getCompanyName().value));
    }

}
