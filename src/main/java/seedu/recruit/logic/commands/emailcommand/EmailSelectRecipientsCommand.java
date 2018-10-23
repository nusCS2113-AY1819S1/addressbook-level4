package seedu.recruit.logic.commands.emailcommand;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import javafx.collections.ObservableList;

import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.Command;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.Model;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.ui.MainWindow;

/**
 * 2nd step of the Email command: selecting recipients
 */
public class EmailSelectRecipientsCommand extends Command {
    public static final String COMMAND_WORD = "next";
    public static final String MESSAGE_USAGE = "Find/Filter the recipients you intend to email. "
            + "All items listed below will be added into the recipients field.\n"
            + "Type \"add\" to add recipients to the field.\n"
            + "Type \"next\" when you have finished adding recipients to move on to the next step.\n"
            + "Type \"cancel\" to cancel the email command.";
    public static final String COMMAND_LOGIC_STATE = "EmailSelectRecipients";
    private final String commandWord;

    public EmailSelectRecipientsCommand(String commandWord) {
        this.commandWord = commandWord;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws ParseException {
        requireNonNull(model);
        EmailUtil emailUtil = model.getEmailUtil();

        //Add recipients only if commandWord is add.
        if (commandWord.equals(EmailUtil.EMAIL_ADD_COMMAND)) {
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

            String output = "Recipients added:\n";
            if (emailUtil.isAreRecipientsCandidates()) {
                output += model.getFilteredCandidateNames();
            } else {
                output += model.getFilteredRecipientJobOfferNames();
            }
            output += EmailSelectRecipientsCommand.MESSAGE_USAGE;
            return new CommandResult(output);

        } else if (commandWord.equals(EmailUtil.EMAIL_NEXT_COMMAND)) {
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

            if (isEmpty) {
                return new CommandResult("ERROR: There are no recipients selected!\n"
                        + EmailSelectRecipientsCommand.MESSAGE_USAGE);
            } else {
                LogicManager.setLogicState(EmailSelectContentsCommand.COMMAND_LOGIC_STATE);
                return new CommandResult(EmailSelectContentsCommand.MESSAGE_USAGE);
            }
        //back command
        } else if (commandWord.equals(EmailUtil.EMAIL_BACK_COMMAND)) {
            LogicManager.setLogicState(EmailSelectRecipientsCommand.COMMAND_LOGIC_STATE);
            return new CommandResult(EmailSelectRecipientsCommand.MESSAGE_USAGE);
        }

        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
    }
}
