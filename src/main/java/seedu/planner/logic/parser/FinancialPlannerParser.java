package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.planner.logic.commands.AddCommand;
import seedu.planner.logic.commands.AddLimitCommand;
import seedu.planner.logic.commands.AddMonthlyLimitCommand;
import seedu.planner.logic.commands.ArchiveCommand;
import seedu.planner.logic.commands.CheckLimitCommand;
import seedu.planner.logic.commands.ClearCommand;
import seedu.planner.logic.commands.Command;
import seedu.planner.logic.commands.DeleteByDateCommand;
import seedu.planner.logic.commands.DeleteCommand;
import seedu.planner.logic.commands.DeleteLimitCommand;
import seedu.planner.logic.commands.DeleteMonthlyLimitCommand;
import seedu.planner.logic.commands.EditCommand;
import seedu.planner.logic.commands.EditLimitCommand;
import seedu.planner.logic.commands.EditMonthlyLimitCommand;
import seedu.planner.logic.commands.ExitCommand;
import seedu.planner.logic.commands.ExportExcelCommand;
import seedu.planner.logic.commands.FindCommand;
import seedu.planner.logic.commands.FindTagCommand;
import seedu.planner.logic.commands.HelpCommand;
import seedu.planner.logic.commands.HistoryCommand;
import seedu.planner.logic.commands.ImportExcelCommand;
import seedu.planner.logic.commands.ListCommand;
import seedu.planner.logic.commands.RedoCommand;
import seedu.planner.logic.commands.SelectCommand;
import seedu.planner.logic.commands.SortCommand;
import seedu.planner.logic.commands.StatisticCommand;
import seedu.planner.logic.commands.SummaryCommand;
import seedu.planner.logic.commands.UndoCommand;

import seedu.planner.logic.parser.exceptions.ParseException;


/**
 * Parses user input.
 */
public class FinancialPlannerParser {

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

        final String commandWord = matcher.group("commandWord").toLowerCase();
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

        case DeleteByDateCommand.COMMAND_WORD:
            return new DeleteCommandByDateEntryParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FindTagCommand.COMMAND_WORD:
            return new FindTagCommandParser().parse(arguments.toLowerCase());

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments.toLowerCase());

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

        case AddLimitCommand.COMMAND_WORD:
            return new AddLimitCommandParser().parse(arguments);

        case AddMonthlyLimitCommand.COMMAND_WORD:
            return new AddMonthlyLimitCommandParser().parse(arguments);

        case DeleteLimitCommand.COMMAND_WORD:
            return new DeleteLimitCommandParser().parse(arguments);

        case DeleteMonthlyLimitCommand.COMMAND_WORD:
            return new DeleteMonthlyLimitCommand();
        case EditLimitCommand.COMMAND_WORD:
            return new EditLimitCommandParser().parse(arguments);

        case EditMonthlyLimitCommand.COMMAND_WORD:
            return new EditMonthlyLimitCommandParser().parse(arguments);

        case CheckLimitCommand.COMMAND_WORD:
            return new CheckLimitCommand();

        case SummaryCommand.COMMAND_WORD:
            return new SummaryCommandParser().parse(arguments);

        case StatisticCommand.COMMAND_WORD:
            return new StatisticCommandParser().parse(arguments);

        case ExportExcelCommand.COMMAND_WORD:
            return new ExportExcelCommandParser().parse(arguments);

        case ImportExcelCommand.COMMAND_WORD:
            return new ImportExcelCommandParser().parse(arguments);

        case ArchiveCommand.COMMAND_WORD:
            return new ArchiveCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
