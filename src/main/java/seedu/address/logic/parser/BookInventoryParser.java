package seedu.address.logic.parser;
import static seedu.address.commons.core.Messages.MESSAGE_ACCESS_DENIED;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_SIMILARITY_FOUND;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.DiceCoefficient.diceCoefficient;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CheckCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
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

import seedu.address.ui.BookListPanel;

/**
 * Parses user input.
 */
public class BookInventoryParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static ArrayList<String> commandList;
    private static DiceCoefficient diceCoefficient;
    private static final double DICE_COEFFICIENT_THRESHOLD = 0.5;
    @FXML
    private StackPane personListPanelPlaceholder;

    private Logic logic;
    private BookListPanel bookListPanel;

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
        Command finalCommand;
        final String finalCommandWord;
        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        diceCoefficient = new DiceCoefficient();
        commandList = new ArrayList<>();
        commandList.add(AddCommand.COMMAND_WORD);
        commandList.add(EditCommand.COMMAND_WORD);
        commandList.add(SellCommand.COMMAND_WORD);
        commandList.add(SelectCommand.COMMAND_WORD);
        commandList.add(DeleteCommand.COMMAND_WORD);
        commandList.add(StockCommand.COMMAND_WORD);
        commandList.add(ClearCommand.COMMAND_WORD);
        commandList.add(FindCommand.COMMAND_WORD);
        commandList.add(CheckCommand.COMMAND_WORD);
        commandList.add(ListCommand.COMMAND_WORD);
        commandList.add(HistoryCommand.COMMAND_WORD);
        commandList.add(ExitCommand.COMMAND_WORD);
        commandList.add(HelpCommand.COMMAND_WORD);
        commandList.add(UndoCommand.COMMAND_WORD);
        commandList.add(RedoCommand.COMMAND_WORD);
        commandList.add(ViewStatisticCommand.COMMAND_WORD);


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
            for (String command : commandList) {
                if (diceCoefficient(commandWord, command) > DICE_COEFFICIENT_THRESHOLD) {
                    throw new ParseException(MESSAGE_SIMILARITY_FOUND + command + "?");
                }
            }
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
        }

        if (finalCommand != null) {
            try {
                Role.checkAccess(finalCommandWord);
            } catch (IllegalAccessException e) {
                throw new ParseException(MESSAGE_ACCESS_DENIED + finalCommandWord + ".");

            }
            return finalCommand;
        }
        return null;
    }
}
