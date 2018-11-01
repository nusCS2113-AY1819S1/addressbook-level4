package seedu.recruit.logic.parser;

import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.LogicState;
import seedu.recruit.logic.commands.AddCandidateCommand;
import seedu.recruit.logic.commands.AddCompanyCommand;
import seedu.recruit.logic.commands.AddJobCommand;
import seedu.recruit.logic.commands.AddJobDetailsCommand;
import seedu.recruit.logic.commands.BlacklistCommand;
import seedu.recruit.logic.commands.CancelCommand;
import seedu.recruit.logic.commands.ClearCandidateBookCommand;
import seedu.recruit.logic.commands.ClearCompanyBookCommand;
import seedu.recruit.logic.commands.Command;
import seedu.recruit.logic.commands.DeleteCandidateCommand;
import seedu.recruit.logic.commands.DeleteCompanyCommand;
import seedu.recruit.logic.commands.DeleteJobOfferCommand;
import seedu.recruit.logic.commands.DeleteShortlistedCandidateCommand;
import seedu.recruit.logic.commands.DeleteShortlistedCandidateInitializationCommand;
import seedu.recruit.logic.commands.EditCandidateCommand;
import seedu.recruit.logic.commands.EditCompanyCommand;
import seedu.recruit.logic.commands.EditJobDetailsCommand;
import seedu.recruit.logic.commands.ExitCommand;
import seedu.recruit.logic.commands.FindCandidateCommand;
import seedu.recruit.logic.commands.FindCompanyCommand;
import seedu.recruit.logic.commands.FindJobOfferCommand;
import seedu.recruit.logic.commands.HelpCommand;
import seedu.recruit.logic.commands.HistoryCommand;
import seedu.recruit.logic.commands.ListCandidateCommand;
import seedu.recruit.logic.commands.ListCompanyCommand;
import seedu.recruit.logic.commands.RedoCandidateBookCommand;
import seedu.recruit.logic.commands.RedoCompanyBookCommand;
import seedu.recruit.logic.commands.SelectCandidateCommand;
import seedu.recruit.logic.commands.SelectCompanyCommand;
import seedu.recruit.logic.commands.SelectJobCommand;
import seedu.recruit.logic.commands.ShortlistCandidateCommand;
import seedu.recruit.logic.commands.ShortlistCandidateInitializationCommand;
import seedu.recruit.logic.commands.SortCandidateCommand;
import seedu.recruit.logic.commands.SortCompanyCommand;
import seedu.recruit.logic.commands.SwitchBookCommand;
import seedu.recruit.logic.commands.UndoCandidateBookCommand;
import seedu.recruit.logic.commands.UndoCompanyBookCommand;
import seedu.recruit.logic.commands.emailcommand.EmailContentsSelectCommand;
import seedu.recruit.logic.commands.emailcommand.EmailInitialiseCommand;
import seedu.recruit.logic.commands.emailcommand.EmailRecipientsSelectCommand;
import seedu.recruit.logic.commands.emailcommand.EmailSendCommand;

