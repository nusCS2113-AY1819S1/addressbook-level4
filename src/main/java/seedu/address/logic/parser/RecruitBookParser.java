package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.LogicManager;
import seedu.address.logic.LogicState;
import seedu.address.logic.commands.AddCandidateCommand;
import seedu.address.logic.commands.AddCompanyCommand;
import seedu.address.logic.commands.AddJobCommand;
import seedu.address.logic.commands.AddJobDetailsCommand;
import seedu.address.logic.commands.CancelCommand;
import seedu.address.logic.commands.ClearCandidateBookCommand;
import seedu.address.logic.commands.ClearJobBookCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCandidateCommand;
import seedu.address.logic.commands.EmailCommand.EmailCommand;
import seedu.address.logic.commands.EmailCommand.EmailInitialiseCommand;
import seedu.address.logic.commands.EmailCommand.EmailSelectContentsCommand;
import seedu.address.logic.commands.EmailCommand.EmailSelectRecipientsCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class RecruitBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    public static EmailCommand emailCommand;

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, LogicState state) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            if(!state.nextCommand.equals("primary")) {
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

            if(state.nextCommand.equals(EmailSelectRecipientsCommand.COMMAND_LOGIC_STATE)) {
                switch (commandWord) {

                case FindCommand.COMMAND_WORD:
                    return new FindCommandParser().parse(arguments);

                case ListCommand.COMMAND_WORD:
                    return new ListCommand();
                }
            } else if (state.nextCommand.equals(EmailSelectContentsCommand.COMMAND_LOGIC_STATE)
                    && emailCommand.isAreRecipientsCandidates()) {
                switch (commandWord) {

                    case FindCommand.COMMAND_WORD:
                        return new FindCommandParser().parse(arguments);

                    case ListCommand.COMMAND_WORD:
                        return new ListCommand();
                }
            } else if (state.nextCommand.equals(EmailSelectContentsCommand.COMMAND_LOGIC_STATE)
                    && !emailCommand.isAreRecipientsCandidates()) {
                switch (commandWord) {

                    case FindCommand.COMMAND_WORD:
                        return new FindCommandParser().parse(arguments);

                    case ListCommand.COMMAND_WORD:
                        return new ListCommand();
                }
            } else {
                switch (state.nextCommand) {

                case AddJobDetailsCommand.COMMAND_WORD:
                    return new AddJobDetailsCommandParser().parse(userInput);

                default:
                    LogicManager.setLogicState("primary");
                }
            }
        } else {
            switch (commandWord) {

            case AddCandidateCommand.COMMAND_WORD:
                return new AddCandidateCommandParser().parse(arguments);

            case AddJobCommand.COMMAND_WORD:
                return new AddJobCommand();

            case EditCandidateCommand.COMMAND_WORD:
                return new EditCandidateCommandParser().parse(arguments);

            case SelectCommand.COMMAND_WORD:
                return new SelectCommandParser().parse(arguments);

            case DeleteCommand.COMMAND_WORD:
                return new DeleteCommandParser().parse(arguments);

            case ClearCandidateBookCommand.COMMAND_WORD:
                return new ClearCandidateBookCommand();

            case ClearJobBookCommand.COMMAND_WORD:
                return new ClearJobBookCommand();

            case FindCommand.COMMAND_WORD:
                return new FindCommandParser().parse(arguments);

            case ListCommand.COMMAND_WORD:
                return new ListCommand();

            case HistoryCommand.COMMAND_WORD:
                return new HistoryCommand();

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

            case AddCompanyCommand.COMMAND_WORD:
                return new AddCompanyCommandParser().parse(arguments);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        }
    }
}
