package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddDistributorCommand;
import seedu.address.logic.commands.AddReminderCommand;
import seedu.address.logic.commands.AddTransactionCommand;
import seedu.address.logic.commands.ChangePasswordCommand;
import seedu.address.logic.commands.ClearDistributorsCommand;
import seedu.address.logic.commands.ClearProductCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteDistributorCommand;
import seedu.address.logic.commands.DeleteProductCommand;
import seedu.address.logic.commands.DeregisterCommand;
import seedu.address.logic.commands.EditDistributorCommand;
import seedu.address.logic.commands.EditProductCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindDistributorCommand;
import seedu.address.logic.commands.FindProductCommand;
import seedu.address.logic.commands.FindTagDistributorCommand;
import seedu.address.logic.commands.FindTagProductCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListDistributorCommand;
import seedu.address.logic.commands.ListProductCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.RedoDistributorCommand;
import seedu.address.logic.commands.RedoProductCommand;
import seedu.address.logic.commands.RegisterCommand;
import seedu.address.logic.commands.RemoveReminderCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.ThreadDueRemindersCommand;
import seedu.address.logic.commands.UndoDistributorCommand;
import seedu.address.logic.commands.UndoProductCommand;
import seedu.address.logic.commands.ViewAllRemindersCommand;
import seedu.address.logic.commands.ViewAllTransactionsInDayCommand;
import seedu.address.logic.commands.ViewDueRemindersCommand;
import seedu.address.logic.commands.ViewLastTransactionCommand;
import seedu.address.logic.commands.ViewTransactionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AllCommandsParser {

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
        case AddDistributorCommand.COMMAND_WORD:
            return new AddDistributorsCommandParser().parse(arguments);

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditDistributorCommand.COMMAND_WORD:
            return new EditDistributorsCommandParser().parse(arguments);

        case EditProductCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteDistributorCommand.COMMAND_WORD:
            return new DeleteDistributorsCommandParser().parse(arguments);

        case DeleteProductCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearProductCommand.COMMAND_WORD:
            return new ClearProductCommand();

        case ClearDistributorsCommand.COMMAND_WORD:
            return new ClearDistributorsCommand();

        case FindDistributorCommand.COMMAND_WORD:
            return new FindDistributorsCommandParser().parse(arguments);

        case FindTagDistributorCommand.COMMAND_WORD:
            return new FindTagDistributorsCommandParser().parse(arguments);

        case FindProductCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FindTagProductCommand.COMMAND_WORD:
            return new FindTagCommandParser().parse(arguments);

        case ListDistributorCommand.COMMAND_WORD:
            return new ListDistributorCommand();

        case ListProductCommand.COMMAND_WORD:
            return new ListProductCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoDistributorCommand.COMMAND_WORD:
            return new UndoDistributorCommand();

        case UndoProductCommand.COMMAND_WORD:
            return new UndoProductCommand();

        case AddTransactionCommand.COMMAND_WORD:
            return new AddTransactionCommandParser().parse(arguments);

        case ViewLastTransactionCommand.COMMAND_WORD:
            return new ViewLastTransactionCommand();

        case RedoDistributorCommand.COMMAND_WORD:
            return new RedoDistributorCommand();

        case RedoProductCommand.COMMAND_WORD:
            return new RedoProductCommand();

        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();

        case RegisterCommand.COMMAND_WORD:
            return new RegisterCommandParser().parse(arguments);

        case DeregisterCommand.COMMAND_WORD:
            return new DeregisterCommandParser().parse(arguments);

        case ChangePasswordCommand.COMMAND_WORD:
            return new ChangePasswordCommandParser().parse(arguments);

        case AddReminderCommand.COMMAND_WORD:
            return new AddReminderCommandParser().parse(arguments);

        case RemoveReminderCommand.COMMAND_WORD:
            return new FinishedReminderCommandParser().parse(arguments);

        case ViewDueRemindersCommand.COMMAND_WORD:
            return new ViewDueRemindersCommand();

        case ThreadDueRemindersCommand.COMMAND_WORD:
            return new ThreadDueRemindersCommand();

        case ViewAllRemindersCommand.COMMAND_WORD:
            return new ViewAllRemindersCommand();

        case ViewAllTransactionsInDayCommand.COMMAND_WORD:
            return new ViewAllTransactionsInDayCommandParser().parse(arguments);

        case ViewTransactionCommand.COMMAND_WORD:
            return new ViewTransactionCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
