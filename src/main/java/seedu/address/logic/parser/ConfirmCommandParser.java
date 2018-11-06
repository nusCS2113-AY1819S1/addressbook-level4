package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddDrinkCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.ViewTransactionsCommand;
import seedu.address.logic.commands.accountant.AnalyseCostsCommand;
import seedu.address.logic.commands.stocktaker.BuyDrinkCommand;
import seedu.address.logic.commands.stocktaker.SellDrinkCommand;
import seedu.address.logic.commands.user.ChangePasswordCommand;
import seedu.address.logic.commands.user.CreateAccountCommand;
import seedu.address.logic.commands.user.DeleteAccountCommand;
import seedu.address.logic.commands.user.LogoutCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ConfirmCommandParser {

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
    public Boolean isCommandNeedToConfirm (String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        // ========= manager commands ==================
        case AddDrinkCommand.COMMAND_WORD:
            return true;

        // ========= stock taker commands ==================
        case SellDrinkCommand.COMMAND_WORD:
            return true;

        case BuyDrinkCommand.COMMAND_WORD:
            return true;

        case ViewTransactionsCommand.COMMAND_WORD:
            return false;

        // ========= accountant commands ==================

        case AnalyseCostsCommand.COMMAND_WORD:
            return false;



        // ==========login related command===============//

        case ChangePasswordCommand.COMMAND_WORD:
            return true;


        case CreateAccountCommand.COMMAND_WORD:
            return true;


        case DeleteAccountCommand.COMMAND_WORD:
            return true;

        case LogoutCommand.COMMAND_WORD:
            return false;
        //=============general command==================//
        case SelectCommand.COMMAND_WORD:
            return false;

        case DeleteCommand.COMMAND_WORD:
            return true;

        case ClearCommand.COMMAND_WORD:
            return true;

        case FindCommand.COMMAND_WORD:
            return false;

        case ListCommand.COMMAND_WORD:
            return false;

        case HistoryCommand.COMMAND_WORD:
            return false;
        case ExitCommand.COMMAND_WORD:
            return false;

        case HelpCommand.COMMAND_WORD:
            return false;

        default:
            return false;
        }
    }

}
