package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.GREETING_MESSAGE_NONEWLINE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddExpensesCommand;
import seedu.address.logic.commands.AddLeavesCommand;
import seedu.address.logic.commands.AddRecruitmentPostCommand;
import seedu.address.logic.commands.AddScheduleCommand;
import seedu.address.logic.commands.AddWorksCommand;
import seedu.address.logic.commands.CalculateLeavesCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearExpensesCommand;
import seedu.address.logic.commands.ClearRecruitmentPostCommand;
import seedu.address.logic.commands.ClearScheduleCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteLeavesCommand;
import seedu.address.logic.commands.DeleteRecruitmentPostCommand;
import seedu.address.logic.commands.DeleteScheduleCommand;
import seedu.address.logic.commands.DeleteWorksCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditRecruitmentPostCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ModifyAllPayCommand;
import seedu.address.logic.commands.ModifyPayCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemoveExpensesCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SelectExpensesCommand;
import seedu.address.logic.commands.SelectRecruitmentPostCommand;
import seedu.address.logic.commands.SelectScheduleCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.addressbook.DayHourGreeting;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        DayHourGreeting greeting = new DayHourGreeting();
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    greeting.getGreeting() + GREETING_MESSAGE_NONEWLINE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case EditRecruitmentPostCommand.COMMAND_WORD:
        case EditRecruitmentPostCommand.COMMAND_ALIAS:
            return new EditRecruitmentPostCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteScheduleCommand.COMMAND_ALIAS:
        case DeleteScheduleCommand.COMMAND_WORD:
            return new DeleteScheduleCommandParser().parse(arguments);

        case DeleteRecruitmentPostCommand.COMMAND_WORD:
        case DeleteRecruitmentPostCommand.COMMAND_ALIAS:
            return new DeleteRecruitmentPostCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ModifyPayCommand.COMMAND_WORD:
        case ModifyPayCommand.COMMAND_ALIAS:
            return new ModifyPayCommandParser().parse(arguments);

        case ModifyAllPayCommand.COMMAND_WORD:
        case ModifyAllPayCommand.COMMAND_ALIAS:
            return new ModifyAllPayCommandParser().parse(arguments);

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

        case AddExpensesCommand.COMMAND_WORD:
        case AddExpensesCommand.COMMAND_ALIAS:
            return new AddExpensesCommandParser().parse(arguments);

        case RemoveExpensesCommand.COMMAND_WORD:
        case RemoveExpensesCommand.COMMAND_ALIAS:
            return new RemoveExpensesCommandParser().parse(arguments);

        case AddScheduleCommand.COMMAND_ALIAS:
        case AddScheduleCommand.COMMAND_WORD:
            return new AddScheduleCommandParser().parse(arguments);

        case AddLeavesCommand.COMMAND_ALIAS:
        case AddLeavesCommand.COMMAND_WORD:
            return new AddLeavesCommandParser().parse(arguments);

        case DeleteLeavesCommand.COMMAND_ALIAS:
        case DeleteLeavesCommand.COMMAND_WORD:
            return new DeleteLeavesCommandParser().parse(arguments);

        case AddWorksCommand.COMMAND_ALIAS:
        case AddWorksCommand.COMMAND_WORD:
            return new AddWorksCommandParser().parse(arguments);

        case DeleteWorksCommand.COMMAND_ALIAS:
        case DeleteWorksCommand.COMMAND_WORD:
            return new DeleteWorksCommandParser().parse(arguments);

        case CalculateLeavesCommand.COMMAND_ALIAS:
        case CalculateLeavesCommand.COMMAND_WORD:
            return new CalculateLeavesCommandParser().parse(arguments);

        case SelectExpensesCommand.COMMAND_WORD:
        case SelectExpensesCommand.COMMAND_ALIAS:
            return new SelectExpensesCommandParser().parse(arguments);

        case SelectScheduleCommand.COMMAND_ALIAS:
        case SelectScheduleCommand.COMMAND_WORD:
            return new SelectScheduleCommandParser().parse(arguments);

        case SelectRecruitmentPostCommand.COMMAND_WORD:
        case SelectRecruitmentPostCommand.COMMAND_ALIAS:
            return new SelectRecruitmentPostCommandParser().parse(arguments);

        case ClearScheduleCommand.COMMAND_ALIAS:
        case ClearScheduleCommand.COMMAND_WORD:
            return new ClearScheduleCommand();

        case ClearExpensesCommand.COMMAND_WORD:
        case ClearExpensesCommand.COMMAND_ALIAS:
            return new ClearExpensesCommand();

        case ClearRecruitmentPostCommand.COMMAND_WORD:
        case ClearRecruitmentPostCommand.COMMAND_ALIAS:
            return new ClearRecruitmentPostCommand();

        case AddRecruitmentPostCommand.COMMAND_WORD:
        case AddRecruitmentPostCommand.COMMAND_ALIAS:
            return new AddRecruitmentPostCommandParser().parse(arguments);

        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND + greeting.getGreeting() + GREETING_MESSAGE_NONEWLINE);
        }
    }

}
