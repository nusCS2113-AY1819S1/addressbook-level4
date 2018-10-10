package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import seedu.address.logic.commands.*;
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

        case AddDCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditDCommand.COMMAND_WORD:
            return new EditDCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteDCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindDCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListDCommand.COMMAND_WORD:
            return new ListDCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoDCommand.COMMAND_WORD:
            return new UndoDCommand();

        case AddTransactionCommand.COMMAND_WORD:
            return new AddTransactionCommandParser().parse(arguments);
            
        case ViewLastTransaction.COMMAND_WORD:
            return new ViewLastTransaction();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();
            
        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();

        case CreateUserCommand.COMMAND_WORD:
            return new CreateUserCommandParser().parse(arguments);

        case DeleteUserCommand.COMMAND_WORD:
            return new DeleteUserCommandParser().parse(arguments);

        case ChangeUserPasswordCommand.COMMAND_WORD:
            return new ChangeUserPasswordCommandParser().parse(arguments);
            
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
