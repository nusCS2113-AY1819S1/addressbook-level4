package seedu.recruit.logic.parser;

import static seedu.recruit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.recruit.commons.util.EmailUtil.EMAIL_ADD_COMMAND;
import static seedu.recruit.commons.util.EmailUtil.EMAIL_BACK_COMMAND;
import static seedu.recruit.commons.util.EmailUtil.EMAIL_NEXT_COMMAND;
import static seedu.recruit.commons.util.EmailUtil.EMAIL_SEND_COMMAND;

import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.LogicState;
import seedu.recruit.logic.commands.Command;
import seedu.recruit.logic.commands.FindCandidateCommand;
import seedu.recruit.logic.commands.FindCompanyCommand;
import seedu.recruit.logic.commands.ListCandidateCommand;
import seedu.recruit.logic.commands.ListCompanyCommand;
import seedu.recruit.logic.commands.SwitchBookCommand;
import seedu.recruit.logic.commands.emailcommand.EmailContentsAddCommand;
import seedu.recruit.logic.commands.emailcommand.EmailContentsBackCommand;
import seedu.recruit.logic.commands.emailcommand.EmailContentsNextCommand;
import seedu.recruit.logic.commands.emailcommand.EmailContentsSelectCommand;
import seedu.recruit.logic.commands.emailcommand.EmailRecipientsAddCommand;
import seedu.recruit.logic.commands.emailcommand.EmailRecipientsNextCommand;
import seedu.recruit.logic.commands.emailcommand.EmailRecipientsSelectCommand;
import seedu.recruit.logic.commands.emailcommand.EmailSendBackCommand;
import seedu.recruit.logic.commands.emailcommand.EmailSendCommand;
import seedu.recruit.logic.commands.emailcommand.EmailSendSendCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;

/**
 * Parser for the email commands to reduce clutter inside RecruitBookParser
 */
public class EmailParser {

    /**
     * Constructor to parse commands if the logic state is something email related
     * @param commandWord name of the command taken from RecruitBookParser
     * @param arguments arguments of the command taken from RecruitBookParser
     * @param state logic state
     * @param emailUtil emailUtil to get boolean value of isAreRecipientsCandidates.
     * @return the email command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String commandWord, String arguments, LogicState state, EmailUtil emailUtil)
            throws ParseException {
        //Email command set recipients step
        if (state.nextCommand.equals(EmailRecipientsSelectCommand.COMMAND_LOGIC_STATE)) {
            switch (commandWord) {

            case ListCandidateCommand.COMMAND_WORD:
                return new ListCandidateCommand();

            case ListCompanyCommand.COMMAND_WORD:
                return new ListCompanyCommand();

            case FindCandidateCommand.COMMAND_WORD:
                return new FindCandidateCommandParser().parse(arguments);

            case FindCompanyCommand.COMMAND_WORD:
                return new FindCompanyCommandParser().parse(arguments);

            case SwitchBookCommand.COMMAND_WORD:
                return new SwitchBookCommand();

            case EMAIL_NEXT_COMMAND:
                return new EmailRecipientsNextCommand();

            case EMAIL_ADD_COMMAND:
                return new EmailRecipientsAddCommand();

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }

        //Email command set contents step. Allow certain commands depending whether
        //recipients are candidates or job offers.
        } else if (state.nextCommand.equals(EmailContentsSelectCommand.COMMAND_LOGIC_STATE)
                && emailUtil.isAreRecipientsCandidates()) {
            switch (commandWord) {

            case ListCompanyCommand.COMMAND_WORD:
                return new ListCompanyCommand();

            case FindCompanyCommand.COMMAND_WORD:
                return new FindCompanyCommandParser().parse(arguments);

            case EMAIL_NEXT_COMMAND:
                return new EmailContentsNextCommand();

            case EMAIL_ADD_COMMAND:
                return new EmailContentsAddCommand();

            case EMAIL_BACK_COMMAND:
                return new EmailContentsBackCommand();

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        } else if (state.nextCommand.equals(EmailContentsSelectCommand.COMMAND_LOGIC_STATE)
                && !emailUtil.isAreRecipientsCandidates()) {
            switch (commandWord) {

            case ListCandidateCommand.COMMAND_WORD:
                return new ListCandidateCommand();

            case FindCandidateCommand.COMMAND_WORD:
                return new FindCandidateCommandParser().parse(arguments);

            case EMAIL_NEXT_COMMAND:
                return new EmailContentsNextCommand();

            case EMAIL_ADD_COMMAND:
                return new EmailContentsAddCommand();

            case EMAIL_BACK_COMMAND:
                return new EmailContentsBackCommand();

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }

        //Email command send step. Choose to send or to go back to edit recipients/contents
        } else if (state.nextCommand.equals(EmailSendCommand.COMMAND_LOGIC_STATE)) {
            switch (commandWord) {

            case EMAIL_SEND_COMMAND:
                return new EmailSendSendCommand();

            case EMAIL_BACK_COMMAND:
                return new EmailSendBackCommand();

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        } else {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
