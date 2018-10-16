package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddSkillCommand;
import seedu.address.logic.commands.AddSkillLevelCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearSearchHistoryCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CreateAccountCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.LoginDialogBox;

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
        case LoginCommand.COMMAND_WORD:
            return new LoginUserIdPasswordCommandParser().parse(arguments);

        case CreateAccountCommand.COMMAND_WORD:
            //@@author Chocological-reused
            //Reused from https://stackoverflow.com/posts/6555051/revisions with minor modifications
            LoginDialogBox.setLoginDialogBox();

            String loginSelection = JOptionPane.showInputDialog(LoginDialogBox.getLoginFrame(),
                    "Please type in master password", null);
            //@@author
            switch (loginSelection) {
            case "123456789":
                return new CreateAccountCommandParser().parse(arguments);
            default:
                throw new IllegalArgumentException("Wrong master password!");
            }

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

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

        case AddSkillCommand.COMMAND_WORD:
            return new AddSkillCommandParser().parse(arguments);

        case AddSkillLevelCommand.COMMAND_WORD:
            return new AddSkillLevelCommand();

        case ClearSearchHistoryCommand.COMMAND_WORD:
            return new ClearSearchHistoryCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * A simpler version of parseCommand without execution
     *
     * @param input anyStringInput
     * @return commandWord if command format is valid.
     */
    public static String basicParseCommand(String input) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(input.trim());
        if (!matcher.matches()) {
            return null;
        }
        final String commandWord = matcher.group("commandWord");
        return commandWord;
    }

}
