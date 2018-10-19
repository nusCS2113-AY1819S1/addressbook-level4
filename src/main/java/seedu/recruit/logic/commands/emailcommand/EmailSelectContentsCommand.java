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
    public static final String MESSAGE_USAGE = "Find the contents that you are going to email recipients about.\n"
            + "All items listed below will be added into the contents field.\n"
            + "Find/Filter the contents you intend to email, then type \"add\" to add contents to the field.\n"
            + "Type \"next\" when you have finished adding contents to move on to the next step.";
    public static final String COMMAND_LOGIC_STATE = "EmailSelectContents";
    private final String commandWord;

    public EmailSelectContentsCommand(String commandWord) {
        this.commandWord = commandWord;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        EmailUtil emailUtil = model.getEmailUtil();

        //Add contents only if commandWord equals add
        if(commandWord.equals("add")) {
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
            return new CommandResult("Contents added!\n" + EmailSelectContentsCommand.MESSAGE_USAGE);
        } else {
            //Check if content array is empty, if it is, do not allow to move on to next stage
            boolean isEmpty = false;

            //if recipient are candidates and job offers arraylist is empty
            if (emailUtil.isAreRecipientsCandidates() && emailUtil.getJobOffers().size() == 0) {
                isEmpty = true;
            }
            //if companies are candidates and candidates arraylist is empty
            if (!emailUtil.isAreRecipientsCandidates() && emailUtil.getCandidates().size() == 0) {
                isEmpty = true;
            }

            if(isEmpty) {
                return new CommandResult("There are no contents selected!\n"
                        + EmailSelectContentsCommand.MESSAGE_USAGE);
            } else {
                LogicManager.setLogicState(EmailSendCommand.COMMAND_LOGIC_STATE);
                return new CommandResult(EmailSendCommand.MESSAGE_USAGE);
            }
        }
    }
}
