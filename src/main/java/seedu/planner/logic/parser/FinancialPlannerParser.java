package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

<<<<<<< HEAD
import seedu.address.logic.commands.DeleteCommandByDateEntry;
import seedu.address.logic.parser.DeleteCommandByDateEntryParser;
=======
<<<<<<< HEAD:src/main/java/seedu/address/logic/parser/FinancialPlannerParser.java
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteCommandByDateEntry;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
=======
>>>>>>> 1047a67bd53031afa580deb37f63665cf34545ef
import seedu.planner.logic.commands.AddCommand;
import seedu.planner.logic.commands.ClearCommand;
import seedu.planner.logic.commands.Command;
import seedu.planner.logic.commands.DeleteCommand;
import seedu.planner.logic.commands.EditCommand;
import seedu.planner.logic.commands.ExitCommand;
import seedu.planner.logic.commands.FindCommand;
import seedu.planner.logic.commands.FindTagCommand;
import seedu.planner.logic.commands.HelpCommand;
import seedu.planner.logic.commands.HistoryCommand;
import seedu.planner.logic.commands.ListCommand;
import seedu.planner.logic.commands.RedoCommand;
import seedu.planner.logic.commands.SelectCommand;
import seedu.planner.logic.commands.UndoCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
<<<<<<< HEAD
=======
>>>>>>> 936a266304811392cda80acfbf3d1820aac87fed:src/main/java/seedu/planner/logic/parser/FinancialPlannerParser.java
>>>>>>> 1047a67bd53031afa580deb37f63665cf34545ef

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

        case DeleteCommandByDateEntry.COMMAND_WORD:
            return new DeleteCommandByDateEntryParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FindTagCommand.COMMAND_WORD:
            return new FindTagCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

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

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
