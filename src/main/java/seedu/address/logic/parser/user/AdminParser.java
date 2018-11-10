package seedu.address.logic.parser.user;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.ViewTransactionsCommand;
import seedu.address.logic.commands.accountant.AnalyseCostsCommand;
import seedu.address.logic.commands.accountant.AnalyseProfitCommand;
import seedu.address.logic.commands.accountant.AnalyseRevenueCommand;
import seedu.address.logic.commands.manager.AddDrinkCommand;
import seedu.address.logic.commands.manager.DeleteCommand;
import seedu.address.logic.commands.stocktaker.BuyDrinkCommand;
import seedu.address.logic.commands.stocktaker.SellDrinkCommand;
import seedu.address.logic.commands.user.ChangePasswordCommand;
import seedu.address.logic.commands.user.CreateAccountCommand;
import seedu.address.logic.commands.user.DeleteAccountCommand;
import seedu.address.logic.commands.user.LogoutCommand;
import seedu.address.logic.parser.DeleteCommandParser;
import seedu.address.logic.parser.EditCommandParser;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.logic.parser.SelectCommandParser;
import seedu.address.logic.parser.accountant.AnalyseCostsCommandParser;
import seedu.address.logic.parser.accountant.AnalyseProfitCommandParser;
import seedu.address.logic.parser.accountant.AnalyseRevenueCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.manager.AddDrinkCommandParser;
import seedu.address.logic.parser.stocktaker.BuyDrinkCommandParser;
import seedu.address.logic.parser.stocktaker.SellDrinkCommandParser;
/**
 * Parses user input.
 */
public class AdminParser {

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

        String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        // ========= manager commands ==================
        case AddDrinkCommand.COMMAND_WORD:
            return new AddDrinkCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);
        // ========= stock taker commands ==================
        case SellDrinkCommand.COMMAND_WORD:
            return new SellDrinkCommandParser().parse(arguments);

        case BuyDrinkCommand.COMMAND_WORD:
            return new BuyDrinkCommandParser ().parse(arguments);



        // ========= accountant commands ==================
        case AnalyseCostsCommand.COMMAND_WORD:
            return new AnalyseCostsCommandParser ().parse (arguments);

        case AnalyseProfitCommand.COMMAND_WORD:
            return new AnalyseProfitCommandParser ().parse (arguments);

        case AnalyseRevenueCommand.COMMAND_WORD:
            return new AnalyseRevenueCommandParser ().parse (arguments);

        // ==========login related command===============//
        case ChangePasswordCommand.COMMAND_WORD:
            return new ChangePasswordCommandParser().parse(arguments);


        case CreateAccountCommand.COMMAND_WORD:
            return new CreateAccountCommandParser().parse(arguments);


        case DeleteAccountCommand.COMMAND_WORD:
            return new DeleteAccountCommandParser().parse(arguments);

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();


        //=============general command==================//
        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);


        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ViewTransactionsCommand.COMMAND_WORD:
            return new ViewTransactionsCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
