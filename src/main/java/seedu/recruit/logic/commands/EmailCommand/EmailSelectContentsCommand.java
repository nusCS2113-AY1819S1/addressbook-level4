package seedu.recruit.logic.commands.EmailCommand;

import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.Command;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.logic.parser.RecruitBookParser;
import seedu.recruit.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * 3rd step of the Email command
 */
public class EmailSelectContentsCommand extends Command {
    public static final String COMMAND_WORD = "next";
    public static final String MESSAGE_USAGE = "Find/Filter the content that you are going to send";
    public static final String COMMAND_LOGIC_STATE = "EmailSelectContents";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        EmailUtil emailUtil = model.getEmailUtil();

        if(emailUtil.isAreRecipientsCandidates()) {
            emailUtil.setContents(model.getFilteredCompanyJobList());
        } else {
            emailUtil.setContents(model.getFilteredCandidateList());
        }

        model.setEmailUtil(emailUtil);
        LogicManager.setLogicState(EmailSendCommand.COMMAND_LOGIC_STATE);
        return new CommandResult(EmailSendCommand.MESSAGE_USAGE);
    }
}