import seedu.recruit.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class RecruitBookParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @param state current Logic State, used for multi step commands
     * @param emailUtil emailUtil variable passed from model manager to access boolean value isAreRecipientsCandidates
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, LogicState state, EmailUtil emailUtil) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());

        if (!matcher.matches()) {
            if (state.nextCommand.equals("primary")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
            } else {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
            }
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        if (!state.nextCommand.equals("primary")) {
            if (commandWord.equals(CancelCommand.COMMAND_WORD)) {
                return new CancelCommand(state.nextCommand);
            }

            switch (state.nextCommand) {

            case AddJobDetailsCommand.COMMAND_WORD:
                return new AddJobDetailsCommandParser().parse(userInput);

            case EmailContentsSelectCommand.COMMAND_LOGIC_STATE:
            case EmailRecipientsSelectCommand.COMMAND_LOGIC_STATE:
            case EmailSendCommand.COMMAND_LOGIC_STATE:
                return new EmailParser().parseCommand(commandWord, arguments, state, emailUtil);

            case ShortlistCandidateCommand.COMMAND_LOGIC_STATE:
            case SelectCompanyCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST:
            case SelectJobCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST:
            case SelectCandidateCommand.COMMAND_LOGIC_STATE:
                return new ShortlistParser().parseCommand(commandWord, arguments, state);

            case DeleteShortlistedCandidateCommand.COMMAND_LOGIC_STATE:
            case SelectCompanyCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST_DELETE:
            case SelectJobCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST_DELETE:
                return new DeleteShortlistedCandidateParser().parseCommand(commandWord, arguments, state);

            default:
                LogicManager.setLogicState("primary");
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        } else {
            switch (commandWord) {
            case AddCandidateCommand.COMMAND_WORD:
                return new AddCandidateCommandParser().parse(arguments);

            case AddJobCommand.COMMAND_WORD:
                return new AddJobCommand();

            case AddCompanyCommand.COMMAND_WORD:
                return new AddCompanyCommandParser().parse(arguments);

            case BlacklistCommand.COMMAND_WORD:
                return new BlacklistCommandParser().parse(arguments);

            case EditCandidateCommand.COMMAND_WORD:
                return new EditCandidateCommandParser().parse(arguments);

            case EditCompanyCommand.COMMAND_WORD:
                return new EditCompanyCommandParser().parse(arguments);

            case EditJobDetailsCommand.COMMAND_WORD:
                return new EditJobDetailsCommandParser().parse(arguments);

            case SelectCandidateCommand.COMMAND_WORD:
                return new SelectCandidateCommandParser().parse(arguments);

            case SelectCompanyCommand.COMMAND_WORD:
                return new SelectCompanyCommandParser().parse(arguments);

            case SelectJobCommand.COMMAND_WORD:
                return new SelectJobCommandParser().parse(arguments);

            case DeleteCandidateCommand.COMMAND_WORD:
                return new DeleteCandidateCommandParser().parse(arguments);

            case DeleteCompanyCommand.COMMAND_WORD:
                return new DeleteCompanyCommandParser().parse(arguments);

            case DeleteJobOfferCommand.COMMAND_WORD:
                return new DeleteJobOfferCommandParser().parse(arguments);

            case DeleteShortlistedCandidateInitializationCommand.COMMAND_WORD:
                return new DeleteShortlistedCandidateInitializationCommand();

            case ClearCandidateBookCommand.COMMAND_WORD:
                return new ClearCandidateBookCommand();

            case ClearCompanyBookCommand.COMMAND_WORD:
                return new ClearCompanyBookCommand();

            case FindCandidateCommand.COMMAND_WORD:
                return new FindCandidateCommandParser().parse(arguments);

            case FindCompanyCommand.COMMAND_WORD:
                return new FindCompanyCommandParser().parse(arguments);

            case FindJobOfferCommand.COMMAND_WORD:
                return new FindJobOfferCommandParser().parse(arguments);

            case HistoryCommand.COMMAND_WORD:
                return new HistoryCommand();

            case SortCandidateCommand.COMMAND_WORD:
                return new SortCandidateCommandParser().parse(arguments);

            case SortCompanyCommand.COMMAND_WORD:
                return new SortCompanyCommandParser().parse(arguments);

            case ListCandidateCommand.COMMAND_WORD:
                return new ListCandidateCommand();

            case ListCompanyCommand.COMMAND_WORD:
                return new ListCompanyCommand();

            case SwitchBookCommand.COMMAND_WORD:
                return new SwitchBookCommand();

            case ShortlistCandidateInitializationCommand.COMMAND_WORD:
                return new ShortlistCandidateInitializationCommand();

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            case UndoCandidateBookCommand.COMMAND_WORD:
                return new UndoCandidateBookCommand();

            case RedoCandidateBookCommand.COMMAND_WORD:
                return new RedoCandidateBookCommand();

            case UndoCompanyBookCommand.COMMAND_WORD:
                return new UndoCompanyBookCommand();

            case RedoCompanyBookCommand.COMMAND_WORD:
                return new RedoCompanyBookCommand();

            case EmailInitialiseCommand.COMMAND_WORD:
                return new EmailInitialiseCommand();

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        }
    }
}
