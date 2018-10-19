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
import seedu.recruit.ui.MainWindow;

/**
 * 2nd step of the Email command
 */
public class EmailSelectRecipientsCommand extends Command {
    public static final String COMMAND_WORD = "next";
    public static final String MESSAGE_USAGE = "Find the recipients that you are going to email\n"
            + "All items listed below will be added into the recipients field.\n"
            + "Find/Filter the recipients you intend to email, then type \"add\" to add recipients to the field.\n"
            + "Type \"next\" when you have finished adding recipients to move on to the next step.\n";
    public static final String COMMAND_LOGIC_STATE = "EmailSelectRecipients";
    private final String commandWord;

    public EmailSelectRecipientsCommand(String commandWord) {
        this.commandWord = commandWord;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        EmailUtil emailUtil = model.getEmailUtil();

        //Add recipients only if commandWord is add.
        if(commandWord.equals("add")) {
            if (MainWindow.getDisplayedBook().equals("candidateBook")) {
                ObservableList<Candidate> recipients = model.getFilteredCandidateList();
                for (Candidate recipient : recipients) {
                    emailUtil.addCandidate(recipient);
                }
                emailUtil.setAreRecipientsCandidates(true);
            } else {
                ObservableList<JobOffer> recipients = model.getFilteredCompanyJobList();
                for (JobOffer recipient : recipients) {
                    emailUtil.addJobOffer(recipient);
                }
                emailUtil.setAreRecipientsCandidates(false);
            }
            return new CommandResult("Recipients added!\n" + EmailSelectRecipientsCommand.MESSAGE_USAGE);
        } else {
            //Check if content array is empty, if it is, do not allow to move on to next stage
            boolean isEmpty = false;

            //if recipient are candidates and candidate arraylist is empty
            if (emailUtil.isAreRecipientsCandidates() && emailUtil.getCandidates().size() == 0) {
                isEmpty = true;
            }
            //if companies are candidates and job offers arraylist is empty
            if (!emailUtil.isAreRecipientsCandidates() && emailUtil.getJobOffers().size() == 0) {
                isEmpty = true;
            }

            if(isEmpty) {
                return new CommandResult("There are no recipients selected!\n"
                        + EmailSelectRecipientsCommand.MESSAGE_USAGE);
            } else {
                LogicManager.setLogicState(EmailSelectContentsCommand.COMMAND_LOGIC_STATE);
                return new CommandResult(EmailSelectContentsCommand.MESSAGE_USAGE);
            }
        }
    }
}
