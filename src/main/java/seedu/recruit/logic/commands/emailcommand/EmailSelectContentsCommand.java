package seedu.recruit.logic.commands.emailcommand;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.Command;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * 3rd step of the Email command
 */
public class EmailSelectContentsCommand extends Command {
    public static final String COMMAND_WORD = "next";
    public static final String MESSAGE_USAGE = "Find the content that you are going to email\n"
            + "Type \"next\" when you have done so to move on to the next step.";
    public static final String COMMAND_LOGIC_STATE = "EmailSelectContents";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        EmailUtil emailUtil = model.getEmailUtil();

        if (emailUtil.isAreRecipientsCandidates()) {
            ObservableList<JobOffer> contents = model.getFilteredCompanyJobList();
            for (JobOffer content : contents) {
                emailUtil.addJobOffer(content);
            }
        } else {
            ObservableList<Candidate> contents = model.getFilteredCandidateList();
            for (Candidate content : contents) {
                emailUtil.addCandidate(content);
            }
        }

        LogicManager.setLogicState(EmailSendCommand.COMMAND_LOGIC_STATE);
        return new CommandResult(EmailSendCommand.MESSAGE_USAGE);
    }
}
