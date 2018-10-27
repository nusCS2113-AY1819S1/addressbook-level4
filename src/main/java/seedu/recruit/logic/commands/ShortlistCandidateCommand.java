package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.ui.MainWindow;

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
            + "for job offer: %2$s, by company: %3$s.\n"
            + "Shortlisting Process Done. You may carry out other commands.\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (ShortlistCandidateInitializationCommand.isShortlisting()) {
            ShortlistCandidateInitializationCommand.isDoneShortlisting();
        }
        Company selectedCompany = SelectCompanyCommand.getSelectedCompany();
        JobOffer selectedJobOffer = SelectJobCommand.getSelectedJobOffer();
        Candidate selectedCandidate = SelectCandidateCommand.getSelectedCandidate();

        selectedJobOffer.addToShortlistedCandidateList(selectedCandidate);

        LogicManager.setLogicState("primary");
        MainWindow.switchToLastViewedBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, selectedCandidate.getName().fullName,
                selectedJobOffer.getJob().value, selectedCompany.getCompanyName().value));
    }
}
