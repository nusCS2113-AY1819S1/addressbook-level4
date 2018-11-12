package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddLeaveCommand;
import seedu.address.logic.commands.CheckCommand;
import seedu.address.logic.commands.CheckLoginStatusCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteLeaveCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditLeaveCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterDepartmentCommand;
import seedu.address.logic.commands.FilterLeaveCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListDepartmentCommand;
import seedu.address.logic.commands.ListLeaveCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.ResetCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SetDepartmentCommand;
import seedu.address.logic.commands.SetPriorityLevelCommand;
import seedu.address.logic.commands.SetScheduleCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FilterDepartmentCommand.COMMAND_WORD:
        case FilterDepartmentCommand.COMMAND_ALIAS:
            return new FilterDepartmentCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListDepartmentCommand.COMMAND_WORD:
        case ListDepartmentCommand.COMMAND_ALIAS:
            return new ListDepartmentCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ScheduleCommand.COMMAND_WORD:
            return new ScheduleCommandParser().parse(arguments);

        case SetScheduleCommand.COMMAND_WORD:
            return new SetScheduleCommandParser().parse(arguments);

        case SetDepartmentCommand.COMMAND_WORD:
        case SetDepartmentCommand.COMMAND_ALIAS:
            return new SetDepartmentCommandParser().parse(arguments);

        case AddLeaveCommand.COMMAND_WORD:
            return new AddLeaveParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();

        case CheckLoginStatusCommand.COMMAND_WORD:
            return new CheckLoginStatusCommand();

        case CheckCommand.COMMAND_WORD:
            return new CheckCommandParser().parse(arguments);

        case DeleteLeaveCommand.COMMAND_WORD:
        case DeleteLeaveCommand.COMMAND_ALIAS:
            return new DeleteLeaveCommandParser().parse(arguments);

        case FilterLeaveCommand.COMMAND_WORD:
        case FilterLeaveCommand.COMMAND_ALIAS:
            return new FilterLeaveCommandParser().parse(arguments);

        case ListLeaveCommand.COMMAND_WORD:
            return new ListLeaveCommand();

        case EditLeaveCommand.COMMAND_APPROVE:
            return new ApproveLeaveCommandParser().parse(arguments);

        case EditLeaveCommand.COMMAND_REJECT:
            return new RejectLeaveCommandParser().parse(arguments);

        case SetPriorityLevelCommand.COMMAND_WORD:
            return new SetPriorityLevelCommandParser().parse(arguments);

        case ResetCommand.COMMAND_WORD:
            return new ResetCommand();
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
