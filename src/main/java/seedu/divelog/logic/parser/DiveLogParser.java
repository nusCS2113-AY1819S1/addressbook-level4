package seedu.divelog.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.divelog.commons.core.Messages;
import seedu.divelog.logic.commands.AddCommand;
import seedu.divelog.logic.commands.ClearCommand;
import seedu.divelog.logic.commands.Command;
import seedu.divelog.logic.commands.CurrentPgCommand;
import seedu.divelog.logic.commands.DeleteCommand;
import seedu.divelog.logic.commands.EditCommand;
import seedu.divelog.logic.commands.ExitCommand;
import seedu.divelog.logic.commands.ExitPlanningCommand;
import seedu.divelog.logic.commands.FindCommand;
import seedu.divelog.logic.commands.HelpCommand;
import seedu.divelog.logic.commands.HistoryCommand;
import seedu.divelog.logic.commands.ListCommand;
import seedu.divelog.logic.commands.PlanningCommand;
import seedu.divelog.logic.commands.PortOverCommand;
import seedu.divelog.logic.commands.RedoCommand;
import seedu.divelog.logic.commands.SelectCommand;
import seedu.divelog.logic.commands.SetUnitsCommand;
import seedu.divelog.logic.commands.UndoCommand;
import seedu.divelog.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class DiveLogParser {

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
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);
        case CurrentPgCommand.COMMAND_WORD:
            //Fallthrough
        case CurrentPgCommand.COMMAND_ALIAS:
            return new CurrentPgCommand();

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

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

        case PlanningCommand.COMMAND_WORD_PLAN:
            return new PlanningCommand();

        case PortOverCommand.COMMAND_WORD_PORT_OVER:
            return new PortOverCommand();

        case ExitPlanningCommand.COMMAND_WORD_NORMAL:
            return new ExitPlanningCommand();


        case SetUnitsCommand.COMMAND_WORD:
            return new SetUnitsCommandParser().parse(arguments);


        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
