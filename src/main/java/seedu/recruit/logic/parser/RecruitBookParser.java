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
import seedu.recruit.logic.commands.CancelCommand;
import seedu.recruit.logic.commands.ClearCandidateBookCommand;
import seedu.recruit.logic.commands.ClearCompanyBookCommand;
import seedu.recruit.logic.commands.Command;
import seedu.recruit.logic.commands.DeleteCandidateCommand;
import seedu.recruit.logic.commands.DeleteCompanyCommand;
import seedu.recruit.logic.commands.DeleteJobOfferCommand;
import seedu.recruit.logic.commands.EditCandidateCommand;
import seedu.recruit.logic.commands.EditCompanyCommand;
import seedu.recruit.logic.commands.ExitCommand;
import seedu.recruit.logic.commands.FilterCommand;
import seedu.recruit.logic.commands.FindCandidateCommand;
import seedu.recruit.logic.commands.FindCompanyCommand;
import seedu.recruit.logic.commands.HelpCommand;
import seedu.recruit.logic.commands.HistoryCommand;
import seedu.recruit.logic.commands.ListCommand;
import seedu.recruit.logic.commands.RedoCommand;
import seedu.recruit.logic.commands.SelectCommand;
import seedu.recruit.logic.commands.SortCommand;
import seedu.recruit.logic.commands.SwitchBookCommand;
import seedu.recruit.logic.commands.UndoCommand;
import seedu.recruit.logic.commands.emailcommand.EmailInitialiseCommand;
import seedu.recruit.logic.commands.emailcommand.EmailSelectContentsCommand;
import seedu.recruit.logic.commands.emailcommand.EmailSelectRecipientsCommand;
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

            case EmailSelectContentsCommand.COMMAND_LOGIC_STATE:
            case EmailSelectRecipientsCommand.COMMAND_LOGIC_STATE:
            case EmailSendCommand.COMMAND_LOGIC_STATE:
                return new EmailParser().parseCommand(commandWord, arguments, state, emailUtil);

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

            case EditCandidateCommand.COMMAND_WORD:
                return new EditCandidateCommandParser().parse(arguments);

            case EditCompanyCommand.COMMAND_WORD:
                return new EditCompanyCommandParser().parse(arguments);

            case SelectCommand.COMMAND_WORD:
                return new SelectCommandParser().parse(arguments);

            case DeleteCandidateCommand.COMMAND_WORD:
                return new DeleteCandidateCommandParser().parse(arguments);

            case DeleteCompanyCommand.COMMAND_WORD:
                return new DeleteCompanyCommandParser().parse(arguments);

            case DeleteJobOfferCommand.COMMAND_WORD:
                return new DeleteJobOfferCommandParser().parse(arguments);

            case ClearCandidateBookCommand.COMMAND_WORD:
                return new ClearCandidateBookCommand();

            case ClearCompanyBookCommand.COMMAND_WORD:
                return new ClearCompanyBookCommand();

            case FilterCommand.COMMAND_WORD:
                return new FilterCommandParser().parse(arguments);

            case FindCandidateCommand.COMMAND_WORD:
                return new FindCandidateCommandParser().parse(arguments);

            case FindCompanyCommand.COMMAND_WORD:
                return new FindCompanyCommandParser().parse(arguments);

            case HistoryCommand.COMMAND_WORD:
                return new HistoryCommand();

            case SortCommand.COMMAND_WORD:
                return new SortCommandParser().parse(arguments);

            case ListCommand.COMMAND_WORD:
                return new ListCommand();

            case SwitchBookCommand.COMMAND_WORD:
                return new SwitchBookCommand();

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            case UndoCommand.COMMAND_WORD:
                return new UndoCommand();

            case RedoCommand.COMMAND_WORD:
                return new RedoCommand();

            case EmailInitialiseCommand.COMMAND_WORD:
                return new EmailInitialiseCommand();

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        }
    }
}
