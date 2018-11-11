package seedu.address.logic.parser;
import static seedu.address.commons.core.Messages.MESSAGE_ACCESS_DENIED;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_SIMILARITY_FOUND;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.DiceCoefficient;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CheckCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandList;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.Role;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SellCommand;
import seedu.address.logic.commands.StockCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewStatisticCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses user input.
 */
public class BookInventoryParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static DiceCoefficient diceCoefficient;
    private static final double DICE_COEFFICIENT_THRESHOLD = 0.5;


    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        SimilarityParser similarityParser = new SimilarityParser();
        CommandList commandList = new CommandList();
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        Command finalCommand;
        final String finalCommandWord;
        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case AddCommand.COMMAND_ALIAS:

        case AddCommand.COMMAND_WORD:
            finalCommandWord = AddCommand.COMMAND_WORD;
            finalCommand = new AddCommandParser().parse(arguments);
            break;
        case EditCommand.COMMAND_WORD:
            finalCommandWord = EditCommand.COMMAND_WORD;
            finalCommand = new EditCommandParser().parse(arguments);
            break;
        case SellCommand.COMMAND_WORD:
            finalCommandWord = SellCommand.COMMAND_WORD;
            finalCommand = new SellCommandParser().parse(arguments);
            break;
        case SelectCommand.COMMAND_WORD:
            finalCommandWord = SelectCommand.COMMAND_WORD;
            finalCommand = new SelectCommandParser().parse(arguments);
            break;
        case DeleteCommand.COMMAND_WORD:
            finalCommandWord = DeleteCommand.COMMAND_WORD;
            finalCommand = new DeleteCommandParser().parse(arguments);
            break;
        case StockCommand.COMMAND_WORD:
            finalCommandWord = StockCommand.COMMAND_WORD;
            finalCommand = new StockCommandParser().parse(arguments);
            break;
        case ClearCommand.COMMAND_ALIAS:
        case ClearCommand.COMMAND_WORD:
            finalCommandWord = ClearCommand.COMMAND_WORD;
            finalCommand = new ClearCommand();
            break;
        case FindCommand.COMMAND_WORD:
            finalCommandWord = FindCommand.COMMAND_WORD;
            finalCommand = new FindCommandParser().parse(arguments);
            break;
        case CheckCommand.COMMAND_WORD:
            finalCommandWord = CheckCommand.COMMAND_WORD;
            finalCommand = new CheckCommandParser().parse(arguments);
            break;
        case ListCommand.COMMAND_WORD:
            finalCommandWord = ListCommand.COMMAND_WORD;
            finalCommand = new ListCommand();
            break;
        case HistoryCommand.COMMAND_WORD:
            finalCommandWord = HistoryCommand.COMMAND_WORD;
            finalCommand = new HistoryCommand();
            break;
        case ExitCommand.COMMAND_WORD:
            finalCommandWord = ExitCommand.COMMAND_WORD;
            finalCommand = new ExitCommand();
            break;
        case HelpCommand.COMMAND_WORD:
            finalCommandWord = HelpCommand.COMMAND_WORD;
            finalCommand = new HelpCommand();
            break;
        case UndoCommand.COMMAND_WORD:
            finalCommandWord = UndoCommand.COMMAND_WORD;
            finalCommand = new UndoCommand();
            break;
        case RedoCommand.COMMAND_WORD:
            finalCommandWord = RedoCommand.COMMAND_WORD;
            finalCommand = new RedoCommand();
            break;
        case ViewStatisticCommand.COMMAND_WORD:
            finalCommandWord = ViewStatisticCommand.COMMAND_WORD;
            finalCommand = new ViewStatisticCommand();
            break;
        default:
        {
            /*
             * This segment finds out if a typo error is noticed by the Dice Algorithm.
             */
            String similarCommandFound = similarityParser
                    .performSimilarityCheck(commandWord, commandList.getCommandList());
            if (!similarCommandFound.isEmpty()) {
                throw new ParseException(MESSAGE_SIMILARITY_FOUND + similarCommandFound + "?");
            }
        }
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

        if (finalCommand != null) {
            try {
                Role.checkAccess(finalCommandWord);
            } catch (IllegalAccessException e) {
                throw new ParseException(MESSAGE_ACCESS_DENIED + finalCommandWord + ".");
            }
        }
        return finalCommand;
    }
    /**
     * This segment accepts command similar to the above method, but no authentication required.
     * Necessary for test.
     */
    public Command parseCommand_withoutAuthentication(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        SimilarityParser similarityParser = new SimilarityParser();
        CommandList commandList = new CommandList();
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        Command finalCommand;
        final String finalCommandWord;
        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case AddCommand.COMMAND_ALIAS:

        case AddCommand.COMMAND_WORD:
            finalCommand = new AddCommandParser().parse(arguments);
            break;
        case EditCommand.COMMAND_WORD:
            finalCommand = new EditCommandParser().parse(arguments);
            break;
        case SellCommand.COMMAND_WORD:
            finalCommand = new SellCommandParser().parse(arguments);
            break;
        case SelectCommand.COMMAND_WORD:
            finalCommand = new SelectCommandParser().parse(arguments);
            break;
        case DeleteCommand.COMMAND_WORD:
            finalCommand = new DeleteCommandParser().parse(arguments);
            break;
        case StockCommand.COMMAND_WORD:
            finalCommand = new StockCommandParser().parse(arguments);
            break;
        case ClearCommand.COMMAND_ALIAS:
        case ClearCommand.COMMAND_WORD:
            finalCommand = new ClearCommand();
            break;
        case FindCommand.COMMAND_WORD:
            finalCommand = new FindCommandParser().parse(arguments);
            break;
        case CheckCommand.COMMAND_WORD:
            finalCommand = new CheckCommandParser().parse(arguments);
            break;
        case ListCommand.COMMAND_WORD:
            finalCommand = new ListCommand();
            break;
        case HistoryCommand.COMMAND_WORD:
            finalCommand = new HistoryCommand();
            break;
        case ExitCommand.COMMAND_WORD:
            finalCommand = new ExitCommand();
            break;
        case HelpCommand.COMMAND_WORD:
            finalCommand = new HelpCommand();
            break;
        case UndoCommand.COMMAND_WORD:
            finalCommand = new UndoCommand();
            break;
        case RedoCommand.COMMAND_WORD:
            finalCommand = new RedoCommand();
            break;
        case ViewStatisticCommand.COMMAND_WORD:
            finalCommand = new ViewStatisticCommand();
            break;
        default:
        {
            /*
             * This segment finds out if a typo error is noticed by the Dice Algorithm.
             */
            String similarCommandFound = similarityParser
                   .performSimilarityCheck(commandWord, commandList.getCommandList());
            if (!similarCommandFound.isEmpty()) {
                throw new ParseException(MESSAGE_SIMILARITY_FOUND + similarCommandFound + "?");
            }
        }
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

        return finalCommand;
    }
}
