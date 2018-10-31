package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddAccountCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.commands.ChangeStatusCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteAccountCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteLoanListCommand;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.EditAccountCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindAccountCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FoundCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListAccountsCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LoanListCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LostCommand;
import seedu.address.logic.commands.LostandFoundCommand;
import seedu.address.logic.commands.OpenCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ResetAccountsCommand;
import seedu.address.logic.commands.SaveCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.StatusCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewLoanListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class StockListParser {

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

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);
        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);
        case AddAccountCommand.COMMAND_WORD:
            return new AddAccountCommandParser().parse(arguments);
        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);
<<<<<<< HEAD
        case EditAccountCommand.COMMAND_WORD:
            return new EditAccountCommandParser().parse(arguments);
        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);
        case DeleteAccountCommand.COMMAND_WORD:
            return new DeleteAccountCommandParser().parse(arguments);
=======
>>>>>>> 4fc0ba719f53a27dde6207be50206f9fa32f6d47
        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);
        case ListCommand.COMMAND_WORD:
            return new ListCommand();
        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();
        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);
<<<<<<< HEAD
        case FindAccountCommand.COMMAND_WORD:
            return new FindAccountCommandParser().parse(arguments);
        case ListCommand.COMMAND_WORD:
            return new ListCommand();
        case ListAccountsCommand.COMMAND_WORD:
            return new ListAccountsCommand();
        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);
        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
=======
        case StatusCommand.COMMAND_WORD:
            return new StatusCommand();
        case ChangeStatusCommand.COMMAND_WORD:
            return new ChangeStatusCommandParser().parse(arguments);
        case LoanListCommand.COMMAND_WORD:
            return new LoanListCommandParser().parse(arguments);
        case ViewLoanListCommand.COMMAND_WORD:
            return new ViewLoanListCommand();
        case DeleteLoanListCommand.COMMAND_WORD:
            return new DeleteLoanListCommandParser().parse(arguments);
        case TagCommand.COMMAND_WORD:
            return new TagCommandParser().parse(arguments);
        case AddTagCommand.COMMAND_WORD:
            return new AddTagCommandParser().parse(arguments);
        case DeleteTagCommand.COMMAND_WORD:
            return new DeleteTagCommandParser().parse(arguments);
        case LostCommand.COMMAND_WORD:
            return new LostCommandParser().parse(arguments);
        case FoundCommand.COMMAND_WORD:
            return new FoundCommandParser().parse(arguments);
        case LostandFoundCommand.COMMAND_WORD:
            return new LostandFoundCommand();
>>>>>>> 4fc0ba719f53a27dde6207be50206f9fa32f6d47
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();
        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();
<<<<<<< HEAD
        case ResetAccountsCommand.COMMAND_WORD:
            return new ResetAccountsCommand();
        case ChangeStatusCommand.COMMAND_WORD:
            return new ChangeStatusCommandParser().parse(arguments);
=======
        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();
>>>>>>> 4fc0ba719f53a27dde6207be50206f9fa32f6d47
        case SaveCommand.COMMAND_WORD:
            return new SaveCommandParser().parse(arguments);
        case OpenCommand.COMMAND_WORD:
            return new OpenCommandParser().parse(arguments);
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
