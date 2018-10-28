package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddExpenditureCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.CheckExpenditureCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CompleteTaskCommand;
import seedu.address.logic.commands.DeleteExpenditureCommand;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.commands.EditExpenditureCommand;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExpenditureGetAdviceCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UncompleteTaskCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewTaskCommand;

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

        case AddTaskCommand.COMMAND_WORD:
            return new AddTaskCommandParser().parse(arguments);

        case AddExpenditureCommand.COMMAND_WORD:
            return new AddExpenditureCommandParser().parse(arguments);

        case EditTaskCommand.COMMAND_WORD:
            return new EditTaskCommandParser().parse(arguments);

        case EditExpenditureCommand.COMMAND_WORD:
            return new EditExpenditureCommandParser().parse(arguments);

        case DeleteExpenditureCommand.COMMAND_WORD:
            return new DeleteExpenditureCommandParser().parse(arguments);

        case DeleteTaskCommand.COMMAND_WORD:
            return new DeleteTaskCommandParser().parse(arguments);

        case CompleteTaskCommand.COMMAND_WORD:
            return new CompleteTaskCommandParser().parse(arguments);

        case UncompleteTaskCommand.COMMAND_WORD:
            return new UncompleteTaskCommandParser().parse(arguments);

        case ExpenditureGetAdviceCommand.COMMAND_WORD:
            return new ExpenditureGetAdviceCommandParser().parse(arguments);

        /*case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
        case ClearCommand.COMMAND_INITIAL:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);*/

        case CheckExpenditureCommand.COMMAND_WORD:
            return new CheckExpenditureCommandParser().parse(arguments);

        case ViewTaskCommand.COMMAND_WORD:
            return new ViewTaskCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
        //case HistoryCommand.COMMAND_ALIAS:
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
